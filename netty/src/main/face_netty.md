1. BIO、NIO 和 AIO 的区别?
>- BIO,同步阻塞式IO，面向流，一个连接来创建一个线程进行处理
>>- 伪异步IO，线程池管理线程进行处理
>- NIO,同步非阻塞式IO，面向缓冲区，一个连接一个线程，通过Selector多路复用器
轮询所有操作状态的改变来管理N个连接和事件,Channel进行读写
>- AIO，异步非阻塞IO，无需一个线程去轮询所有IO操作的状态改变，在相应的状态改变后，
系统会通知对应的线程来处理

2. NIO的组成
>- Selector,多路复用器，负责管理channel的所有事件
>- Buffer，缓冲区，数据流入、流出所必须的区域
>- Channel，通道，用来进行读写

3. netty的特点
>- netty是一个采用多线程Reactor模式的高性能、异步事件驱动的NIO框架，提供了对TCP、UDP、文件传输的支持。<br>
>- 简化了NIO的使用，封装了多种编解码器、粘包/半包处理、心跳检测、http/ssl等开箱即用的处理器
>- 缓冲区通过内存池的方式循环利用，ByteBuf通过引用计数器及时申请释放不再引用的对象，降低了 GC 频率
>- 大量使用了 volitale、使用了 CAS 和原子类、线程安全类的使用、读写锁的使用

4. netty的线程模型
> netty可以选择构建两个线程池，boss线程池和worker线程池
>- boss线程池用于mainReactor线程负责建立连接，subReactor线程负责监听事件
>- worker线程负责handller的处理

5. 什么是粘包半包，怎么处理
> 导致粘包/半包的原因如下
>- 由于TCP采用nagel算法为了减少网络开销，每一次都在MISS大小想尽可能嘴大的发送数据包
，会对数据进行拆分发送
>- 接收数据的缓冲区在大小不足时会进行拆包处理

> netty提供了4个已经封装好的处理器解决粘包/半包问题
>- FixedLengthFrameDecoder(int frameLength),固定长度拆包，适用于数据包长度固定
>- LineBasedFrameDecoder(int maxLength),按行拆包,数据("\r\n")结尾
>- DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf delimiter), 固定分隔符拆包，数据必须以分隔符结尾
>- LengthFieldBasedFrameDecoder,基于长度域拆包

6. 了解哪些序列化协议
> 数据包需要以二进制形式在网络传输中传输，所以数据需要编码为二进制流传输，接收在进行
解码使用<br>
>> 影响序列化的几个因素有
>>- 序列化后的码流大小,影响网络开销
>>- 序列化的性能，影响CPU的资源占用
>>- 是否支持跨语言，影响开发语言的切换和平台的对接

>>常见的序列化方式有以下几种
>>- java自带序列化，无法跨语言、码流太大、序列化的性能差
>>- xml，可读性好，不能序列化方法，文件大，当做配置文件存储数据，实时数据转换。
>>- JSON,兼容性高、格式简单、码流小、扩展性好，描述性差，额外空间开销大，适用于
跨防火墙访问、可调式性要求高、基于 Web browser 的 Ajax 请求、传输数据量相对小，实时性要求相对低(例如秒级别)的服务
>>- FastJSON，简单易用，速度快，适用场景:协议交互、Web 输出、Android 客户端
>>- Thrift，不仅是序列化协议，还是一个 RPC 框架，体积小, 速度快、支持多种语言和
丰富的数据类型、对于数据字段的增删具有较强的兼容性、支持二进制压缩编 码。
缺点:使用者较少、跨防火墙访问时，不安全、不具有可读性，调试代码时相对困难、
不能与其他传输层协议共同使用(例如 HTTP)、无法支持向持久层直接读写数据，即：
不适合做数据持久化序列化协议。适用场景:分布式系统的 RPC 解决方案
>>- Protobuf，将数据结构以.proto 文件进行描述，序列化后码流小，性能高、
结构化数据存 储格式(XML JSON 等)、通过标识字段的顺序，可以实现协议的前向兼容、
结构化的文档 更容易管理和维护。缺点:需要依赖于工具生成代码、支持的语言相对较少，
官方只支持 Java 、C++ 、python。适用场景:对性能要求高的RPC调用、
具有良好的跨防火墙的访问 属性、适合应用层对象的持久化
>>- protostuff 基于 protobuf 协议，但不需要配置 proto 文件，直接导包即可
>>- Hessian 采用二进制协议的轻量级 remoting onhttp 工具
>>- MessagePack

7. Netty 的零拷贝实现
> Netty 接收或发送Bytebuf采用DirectMemory，不要拷贝到localMemory中，减少堆拷贝的过程。
>- CHannelConfig创建ByteBufAllocator默认使用Drect Buffer
>- CompositeByteBuf可以合并多个ByteBuf为一个写入/写出，内部个个buf是独立的
>- FileRegion包装的FileChannel.tranferTo可直接将文件缓存区中的数据发送到指定Channel，
避免循环write导致的内存拷贝
>- 
8. netty的高性能表现在哪些方面
>- 心跳，服务端用来定时清除闲置会话，客户端用来检测测会话是否断 开，是否重来，检测网络延迟
>- 串行无锁化设计,避免线程的竞争、上下文切换
>- 可靠性，读写超时处理、内存池重复使用、优雅停机、资源释放
>- 安全性，支持的安全协议:SSL V2和V3，TLS，SSL单向认证、双向认证和第三方CA 认证
>- TCP 参数配置

9. NIOEventLoopGroup 源码







