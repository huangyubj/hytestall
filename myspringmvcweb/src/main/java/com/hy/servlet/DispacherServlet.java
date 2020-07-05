package com.hy.servlet;

import com.hy.annotation.HyAutoware;
import com.hy.annotation.HyController;
import com.hy.annotation.HyRequestMapping;
import com.hy.annotation.HyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;


public class DispacherServlet extends HttpServlet {

    List<String> classNames = new ArrayList<>();

    private Map<String, Object> beans = new HashMap<>();

    private Map<String, Object> HandlerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        /**
         * 1.服务启动扫描类
         * 2.实例化扫描的对象
         * 3.依赖注入
         * 4.建立一个path与method的映射关系
         */
        scanPackge("com.hy");
        instance();
        ioc();
        handlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 1.获取url，
         * 2.根据url解析获取映射，
         * 3.找到对应的Handler，反射调用Method
         * 4.如果Method被Responsebody 注解直接返回字符串
         */
    }

    private void handlerMapping() {

    }

    private void ioc() {
        Set<Map.Entry<String, Object>> sets = beans.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = sets.iterator();
        if (iterator.hasNext()) {
            Object o = iterator.next().getValue();
            Class clazz = o.getClass();
            Field[] fields = clazz.getFields();
            for (Field fied: fields) {
                if(fied.isAnnotationPresent(HyAutoware.class)){
                    HyAutoware hyAutoware = fied.getAnnotation(HyAutoware.class);
                    String key = hyAutoware.value();
                    key = key != null && !"".equals(key) ? key : fied.getType().getName();
                    Object fieldObj = beans.get(key);

                }
            }
        }
    }

    private void instance() {
        if(classNames.size() == 0){
            System.out.println("包扫描失败！");
            return;
        }
        for (String className : classNames) {
            // com.enjoy.james.service.impl.JamesServiceImpl.class
            String cn = className.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(cn);
                if(clazz.isAnnotationPresent(HyController.class)){
                    HyController controller = clazz.getAnnotation(HyController.class);
                    Object obj = clazz.newInstance();
                    String key = controller.value();
                    key = key != null && !"".equals(key) ? key : clazz.getName();
                    beans.put(key, obj);
                    System.out.println(String.format("regist instance----key:%s", key));
                }else if(clazz.isAnnotationPresent(HyService.class)){
                    HyService service = clazz.getAnnotation(HyService.class);
                    Object obj = clazz.newInstance();
                    String key = service.value();
                    key = key != null && !"".equals(key) ? key : clazz.getName();
                    beans.put(key, obj);
                    System.out.println(String.format("regist instance----key:%s", key));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

    private void scanPackge(String path) {
        String fileStr = this.getClass().getResource("/" + path.replaceAll("\\.", "/")).getFile();
        File file = new File(fileStr);
        if(file.exists()){
            String[] filesArray = file.list();
            for (String filepath: filesArray) {
                File tempFile = new File(fileStr + "/"+ filepath);
                if(tempFile.isDirectory()){
                    scanPackge(path + "." + filepath);
                }else{
                    classNames.add(path+"."+filepath);
                    System.out.println(path+"."+filepath);
                }
            }
        }
    }

    public static void main(String[] args) {
        DispacherServlet servlet = new DispacherServlet();
        servlet.scanPackge("com.hy");
    }

}
