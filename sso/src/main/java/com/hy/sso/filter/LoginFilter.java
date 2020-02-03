package com.hy.sso.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    public static final String USER_INFO = "user_info";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userInfo = request.getSession().getAttribute(USER_INFO);
        String path = request.getServletPath();
        if(!"/login".equals(path) && !"/tologin".equals(path) &&userInfo == null){
            request.getRequestDispatcher("/tologin").forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
