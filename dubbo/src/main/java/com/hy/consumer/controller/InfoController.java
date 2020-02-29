package com.hy.consumer.controller;

import com.hy.api.entity.InfoEntity;
import com.hy.api.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/info/detail")
    @ResponseBody
    public String getInfo(String id){
        InfoEntity info = infoService.getDetail(id);
        return info.toString();
    }
}
