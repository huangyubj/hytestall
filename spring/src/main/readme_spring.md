##### spring核心组件
>- Spring core
>- Spring web
>- Spring IOC
>- Spring AOP
>- Srping MVC
>- Spring ORM
>- Spring JMS
>- Spring Test

#### 常用注解
>- `@Configuration`，注解配置类
>- `@ComponentScan`，指定扫包范围
>>+ useDefaultFilters,默认是true,扫描所有组件,使用自定义扫描范围，要改成false
>>+ excludeFilters = Filter[] 指定扫描的时候按照什么规则排除那些组件
>>+ includeFilters = Filter[] 指定扫描的时候只需要包含哪些组件
>>+ 过滤类型分为以下五种
>>>+ FilterType.ANNOTATION：按照注解
>>>+ FilterType.ASSIGNABLE_TYPE：按照给定的类型；比如按BookService类型
>>>+ FilterType.ASPECTJ：使用ASPECTJ表达式
>>>+ FilterType.REGEX：使用正则指定
>>>+ FilterType.CUSTOM：使用自定义规则，自已写类，实现TypeFilter接口
```
@ComponentScan(value="com.test.hy", inlucdeFilters={
	@Filter=(type=FilterType.xxx, classes={MyFilter.class})
}, useDefaultFilters=false)
```
>- `@Scope("prototype")`,实例作用域范围
>>+ singleton 默认，单利；
>>+ prototype多实例；
>>+ request，一次请求；
>>+ session 同一个session创建
>- `@lazy` 懒加载
>- `@Conditional`  条件注册bean，注入实现了Condition接口的类来进行过滤
>- 导入自定义bean的种方式
>>+ 扫包+注册组件注解（@Controller、@Service、@Repository、@Component,一般针对自己写的类
>>+ @Bean，一般用来导入第三方实例
>>+ `@Import`({Dog.class, JamesImportSelector.class, JamesImportBeanDefinitionRegistor.class}) <br>
>>可以导入直接类名、实现ImportSelector 接口，实现ImportBeanDefinitionResgistrar 接口
>>+ 使用spring提供的FactoryBean（工厂bean），自定义实现getObject、isSingleton等方法
>- `@Value`赋值,
>>+ 基本字符赋值：张三
>>+ springEL表达式赋值#{20-2}
>>+ 读取配置文件属性赋值${redis.id}
>- `@PropertiySource`(value=("classpath:/test.properties")) 加载配置文件
> app.getEnvironment().getProperty("redis.id")也能获取配置属性
>- 自动装配bean的几种方法
>>+ `@AutoWire`自动装配，默认优先按照`类型`去装配
>> 属性默认required=true.必须找到bean，否则报异常未注册实例, 参数、
>> 方法、构造方法 注解时，参数实例会会通过容器赋值，在spring创建该对象自动完成赋值
>>>+ `@Primary` 进行注解的bean将优先加载
>>>+ `@Qualifier`加载指定的bean
>>+ `@Resource` 属于java规范的自动装载bean，但不支持@Primary和@Qualifier
>>+ `@Inject`，需要引入第三方包，不支持required=false,  但支持primary
>- AOP相关注解
>>+ `@EnableAspectJAutoProxy` 开启aop注解功能
>>+ `@Aspect` 声明一个类为切面类
>>+ `@Before`("execution(public int com.hy.aspect.Calculator.div(int, int)")
>>+ `@After`----被注解方法可通过JoinPoint参数拿到方法名、参数等信息
>>+ `@AfterReturing`(value="pointcut()", returning="result", throwing="exception",
returning="result")---可通过参数result拿到返回对象
>>+ `@AfterThrowing`(value="",throwing="e")--可通过参数 e 拿到异常信息
>>+ `@Around`,环绕
>>+ `@Pointcut`("execution(public int com.enjoy.cap10.aop.Calculator.*(..))")
注解定义一个公共切入方法，抽取公共切入点表达式，使用 @Before("pointCut()")

