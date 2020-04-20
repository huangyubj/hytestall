package com.hy.controller;

import com.hy.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PayController {

    @GetMapping
    public String pey(){
        return "sweeppaycode";
    }

    @PostMapping("/dopay")
    @ResponseBody
    public String dopay(Message message){

        return null;
    }
}
