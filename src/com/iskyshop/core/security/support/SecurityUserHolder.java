package com.iskyshop.core.security.support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.User;

public class SecurityUserHolder {
    public static User getCurrentUser() {
        if ((SecurityContextHolder.getContext().getAuthentication() != null)
                && (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)) {
            return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        User user = null;
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            user = (request.getSession(false).getAttribute("user") != null) ? (User) request.getSession(false)
                    .getAttribute("user") : null;

            Cookie[] cookies = request.getCookies();
            String id = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("iskyshop_user_session")) {
                        id = CommUtil.null2String(cookie.getValue());
                    }
                }
            }
            if (id.equals("")) {
                user = null;
            }
        }

        return user;
    }
}