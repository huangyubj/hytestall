1. SpringMvc的优点
>- 清晰的角色划分：控制器(controller)、验证器(validator)、命令对象(command obect)、
表单对象(form object)、模型对象(model object)、Servlet分发器(DispatcherServlet)、
处理器映射(handler mapping)、试图解析器(view resoler)等等。
每一个角色都可以由一个专门的对象来实现
>- 

2. SpringMvc 的控制器是不是单例模式
> 是，存在线程安全，
>- 不存在可变的成员变量,即状态不可变，是安全的
>- 通过ThreadLocal来存储状态可变的变量

3. SpringMvc和struts2区别
>- sp入口是一个servlet，st是一个filter
>- sp针对方法开发,可单例、多例，st针对类作为action开发，只能多例，所以sp性能高于st

4. SpringMVC 怎么样设定重定向和转发的
>- 转发在返回前加forward:，如：forward:user.do?name=method4
>- 重定向返回前加 redirect:，如：redirect:http://www.baidu.com

5. Springnmvc 能返回的数据类型
>- String、Object,通过@ResponseBody 可直接返回数据
>- ModeAndView，返回一个view页面

6. Model、ModelMap、ModelAndView
>- Model是用于给View传递数据的模型
>- ModeMap是用于服务给页面传递数据的，无需自己创建，容器自动注入
>- ModeAndView,需要自行创建定义返回的视图和数据模型

7. Filter和Interceptor的区别
>- Filter是基于函数回调（doFilter()方法）的，而Interceptor则是基于Java反射的（AOP思想）。
>- Filter依赖于Servlet容器，而Interceptor不依赖于Servlet容器。
>- Filter对几乎所有的请求起作用，而Interceptor只能对action请求起作用。
>- Interceptor可以访问Action的上下文，值栈里的对象，而Filter不能。
>- 在action的生命周期里，Interceptor可以被多次调用，而Filter只能在容器初始化时调用一次。

8. spring MVC工作原理
![Spring MVC 工作原理](./springmvc.png)
>- 客户端（浏览器）发送请求，直接请求到`DispatcherServlet`。
>- DispatcherServlet根据请求信息调用`HandlerMapping`，解析请求对应的Handler。
>- 解析到对应的`Handler`（也就是我们平常说的Controller控制器）。
>- `HandlerAdapter`会根据Handler来调用真正的处理器来处理请求和执行相对应的业务逻辑。
>- 处理器处理完业务后，会返回一个`ModelAndView`对象，Model是返回的数据对象，View是逻辑上的View。
>- `ViewResolver`会根据逻辑View去查找实际的View。
>- DispatcherServlet把返回的Model传给View（视图渲染）。
>- 把`View`返回给请求者（浏览器）