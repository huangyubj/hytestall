package com.hy.controller;

import com.hy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlashsalesCOntroller {

    @Autowired
    OrderService orderService;

    @PostMapping("/initCatalog")
    @ResponseBody
    public String initCatalog()  {
        try {
            orderService.initGoodsInfo();
        } catch (Exception e) {

        }

        return "init is ok";
    }
}
