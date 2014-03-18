/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.zip;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CompressionFilter implements Filter {
    protected Log log = LogFactory.getFactory().getInstance(super.getClass().getName());

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        boolean compress = false;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Enumeration headers = httpRequest.getHeaders("Accept-Encoding");
            while (headers.hasMoreElements()) {
                String value = (String) headers.nextElement();
                if (value.indexOf("gzip") != -1) {
                    compress = true;
                }
            }
        }

        if (compress) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader("Content-Encoding", "gzip");
            CompressionResponse compressionResponse = new CompressionResponse(httpResponse);
            chain.doFilter(request, compressionResponse);
            compressionResponse.close();
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}