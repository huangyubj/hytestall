package com.hy.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 1.服务启动扫描装配注解
 * 2.实例化扫描的对象
 * 3.依赖注入
 * 4.建立一个path与method的映射关系
 */
public class DispacherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }
}
