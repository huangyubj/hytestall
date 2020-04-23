package com.hy.controller;

import com.hy.entity.Messageflow;
import com.hy.service.YuebaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PayController {

    @GetMapping
    public String pey(){
        return "sweeppaycode";
    }

    @Autowired
    private YuebaoService yuebaoService;

    @PostMapping("/dopay")
    @ResponseBody
    public String dopay(Messageflow message){
        yuebaoService.doPay(message);
        return "支付成功";
    }
}
