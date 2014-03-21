package com.iskyshop.core.loader;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iskyshop.core.security.SecurityManager;

public class ServletContextLoaderListener implements ServletContextListener {
    @SuppressWarnings("rawtypes")
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        SecurityManager securityManager = getSecurityManager(servletContext);
        Map urlAuthorities = securityManager.loadUrlAuthorities();
        servletContext.setAttribute("urlAuthorities", urlAuthorities);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute("urlAuthorities");
    }

    protected SecurityManager getSecurityManager(ServletContext servletContext) {
        return ((SecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(
                "securityManager"));
    }
}