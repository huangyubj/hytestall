package other;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavassistDemo {
    private static final String CLASS_NAME = "other.SsistHello";
    //ASM
    public static void main(String[] args) {
//        makeClass();
        testClass();
    }

    private static void testClass() {
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.getCtClass(CLASS_NAME);
            Class helloClass1 = ctClass.toClass();
            //三种反射方式 1. Class.forName();
            Class helloClass = Class.forName(CLASS_NAME);
            Object hello = helloClass.newInstance();
            //2.hello.getClass()
            hello.getClass();
            //3.String.class
            Method methodSet = helloClass.getMethod("setMsg", String.class);
            methodSet.invoke(hello, "hello ssist");
            Method method = helloClass.getMethod("hello");
            method.invoke(hello);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void makeClass(){
        try {
            ClassPool classPool = ClassPool.getDefault();
//        创建一个空类
            CtClass ctClass = classPool.makeClass(CLASS_NAME);
//        新增msg 字段
            CtField ctField = new CtField(classPool.get("java.lang.String"), "msg", ctClass);
            ctField.setModifiers(Modifier.PRIVATE);
            ctClass.addField(ctField, CtField.Initializer.constant("hello ssist"));
//        生成 getter、setter 方法
            ctClass.addMethod(CtNewMethod.setter("setMsg", ctField));
            ctClass.addMethod(CtNewMethod.getter("getMsg", ctField));
//        无参构造方法
            ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
//      有参构造方法
            CtConstructor ctConstructorParam = new CtConstructor(new CtClass[]{classPool.get("java.lang.String")}, ctClass);
//          $0=this / $1,$2,$3... 代表方法参数
            ctConstructorParam.setBody("{$0.msg = $1;}");
            ctClass.addConstructor(ctConstructorParam);

//        添加自定义hello方法
            CtMethod ctMethod = new CtMethod(CtClass.voidType, "hello", new CtClass[]{}, ctClass);
            ctMethod.setModifiers(Modifier.PUBLIC);
            ctMethod.setBody("{System.out.println(\"print message:\"+msg);}");
            ctClass.addMethod(ctMethod);
            //这里会将这个创建的类对象编译为.class文件
            ctClass.writeFile("F:/my_hy/testall/hytest/other/target/classes/");

        }catch (NotFoundException e){
            e.printStackTrace();
        }catch (CannotCompileException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