#### bean的生命周期，
>- xml中指定`init-method`和`destory-mothod`
>- 注解指定@Bean(`initMethod`="init", `destroyMethod`="destroy")
>- bean实现 `InitializingBean` 和 `DisposableBean`接口构造方法之后，容器销毁之前
>- java规范注解`@PostConstruct`: 在Bean创建完成,且属于赋值完成后进行初始化,属于JDK规范的注解
`@PreDestroy`: 在bean将被移除之前进行通知, 在容器销毁之前进行清理工作



#### spring核心类/接口
#####BeanFactory
> getBean反射获取实例
##### BeanPostProcessor
> 允许自定义修改新bean实例的工厂钩子，用来增强bean，AOP、事务等都是通过实现
> BeanPostProcessor进行增强
>>- postProcessBeforeInitialization()是bean初始化之前，用来增强bean
>>- postProcessAfterInitialization()是bean初始化之后处理，
##### Aware
> 用来获取容器中的属性，如下等
>>+ EnvironmentAware, 环境变量
>>+ EmbeddedValueResolverAware，resolving embedded definition values
>>+ ResourceLoaderAware,资源加载器
>>+ ApplicationEventPublisherAware，事件发布器
>>+ MessageSourceAware,国际化
>>+ ApplicationContextAware,应用上下文
>>+ BeanClassLoadAware 类加载器
>>+ BeanNameAware

##### RootBeanDefinition，bean属性定义
##### BeanWrapper，包装bean实例
##### Ordered 控制增强器的执行顺序
##### Advisor 增强器创建的各种增强点

#### spring容器启动过程
1. `prepareRefresh`（prepare 准备）校验环境、属性、数据正确性
2. `obtainFreshBeanFactory`（obtain (尤指经努力) 获得，赢得; 存在; 流行; 沿袭）
 获取BeanFactory实例，
3. `prepareBeanFactory`（prepareBF 再准备BeanFactory的后续工作）包换注册classLoader类加载器
resource环境 Aware接口等
4. postProcessBeanFactory（postProcessB spring预留增强BeanFactory接口）不重要
5. `invokeBeanFactoryPostProcessors` （invokeBFPP 调用执行BeanFactory的增强器，
包括用户自定义扩展的增强器）
6. `registerBeanPostProcessors`（registBPP 注册其他bean增强器，包括`AOP`，
`Transaction`等都在此注册，注册按照PriorityOrdered、Ordered、None进行注册，）
7. initMessageSource（国际化，初始化信息源，注册`messageSource`的bean实例可实现自定义国际化）
8. initApplicationEventMulticaster（初始化事件派发器，
注册applicationEventMulticaster实例可实现自定义事件派发器）
9. onrefresh（预留自动以扩展接口）不重要
10. registerListeners（给容器中将所有项目里面的ApplicationListener注册进来）
11. `finishBeanFactoryInitialization`（finish init 加载bean，`单实例非懒加载bean`，将在此初始化）
>- 先获取`RootBeanDefinition`bean实例定义
>- 获取一个BeanWrapper，包装bean
>- 正式实例化前
>- initializeBean进行bean实例化
>>+ Aware接口调用
>>+ BeanPostProcessor 前置增强调用
>>+ bean实例化前被调用，如：init-method、InitializingBean
>>+ BeanPostProcessor后置增强调用，如：AOP、Transaction等
>>>* getAdvicesAndAdvisorsForBean,获取所有Advisors增强到bean上，供方法增强
>时使用
12. finishRefresh（finish 结束）

##### REST风格传递参数
```
// {blogName}、{year}作为占位符!!!
	/* =========/testPathVariable/codinglin/2019
	 * =========/testPathVariable/{blogName}/{year}
	 * @PathVariable注解：将Restful风格请求中的参数，映射给方法的形参
	 */
	@RequestMapping("/testPathVariable/{blogName}/{year}")
	public String testPathVariable(@PathVariable("blogName")String blogName,@PathVariable("year")Integer year) {
		System.out.println("blogName:"+blogName+"===url:"+year);
		return "success";
	}
```