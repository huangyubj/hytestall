package com.hy.controller;

import com.hy.annotation.HyController;
import com.hy.annotation.HyRequestMapping;

@HyController
public class FaviconController {

    @HyRequestMapping("/favicon.ico")
    public void favicon(){

    }
}
