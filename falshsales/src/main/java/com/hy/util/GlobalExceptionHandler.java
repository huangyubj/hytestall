package com.hy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public Object defaultExceptionHandler(HttpServletRequest req, Exception e){
//        e.printStackTrace();
//        System.err.println("this is exception");
//        return "this is a exception";
//    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Long processException(HttpServletRequest req, Exception e) {
        log.info("error:{}", e.getMessage());
        return 0L;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return (factory->{
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.do");
            factory.addErrorPages( error404Page);
        });
    }
}
