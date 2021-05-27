- 方法参数匹配
args()
@args()
- 方法描述匹配
execution()
- 当前AOP代理对象类型匹配
this()
- 目标类匹配
target()
@target()
within()
@within()
- 标有此注解的方法匹配
@annotation()


> 其中execution 是用的最多的,其格式为: <br>
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)

>- returning type pattern,name pattern, and parameters pattern是必须的.
>- ret-type-pattern:可以为*表示任何返回值,全路径的类名等.
>- name-pattern:指定方法名, *代表所有
>- set*代表以set开头的所有方法.
>- parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数
>- (*,String)代表第一个参数为任何值,第二个为String类型.

```
任意公共方法的执行：
execution(public * *(..))
任何一个以“set”开始的方法的执行：
execution(* set*(..))
AccountService 接口的任意方法的执行：
execution(* com.xyz.service.AccountService.*(..))
定义在service包里的任意方法的执行：
execution(* com.xyz.service.*.*(..))
定义在service包和所有子包里的任意类的任意方法的执行：
execution(* com.xyz.service..*.*(..))
定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：
execution(* com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))")
***> 最靠近(..)的为方法名,靠近.*(..))的为类名或者接口名,如上例的JoinPointObjP2.*(..))

pointcutexp包里的任意类.
within(com.test.spring.aop.pointcutexp.*)
pointcutexp包和所有子包里的任意类.
within(com.test.spring.aop.pointcutexp..*)
实现了MyInterface接口的所有类,如果MyInterface不是接口,限定MyInterface单个类.
this(com.test.spring.aop.pointcutexp.MyInterface)
***> 当一个实现了接口的类被AOP的时候,用getBean方法必须cast为接口类型,不能为该类的类型.

带有@MyTypeAnnotation标注的所有类的任意方法.
@within(com.elong.annotation.MyTypeAnnotation)
@target(com.elong.annotation.MyTypeAnnotation)
带有@MyTypeAnnotation标注的任意方法.
@annotation(com.elong.annotation.MyTypeAnnotation)
***> @within和@target针对类的注解,@annotation是针对方法的注解

参数带有@MyMethodAnnotation标注的方法.
@args(com.elong.annotation.MyMethodAnnotation)
参数为String类型(运行是决定)的方法.
args(String)
————————————————
版权声明：本文为CSDN博主「Elong_Deo」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq525099302/article/details/53996344
```