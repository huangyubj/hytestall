package com.hy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//作用范围:用在接口或类上
@Target({java.lang.annotation.ElementType.TYPE})
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
//明该注解将被包含在javadoc中    @Inherited：说明子类可以继承父类中的该注解
@Documented
public @interface HyController {
    String value() default "";
}
