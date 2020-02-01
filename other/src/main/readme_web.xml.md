#### webx.ml解析过程
1. 启动一个WEB项目的时候，WEB容器会去读取它的配置文件web.xml，读取`<listener>`和`<context-param>`两个结点。
2. 紧急着，容创建一个ServletContext（servlet上下文），这个web项目的所有部分都将共享这个上下文。
3. 容器将`<context-param>`转换为键值对，并交给servletContext。
4. 容器创建`<listener>`中的类实例，创建监听器。

#### Load-on-startup
> Load-on-startup 元素在web应用启动的时候指定了servlet被加载的顺序，
>它的值必须是一个整数。`<load-on-startup>5</load-on-startup>`
>- 当值为0或者大于0时，表示容器在应用启动时就加载这个servlet；
>- 当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。
>- 正数的值越小，启动该servlet的优先级越高。

#### 加载顺序
> web.xml 的加载顺序是：ServletContext -> context-param -> listener -> filter
-> servlet ，而同个类型之间的实际程序调用的时候的顺序是根据对应的 mapping 的顺序进行调用的。

#### web.xml文件详解
>web.xml首先是肯定要包含它的schema.
```xml
<web-app xmlns="http://java.sun.com/xml/ns/j2ee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
    version="2.4">
    <discription>是对站台的描述</discription>
    <display-name>定义站台的名称</display-name>
    <distributable/> <!--是指定该站台是否可分布式处理-->
</web-app>
```
> `<context-param></context-param>`用来设定web站台的环境参数,
> 可以在servlet中用 getServletContext().getInitParameter("my_param") 来取得
```xml
<context-param>
    <param-name>my_param</param-name>
    <param-value>hello</param-value>
</context-param>
```
> `<filter></filter>` 是用来声明filter的相关设定，
> `<filter-mapping>`filter拦截映射定义，两者结合使用
```xml
<filter>
    <filter-name>setCharacterEncoding</filter-name>
    <filter-class>com.myTest.setCharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>setCharacterEncoding</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
> `<listener></listener>`用来设定Listener接口
```xml
<listener>
    <listener-class>com.myTest.ContextListener</listener-class>
</listener>
```
> `<servlet></servlet>`用来声明一个servlet的数据
> `<servlet-mapping>`servlet映射地址，两者结合使用
```xml
<servlet>
        <servlet-name>ShoppingServlet</servlet-name>
        <servlet-class>com.myTest.ShoppingServlet</servlet-class>
</servlet>
<servlet-mapping>
        <servlet-name>ShoppingServlet</servlet-name>
        <url-pattern>/shop/ShoppingServlet</url-pattern>
</servlet-mapping>
```

> `<session-config></session-config>` 用来定义web站台中的session参数，
> 用来定义这个web站台所有session的有效期限，单位为分钟
```xml
<session-config>
    <session-timeout>15</session-timeout>
</session-config>
```
> `<mime-mapping></mime-mapping>` 定义某一个扩展名和某一个MIME Type做对映，包含两个                                                        子元素：
```xml
<mime-mapping>
    <!--    扩展名的名称 -->
    <extension>xls</extension>
    <!--    MIME格式 -->
    <mime-type>application/vnd.ms-excel</mime-type>
</mime-mapping>
```
> `<welcome-file-list></welcom-file-list>` 用来定义首页的列单
```xml
<welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>index.html</welcome-file>
</welcom-file-list>
```

> `<error-page></error-page>` 用来处理错误代码或异常的页面
```xml
<error-page>
    <error-code>404</error-code>
    <location>/error404.jsp</location>
</error-page>
<error-page>
    <exception-type>java.lang.Exception</exception-type>
    <location>/exception.jsp</location>
</error-page>
```
> `<resource-ref></resource-ref>` 定义利用JNDI取得站台可利用的资源，配置数据库连接池就可在此配置：
```xml
<resource-ref>
        <description>JNDI JDBC DataSource of shop</description>
        <res-ref-name>jdbc/sample_db</res-ref-name>
        <res-type>javax.sql.DataSource</res-type>
        <res-auth>Container</res-auth>
        <!--资源是否共享 -->
        <res-sharing-scope>Unshareable或者Shareable</res-sharing-scope> 
</resource-ref>
```
> 配置Spring
```xml
   <!-- 指定spring配置文件位置 -->
   <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>
       <!--加载多个spring配置文件 -->
        /WEB-INF/applicationContext.xml, /WEB-INF/action-servlet.xml
      </param-value>
   </context-param>
   <!-- 定义SPRING监听器，加载spring -->
<listener>
     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<listener>
     <listener-class>
       org.springframework.web.context.request.RequestContextListener
     </listener-class>
</listener>
```
>spring加载顺序先 listener >> filter >> servlet >>  spring
> 所以，如果过滤器中要使用到 bean，可以将spring 的加载 改成 Listener的方式
