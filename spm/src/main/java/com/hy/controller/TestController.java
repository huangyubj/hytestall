package com.hy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/jsp")
    public String helloJsp(){
        return "index";
    }
    @RequestMapping("/thymeleaf")
    public String helloThymeleaf(ModelMap map){
        map.addAttribute("name", "hy");
        return "testThymeleaf";
    }
}
