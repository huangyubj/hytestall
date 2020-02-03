package com.hy.sharesurpport;

import com.hy.sharesurpport.utils.SessionUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ShareHttpServletRequest extends HttpServletRequestWrapper {

    private static final String SESSIONID_NAME = "session_share_name";
    private RedisTemplate redisTemplate;
    private boolean isCommit = false;

    private ShareSession session;

    public ShareHttpServletRequest(HttpServletRequest request, RedisTemplate redisTemplate) {
        super(request);
        this.redisTemplate = redisTemplate;
    }

    /*
    更新用户登陆信息
     */
    public void sessionCommit(){
        if(isCommit)
            return;
        isCommit = true;
        redisTemplate.opsForHash().putAll(session.getId(), session.getAttrs());
    }

    public ShareSession getSession(){
        if (session == null){
            session = createSession();
        }
        return session;
    }

    private ShareSession createSession() {
        session = new ShareSession();
        String sessionId = SessionUtil.getShareSessionId(this);
        Map<String, Object> attrs;
        if(null == sessionId || "".equals(sessionId)){
            String id = UUID.randomUUID().toString();
            session.setId(id);
            attrs = new HashMap<String, Object>();
        }else{
            session.setId(sessionId);
            attrs = redisTemplate.opsForHash().entries(sessionId);
        }
        session.setAttrs(attrs);
        return session;
    }
    public boolean isLogin(){
        Object user = getSession().getAttribute(SessionFilter.SESSION_USER_INFO);
        return null != user;
    }
}
