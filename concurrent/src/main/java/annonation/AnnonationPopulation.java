package annonation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnonationPopulation {
    public static void main(String[] args) {
        try {
            //获取到要注入的属性
            PropertyDescriptor descriptor = new PropertyDescriptor("persion", PersionDao.class);
            // 实例化一个需要注入的实例
            Persion persion = (Persion) descriptor.getPropertyType().newInstance();
            // 获取对应的注入方法
            Method writeMethod = descriptor.getWriteMethod();
            // 注入方法的注解
            Annotation annotation = writeMethod.getAnnotation(InjectPerson.class);
            // 注解的方法
            Method[] annnotationMethods = annotation.getClass().getMethods();
            for (Method method: annnotationMethods) {
                // 反射注解方法获取注解的值
                Object o = method.invoke(annotation);
                //获取需要赋值对象的 set方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(method.getName(), Persion.class);
                Method ppMethod = propertyDescriptor.getWriteMethod();
                //调用set方法赋值
                ppMethod.invoke(persion, o);
            }
            PersionDao persionDao = new PersionDao();
            // set方法赋值
            writeMethod.invoke(persionDao, persion);
            System.out.println(persionDao.toString());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
