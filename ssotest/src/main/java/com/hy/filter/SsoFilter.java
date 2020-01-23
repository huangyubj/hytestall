package com.hy.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SsoFilter implements Filter {
    public static final String USER_INFO = "user_info";

    @Autowired
    private RedisTemplate redisTemplate;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object userInfo = request.getSession().getAttribute(USER_INFO);
        String path = request.getServletPath();
        if(!"/login".equals(path) && !"/tologin".equals(path) &&userInfo == null){
            String ticket = request.getParameter("ticket");
            if(null != ticket && null != (userInfo = redisTemplate.opsForValue().get(ticket))){
                request.getSession().setAttribute(USER_INFO, userInfo);
                redisTemplate.delete(ticket);
            }else{
                response.sendRedirect("http://login.hy.com:9090?url=" + request.getRequestURL().toString());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
