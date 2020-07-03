package com.hy;

import com.hy.servlet.DispacherServlet;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class App {

    private Tomcat tomcat = new Tomcat();

    File tmpDir = new File(this.getClass().getResource("/").getPath() + "\\tomcat");

    public static void main(String[] args) {
        new App().init();
    }

    private void init() {
        shutDownListenner();
        refresh();
    }

    private void refresh() {
        if(!tmpDir.exists()){
            new File(tmpDir.getPath() + "\\webapps\\ROOT").mkdirs();
        }
        tomcat.setBaseDir(tmpDir.getAbsolutePath()); // 设置工作目录
        tomcat.setHostname("localhost"); // 主机名, 将生成目录: {工作目录}/work/Tomcat/{主机名}/ROOT
        System.out.println("工作目录: " + tomcat.getServer().getCatalinaBase().getAbsolutePath());

        tomcat.setPort(8081);
        Connector conn = tomcat.getConnector(); // Tomcat 9.0 必须调用 Tomcat#getConnector() 方法之后才会监听端口
        System.out.println("连接器设置完成: " + conn);

        // contextPath要使用的上下文映射，""表示根上下文
        // docBase上下文的基础目录，用于静态文件。相对于服务器主目录必须存在 ({主目录}/webapps/{docBase})
        Context ctx = tomcat.addContext("", /*{webapps}/~*/ "/ROOT");

        Tomcat.addServlet(ctx, "dispacherServlet", new DispacherServlet()

//                new HttpServlet() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            protected void service(HttpServletRequest request, HttpServletResponse response)
//                    throws ServletException, IOException {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/plain");
//                response.setHeader("Server", "Embedded Tomcat");
//                try (Writer writer = response.getWriter()) {
//                    writer.write("Hello, Embedded Tomcat!");
//                    writer.flush();
//                }
//            }
//        }

        );
        //生命周期监听
        ctx.addLifecycleListener(new LifecycleListener() {
            public void lifecycleEvent(LifecycleEvent event) {
//				System.out.println(event.getLifecycle().getState().name());
                if (event.getLifecycle().getState() == LifecycleState.STARTING_PREP) {
                    try {
//                        new SpringServletContainerInitializer().onStartup(new HashSet<Class<?>>() {
//                            private static final long serialVersionUID = 1L;
//                            {
//                                add(WebAppInitializer.class);
//                            }
//                        }, ctx.getServletContext());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ctx.addServletMappingDecoded("/", "dispacherServlet");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        System.out.println("tomcat 已启动");
        tomcat.getServer().await();
    }

    private void shutDownListenner(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    tomcat.destroy();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            })
        );
    }
}
