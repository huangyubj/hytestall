package com.hy.sso.controller;

import com.hy.sso.filter.LoginFilter;
import com.hy.sso.filter.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class BusiController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/tologin", method = RequestMethod.GET)
    public String toLogin(Model model, HttpServletRequest request){
        Object user =  request.getSession().getAttribute(LoginFilter.USER_INFO);
        String url = request.getParameter("url");
        //已经登陆
        if(user != null){
            String ticket = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(ticket, user);
            return "redirect:"+url+"?ticket="+ticket;
        }
        UserForm u = new UserForm();
        u.setUsername("hy");
        u.setPassword("hy");
        u.setBackurl(url);
        model.addAttribute("user", u);
        return "login";
    }

    @PostMapping("/login")
    public void doLogin(@ModelAttribute UserForm user, HttpServletRequest request, HttpServletResponse response){
        if("hy".equals(user.getUsername())){
            String url = user.getBackurl();
            request.getSession().setAttribute(LoginFilter.USER_INFO, user);
            try {
                if(!"".equals(url) && null != url){
                    String ticket = UUID.randomUUID().toString();
                    redisTemplate.opsForValue().set(ticket, user);
                    response.sendRedirect(url+"?ticket="+ticket);
                }else{
                    response.sendRedirect("/index");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", request.getSession().getAttribute(LoginFilter.USER_INFO));
        return modelAndView;
    }
}
