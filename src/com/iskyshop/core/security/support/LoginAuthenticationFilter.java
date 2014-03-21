package com.iskyshop.core.security.support;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.util.TextUtils;

import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.uc.api.UCClient;
import com.iskyshop.uc.api.XMLHelper;

public class LoginAuthenticationFilter extends AuthenticationProcessingFilter {

    @Autowired
    private ISysConfigService configService;

    public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        String login_role = request.getParameter("login_role");
        if ((login_role == null) || (login_role.equals("")))
            login_role = "user";
        HttpSession session = request.getSession();
        session.setAttribute("login_role", login_role);
        session.setAttribute("ajax_login", Boolean.valueOf(CommUtil.null2Boolean(request.getParameter("ajax_login"))));
        boolean flag = true;
        if (session.getAttribute("verify_code") != null) {
            String code = (request.getParameter("code") != null) ? request.getParameter("code").toUpperCase() : "";
            if (!(session.getAttribute("verify_code").equals(code))) {
                flag = false;
            }
        }
        if (!(flag)) {
            String username = obtainUsername(request);
            String password = "";
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                    password);
            if ((session != null) || (getAllowSessionCreation())) {
                request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", TextUtils.escapeEntities(username));
            }
            setDetails(request, authRequest);
            return getAuthenticationManager().authenticate(authRequest);
        }
        String username = "";
        if (CommUtil.null2Boolean(request.getParameter("encode")))
            username = CommUtil.decode(obtainUsername(request)) + "," + login_role;
        else
            username = obtainUsername(request) + "," + login_role;
        String password = obtainPassword(request);

        if (this.configService.getSysConfig().isUc_bbs()) {
            String uc_login_js = uc_Login(CommUtil.decode(obtainUsername(request)), password);
            request.getSession(false).setAttribute("uc_login_js", uc_login_js);
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        if ((session != null) || (getAllowSessionCreation())) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", TextUtils.escapeEntities(username));
        }
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws IOException {
        request.getSession(false).removeAttribute("verify_code");

        super.onSuccessfulAuthentication(request, response, authResult);
    }

    @SuppressWarnings("unused")
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {
        String uri = request.getRequestURI();
        super.onUnsuccessfulAuthentication(request, response, failed);
    }

    @SuppressWarnings({ "rawtypes", "unused" })
    private static String uc_Login(String username, String pws) {
        String ucsynlogin = "";
        UCClient e = new UCClient();
        String result = e.uc_user_login(username, pws);
        LinkedList rs = XMLHelper.uc_unserialize(result);
        if (rs.size() > 0) {
            int uid = Integer.parseInt((String) rs.get(0));
            String uname = (String) rs.get(1);
            String password = (String) rs.get(2);
            String email = (String) rs.get(3);
            if (uid > 0) {
                ucsynlogin = e.uc_user_synlogin(uid);
            } else if (uid == -1) {
                System.out.println("�û�������,���߱�ɾ��");
            }

        }

        return ucsynlogin;
    }
}