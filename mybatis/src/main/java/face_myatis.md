1. mybatis简介，(所有相关回答均可从此开始起手式)
> MyBatis 是一个可以自定义 SQL、存储过程和高级映射的持久层框架,
>- 高版本的JDBC已采用`SPI加载驱动`
>- Cache采用`装饰器模式`封装了如FIFO、LRU、Schedule等缓存器,利于针对cache扩展增强
>- 日志Logging采用`适配器模式`扩展
>- Excutor采用`模版模式`进行扩展不同的Excutor
2. mybatis的缓存
> MyBatis 的缓存分为`一级缓存`和`二级缓存`,一级缓存放在`session`里面，在select上通过flushCache开启和关闭,默认为true,
> 二级缓存放在它的`命名空间`里,使用二级缓存属性类需要实现
> 序列化,可在它的映射文件中配置<cache/>
> ，当进行Insert、update、delete操作会清除对应缓存
3. mybatis如何进行分页
> mybatis使用RowBounds进行分页，也可以使用sql limit进行分页，或使用分页插件
4. mybatis的插件运行原理
>+ Mybatis 仅可以编写针对 `ParameterHandler、ResultSetHandler、StatementHandler、
> Executor` 这 4 种接口的插件，Mybatis 通过动态代理，为需要拦截的接口生成代理对象
> 以实 现接口方法拦截功能，每当执行这 4 种接口对象的方法时，就会进入拦截方法，
> 具体就是 InvocationHandler 的 invoke()方法，当然，只会拦截那些你指定需要拦
> 截的方法
>+ 实现 Mybatis 的 `Interceptor 接口并复写 intercept()`方法，然后在给插件编写注解，
>指定 要拦截哪一个接口的哪些方法即可
5. Mybatis 动态 sql 是做什么的?都有哪些动态 sql?
> Mybatis 动态 sql 可以让我们在 Xml 映射文件内，以标签的形式编写动态 sql，
> 完成逻辑判断和动态拼接 sql 的功能,提供了9种动态sql标签
>* trim、where、set、foreach、if、choose、when、otherwise、bind
6. #{}和${}的区别是什么?
> #{}是预编译处理，可以防止sql注入，${}是字符串替换
7. Mybatis 的延迟加载是什么
> Mybatis 仅支持 association 一对一关联对象和 collection 一对多关联集合对象的延迟加载
> 。可以通过 `lazyLoadingEnabled`=true|false来配置是否启用延迟加载<br>
> 它是使用 CGLIB 创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，在单独发送
> 事先保存好的查询给对象赋值
8. MyBatis 的好处是什么?
>* 独立出来的xml编写sql，更加的便利和灵活，
>* 封装了底层 JDBC API 的调用细节，并能自动将结果集转换成 Java Bean 对象，
> 大大简化了 Java 数据库编程的重复工作
9. MyBatis 实现一对一有几种方式?
> 有`联合查询`和`嵌套查询`
>+ 联合查询是几个表联合查询,只查询一次,通过在 resultMap 里面 配置 association
>节点配置一对一的类就可以完成，关联查询适合数据少
>+ 嵌套查询是先查一个表,根据这个表里面 的结果的外键 id,去再另外一个表里面查询数据,
> 也是通过 association 配置,但另外一个表的 查询通过 select 属性配置。
>>嵌套查询又分为`嵌套结果`和`嵌套查询`
>>+ 嵌套结果是使用嵌套结果映射(另一个resultMap)来处理重复的联合结果的子集
>>+ 嵌套查询是通过执行另外一个 SQL 映射语句来返回预期的复杂类型
10. Mybatis 是如何将 sql 执行结果封装为目标对象并返回的?都有哪些映射形式?
> 可以使用<resultMap>标签或者直接写查询字段
11. 当实体类中的属性名和表中的字段名不一样，如果将查询的结果封装到指定 pojo
> 可以采用sql别名方式或者resultmap中进行映射
12. Mybatis 都有哪些 Executor 执行器
> Mybatis 有三种基本的 Executor 执行器，`SimpleExecutor`、`ReuseExecutor`、
> `BatchExecutor`，可在配置文件中指定Executor或创建session时指定ExecutorType.xxx
>- SimpleExecutor:每执行一次 update 或 select，就开启一个 Statement 对象，
> 用完立刻关闭 Statement 对象
>- ReuseExecutor:执行 update 或 select，以 sql 作为 key 查找 Statement 对象，
> 存在就使用，不存在就创建，用完后，不关闭 Statement 对象， 而是放置于Map
>- BatchExecutor是完成批处理
13. resultType resultMap 的区别?
>- 类的名字和数据库相同时，可以直接设置 resultType 参数为 Pojo 类
>- 若不同，需要设置 resultMap 将结果名字和 Pojo 名字进行转换
14. Mybatis 源码解读
>- 已二进制字节流的形式读取mybatis-config文件，转为document可操作对象，
>- 将配置中的configuration内容解析为一个Configration对象，含 settings(先加载默认，在进行覆盖)、properties、typeAliases、plugins
>- XmlMapperBuilder解析mapper文件最终注册到MapperRegistry中
>- 通过解析注册到MapperRegistry获取MapperProxyFactory
>- 通过反射获取一个Mapper实例MapperProxy代理Mapper
>- 通过MapperMethod方法执行(此处mapperMethod做了缓存)
>- MapperMethod.execute 通过操作类型(增删改查)执行了sqlsession对应的方法
>- sqlsession通过Executor模版执行操作(Spring的SqlSessionTemplate也是通过对SqlSession的实现)
>- 从BaseExecutor能看到增、删、改都会清空缓存,查询会优先查询缓存
>- Executor操作通过
>- StatementHandler处理操作
>- ParameterHandler处理参数
>- ResultSetHandler处理查询结果