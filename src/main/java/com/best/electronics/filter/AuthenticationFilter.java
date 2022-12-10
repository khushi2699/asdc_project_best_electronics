package com.best.electronics.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter  {

    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig){
        System.out.println("Inside AuthenticationFilter filter");
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);

        String excludedUrls = filterConfig.getInitParameter("excluded_urls");
        String uri = httpServletRequest.getRequestURI();
        if(excludedUrls != null){
            String[] exUrls = excludedUrls.split(",");
            for(String excludedUrl: exUrls){
                if(Pattern.matches(excludedUrl.trim(), uri)){
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        if (session == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/welcome");
        } else {
            chain.doFilter(request, response);
        }
    }
}

