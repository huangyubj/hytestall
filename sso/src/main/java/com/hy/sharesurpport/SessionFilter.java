package com.hy.sharesurpport;

import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 简单session共享实现（利用redis缓存登陆的sessioidn/token信息）
 * 1。增强HttpServerletRequest，登陆成功向redis中存储一份sessioidn/token对应的用户登陆信息
 * sessionid/token可通过cookie来共享
 * 2。新请求携带sessionid/token，增强request获取session，如果没有创建新session并从redis缓存中尝试获取缓存信息
 *
 */
public class SessionFilter implements Filter {

    public static final String SESSION_USER_INFO = "session_user_info";

    private RedisTemplate redisTemplate;

    public SessionFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rst = (HttpServletRequest) servletRequest;
        ShareHttpServletRequest request = new ShareHttpServletRequest(rst, redisTemplate);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        Object userInfo = request.getSession().getAttribute(SESSION_USER_INFO);
        String path = request.getServletPath();
        if(!"/login".equals(path) && !"/tologin".equals(path) && !request.isLogin()){
            request.getRequestDispatcher("/tologin").forward(request, response);
            return;
        }
        try{
            filterChain.doFilter(request, response);
        }finally {
            request.sessionCommit();
        }
    }

    public void destroy() {

    }
}
