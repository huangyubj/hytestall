package com.hy.controller;

import com.hy.filter.SsoFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BusiController {

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", request.getSession().getAttribute(SsoFilter.USER_INFO));
        return modelAndView;
    }
}
