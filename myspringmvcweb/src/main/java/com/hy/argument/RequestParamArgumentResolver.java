package com.hy.argument;

import com.hy.annotation.HyParame;
import com.hy.annotation.HyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@HyService("requestParamArgumentResolver")
public class RequestParamArgumentResolver implements ArgumentResolver {
    @Override
    public boolean support(Class<?> type, int paramIndex, Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        Annotation[] paramAnnotations = annotations[paramIndex];
        for (Annotation annotation:paramAnnotations) {
            if(HyParame.class.isAssignableFrom(annotation.getClass())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int paramIndex, Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        Annotation[] paramAnnotations = annotations[paramIndex];
        for (Annotation annotation:paramAnnotations) {
            if(HyParame.class.isAssignableFrom(annotation.getClass())){
                return request.getParameter(((HyParame) annotation).value());
            }
        }
        return null;
    }
}
