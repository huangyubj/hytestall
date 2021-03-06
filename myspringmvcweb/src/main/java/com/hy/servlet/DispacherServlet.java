package com.hy.servlet;

import com.hy.annotation.HyAutoware;
import com.hy.annotation.HyController;
import com.hy.annotation.HyRequestMapping;
import com.hy.annotation.HyService;
import com.hy.handlerAdapter.HyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;


public class DispacherServlet extends HttpServlet {

    private static final String HANDLER_ADAPTER = "com.hy.handlerAdapter.HyHandlerAdapter";

    List<String> classNames = new ArrayList<>();

    private Map<String, Object> beans = new HashMap<>();

    private Map<String, Object> handlerMap = new HashMap<>();

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
        System.out.println("getPathInfo-" + req.getPathInfo());
        System.out.println("getPathTranslated-" + req.getPathTranslated());
        System.out.println("getContextPath-" + req.getContextPath());
        System.out.println("getServletPath-" + req.getServletPath());
        System.out.println("getRequestURI-" + req.getRequestURI());
        System.out.println("getRequestURL-" + req.getRequestURL());
        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.replace(context, "");
        Method method = (Method) handlerMap.get(path);
        if(null != method){
            Class clazz = method.getDeclaringClass();
            Object controller = beans.get(clazz.getName());
            HyHandlerAdapter hyHandlerAdapter = (HyHandlerAdapter) beans.get(HANDLER_ADAPTER);
            Object[] args =  hyHandlerAdapter.hand(req, resp, method, beans);
            try {
                method.invoke(controller, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    private void handlerMapping() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if(clazz.isAnnotationPresent(HyController.class)){
                String classpath = "";
                if(clazz.isAnnotationPresent(HyRequestMapping.class)){
                    classpath = clazz.getAnnotation(HyRequestMapping.class).value();
                }
                Method[]  methods = clazz.getMethods();
                for (Method method:methods) {
                    if (method.isAnnotationPresent(HyRequestMapping.class)){
                        String methodPath = classpath + method.getAnnotation(HyRequestMapping.class).value();
                        handlerMap.put(methodPath, method);
                        System.out.println(String.format("add mapping----key:%s", methodPath));
                    }
                }
            }
        }

    }

    private void ioc() {
        Set<Map.Entry<String, Object>> sets = beans.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = sets.iterator();
        if (iterator.hasNext()) {
            Object instance = iterator.next().getValue();
            Class clazz = instance.getClass();
            Field[] fields = clazz.getFields();
            for (Field fied: fields) {
                if(fied.isAnnotationPresent(HyAutoware.class)){
                    HyAutoware hyAutoware = fied.getAnnotation(HyAutoware.class);
                    String key = hyAutoware.value();
                    key = key != null && !"".equals(key) ? key : fied.getType().getName();
                    Object fieldObj = beans.get(key);
                    fied.setAccessible(true);
                    try {
                        fied.set(instance, fieldObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
                    key = key != null && !"".equals(key) ? key : clazz.getInterfaces()[0].getName();
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
        try {
            servlet.init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}
