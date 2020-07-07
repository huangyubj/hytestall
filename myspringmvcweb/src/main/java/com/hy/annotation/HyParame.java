package com.hy.annotation;

import java.lang.annotation.*;

//作用范围:用在接口或类上
@Target({ElementType.PARAMETER})
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
//明该注解将被包含在javadoc中    @Inherited：说明子类可以继承父类中的该注解
@Documented
public @interface HyParame {
    String value() default "";
}
