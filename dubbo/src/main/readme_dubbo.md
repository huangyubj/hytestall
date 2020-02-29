
![Dubbo 结构图](./dubbo-architecture.jpg)

|节点|	角色说明|
|---|---|
|Provider	|暴露服务的服务提供方
|Consumer	|调用远程服务的服务消费方
|Registry	|服务注册与发现的注册中心
|Monitor	|统计服务的调用次数和调用时间的监控中心
|Container	|服务运行容器
##### 调用关系说明
0. 服务容器负责启动，加载，运行服务提供者。
1. 服务提供者在启动时，向注册中心注册自己提供的服务。
2. 服务消费者在启动时，向注册中心订阅自己所需的服务。
3. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
4. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
5. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

![Dubbo 未来可能的一种架构](./dubbo-architecture-future.jpg)

|节点|	角色说明|
|---|---|
|Deployer	|自动部署服务的本地代理
|Repository	|仓库用于存储服务应用发布包
|Scheduler	|调度中心基于访问压力自动增减服务提供者
|Admin	|统一管理控制台
|Registry	|服务注册与发现的注册中心
|Monitor	|统计服务的调用次数和调用时间的监控中心

![Dubbo 总体架构图](./dubbo.png)
> Dubbo 总体架构分为10层，左蓝是消费者使用的接口，右绿为服务提供方使用接口，中轴线是双防均用的接口
>- 服务接口层（Service）：该层是与实际业务逻辑相关的，根据服务提供方和服务消费方的业务设计对应
> 的接口和实现。
>- 配置层（Config）：对外配置接口，以ServiceConfig和ReferenceConfig为中心，可以直接new配置类，
> 也可以通过spring解析配置生成配置类。
>- 服务代理层（Proxy）：服务接口透明代理，生成服务的客户端Stub和服务器端Skeleton，
> 以ServiceProxy为中心，扩展接口为ProxyFactory。
>- 服务注册层（Registry）：封装服务地址的注册与发现，以服务URL为中心，扩展接口为RegistryFactory、
> Registry和RegistryService。可能没有服务注册中心，此时服务提供方直接暴露服务。
>- 集群层（Cluster）：封装多个提供者的路由及负载均衡，并桥接注册中心，以Invoker为中心，
> 扩展接口为Cluster、Directory、Router和LoadBalance。将多个服务提供方组合为一个服务提供方，
> 实现对服务消费方来透明，只需要与一个服务提供方进行交互。
>- 监控层（Monitor）：RPC调用次数和调用时间监控，以Statistics为中心，扩展接口为MonitorFactory、
> Monitor和MonitorService。
>- 远程调用层（Protocol）：封将RPC调用，以Invocation和Result为中心，扩展接口为Protocol、Invoker
>和Exporter。Protocol是服务域，它是Invoker暴露和引用的主功能入口，它负责Invoker的生命周期管理。
> Invoker是实体域，它是Dubbo的核心模型，其它模型都向它靠扰，或转换成它，它代表一个可执行体，
> 可向它发起invoke调用，它有可能是一个本地的实现，也可能是一个远程的实现，也可能一个集群实现。
>- 信息交换层（Exchange）：封装请求响应模式，同步转异步，以Request和Response为中心，
> 扩展接口为Exchanger、ExchangeChannel、ExchangeClient和ExchangeServer。
>- 网络传输层（Transport）：抽象mina和netty为统一接口，以Message为中心，扩展接口为Channel、
> Transporter、Client、Server和Codec。
>- 数据序列化层（Serialize）：可复用的一些工具，扩展接口为Serialization、 ObjectInput、
> ObjectOutput和ThreadPool
