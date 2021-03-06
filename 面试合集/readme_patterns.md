##### 六大基础原则    单一职责（dao/controller/service,服务分开）
- 里氏替换（子类可以扩展父类功能，但不改变父类功能）
- 依赖倒置（面向接口编程）
- 接口隔离（设计接口功能尽量细粒度，最小功能原则）
- 迪米特（降低耦合，局部变量中不引入新的类）
- 开闭原则（对扩展开放，对修改关闭，不修改原有代码扩展功能）

#####
1. 解耦，对象创建和使用过程分离
2. 对象创建包含init方法的调用，黑盒创建过程
3. 面向接口编程，使用者只管使用，只知其接口，不知其实现类

#### 对象创建型模式(create)
- 单例模式(single)
> 饿汉式/懒汉式（线程安全：双重校验/静态内部类懒汉加载）
- 简单工厂(simple factory)
> 一个工厂什么都创建，要什么工厂创建什么（对象少使用，方便，对象一多，修改查看使用麻烦）
- 工厂模式(factory)
> 产品分类，一个工厂（interface）创建一系列水果（实例），有新的种类，直接扩展工厂
- 抽象工厂(abstract factory)
> 统一类工厂合并（苹果工厂/苹果箱包装）Furute/Package，spring通过DI注入实现。
- 建造者模式(build)
> 将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示,
> 水果套餐组合/电脑拼装组合，说到底就是组合构建，简化复杂的创建过程
- 原型模式
> 创建对象的种类，并且通过拷贝这些原型创建新的对象
>  1、性能提高。 2、逃避构造函数的约束。
##### 创建模型总结
|类型 |  | 说明  |
|------|-------:|-----: |
|简单工厂||数量少使用
|工厂模式||数量多分类使用
|抽象工厂||简单组合使用
|建造者||复杂组合使用
|原型|性能提高。逃避构造函数的约束|消耗资源多多，new繁琐

`#简单工厂（数量少使用）-->工厂模式（数量多分类使用）-->抽象工厂（简单组合使用）-->建造者模式（复杂组合使用）
    #从单个接口-->多个接口-->组合接口的使用`


#### 结构型模式(structure)
> `适配和桥接都是把对象传入组合起来使用，桥接的目标是分离，适配的目标是合并`
- 适配器模式(adapter)
>+ #1)接口适配，A类适配（实现）AB接口，对外提供AB接口方法
>+ #2)类适配，构造注入，调用适配方法
>+ 匹配不匹配，通过转接头适配(2)3))
- 桥接模式(bridge)
>+ 通过提供抽象化和实现化之间的桥接结构，解耦
>+ 构造注入，组合N种结果（桥A通向A1 A2 A3多地，桥B通向B1 B2 B3 多地，A引入B接口/抽象类，A就能与B组出从An-->Bn的N种组合）
- 装饰器模式(decorator)
> io缓冲流的实现思路，通过装饰原有类，扩展功能，装饰类也会实现同一接口,动态增加功能，动态撤销。
- 代理模式(proxy)
> 为其他对象提供一种代理以控制对这个对象的访问。
- 组合模式(composite)
> 如tree，通过组合包含，组织结构 <br>
> 将对象组合成树形结构以表示"部分-整体"的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
- 外观模式(facade)
> 当访问流程比较复杂时，将流程封装成一个接口，提供给外部使用<br>
> 为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
#### 行为设计模式(action)
    #模板和策略都是
- 模板方法模式(template)
> 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的  
> 结构即可重定义该算法的某些特定步骤<br>
> 如： spring种的Template
- 策略模式（strategy）
> 定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。<br>
> 根据不同的策略来处理不同算法分支<br>
> 主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
- 责任链模式（chain）
>+ 一个责任链client，注册多给责任类，链条传递执行（多个优惠卷传递执行）
>+ 避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，  
> 并且沿着这条链传递请求，直到有对象处理它为止<br>
>+ 主要解决：职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，  
> 无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了。<br>
> 如：netty的handler，spring的filter
- 观察者模式（Observer）
> 定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新
>* 订阅/发布，JDK提供了Observeable/Obsever 来实现观察者模式
>* 主要解决：一个对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。
- 命令模式(command)
> 将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化