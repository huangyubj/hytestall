package com.hy.controller;

import com.hy.entity.Messageflow;
import com.hy.service.YuebaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class PayController {

    /**
     * 模拟扫码获取支付信息
     * @return
     */
    @GetMapping("/sweeppaycode")
    public String pey(ModelMap map){
        Messageflow messageflow = new Messageflow();
        messageflow.setPayuserid(1);
        messageflow.setReceiveuserid(2);
        messageflow.setMoney(50L);
        map.addAttribute("message", messageflow);
        return "sweeppaycode";
    }

    @Autowired
    private YuebaoService yuebaoService;

    /**
     * 支付操作
     * @param message
     * @return
     */
    @PostMapping("/dopay")
    @ResponseBody
    public String dopay(Messageflow message){
        yuebaoService.doPay(message);
        return "支付成功";
    }
}
