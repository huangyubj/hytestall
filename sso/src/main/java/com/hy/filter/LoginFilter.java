package com.hy.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截非登录请求，校验是否登录
 */
public class LoginFilter implements Filter {

    public static final String USER_INFO = "user_info";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userinfo = request.getSession().getAttribute(USER_INFO);
        String uri = request.getRequestURI();
        String path = request.getServletPath();
        //不是登录或者去登录，用户信息为空则需要登录
        if(!"/tologin".equals(path) && !"login".equals(path) && userinfo == null){
            request.getRequestDispatcher("/tologin").forward(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
