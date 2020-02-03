package com.hy.sharesurpport.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtil {


    private static final String COOKIE_NAME_SESSION = "hypersion";

    public static String getShareSessionId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie : cookies){
            if(cookie != null && COOKIE_NAME_SESSION.equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
    public static void onNewSession(HttpServletRequest request,
                                    HttpServletResponse response) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        Cookie cookie = new Cookie(COOKIE_NAME_SESSION, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath(request.getContextPath() + "/");
        cookie.setDomain(".hy.com");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }
}
