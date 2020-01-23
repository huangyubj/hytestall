package com.hy.sso.controller;

import com.hy.sso.filter.UserForm;
import com.hy.sharesurpport.SessionFilter;
import com.hy.sharesurpport.ShareHttpServletRequest;
import com.hy.sharesurpport.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class BusiController {
    @RequestMapping(value = "/tologin", method = RequestMethod.GET)
    public String toLogin(Model model){
        UserForm user = new UserForm();
        user.setUsername("hy");
        user.setPassword("hy");
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public void doLogin(@ModelAttribute UserForm user, ShareHttpServletRequest request, HttpServletResponse response){
        if("hy".equals(user.getUsername())){
            request.getSession().setAttribute(SessionFilter.SESSION_USER_INFO, user);
            SessionUtil.onNewSession(request, response);
            request.sessionCommit();
            try {
                response.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", request.getSession().getAttribute(SessionFilter.SESSION_USER_INFO));
        return modelAndView;
    }
}
