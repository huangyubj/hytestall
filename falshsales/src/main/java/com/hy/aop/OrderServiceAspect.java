package com.hy.aop;

import com.hy.dao.CatalogMapper;
import com.hy.entity.Catalog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
@Aspect
public class OrderServiceAspect {

    @Autowired
    private CatalogMapper catalogMapper;

    @Pointcut("@annotation(com.hy.annotation.OrderService)")
    public void aspectMethod(){

    }

    @Around("aspectMethod()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object[] args = proceedingJoinPoint.getArgs();
        int orderId = -1;
        if(args.length == 1){
            int id = (int) args[0];
            int num = catalogMapper.updateCatalognum(id, 1);
            if(num == 1){
                try {
                    orderId = (int) proceedingJoinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return orderId;
    }
}
