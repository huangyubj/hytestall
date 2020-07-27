1. Zookeeper是个啥
> ZooKeeper 是为分布式系统中各个服务主机提供高可用、高性能、具备严格顺序访问控制的协调服务,
>- 提供一个`多层级的节点命名空间`znode,采用`watch机制`监听znode，当znode节点数据变更时通知
client做出对应业务上的变更
>- 节点属性zxid的递增，保证了事务顺序的一致性，
2. znode的类型
>- PERSISTENT 持久化节点
>- PERSISTENT_SEQUENTIAL 持久化有序节点
>- EPHEMERAL 	临时节点,临时节点断开会删除
>- EPHEMERAL_SEQUENTIAL	临时有序节点

3. zk可以用来做什么
>- `负载均衡`，客户端从zk获取服务信息时，按照负载策略返回地址
>- `master选举`(recipes下有demo),通过ZAB协议利用节点信息投票,选取leader节点
>- `集群管理`，临时有序节点存储broker信息,通过心跳检测,当断开连接临时节点删除，新的服务
启动，注册到指定临时节点中
>- `分布式锁`(recipes下有demo)
>>- 利用节点不可重复创建来实现锁，创建成功获取锁，失败retry，直到获取到锁
>>- 高性能的分布式锁采用一个有序节点来做一个锁队列,进行锁分配，头节点(即序号最小)为自己时获取锁成功，具体同AQS原理
>- `配置管理`，利用节点存储配置信息，watch节点进行配置变更处理
>- `分布式队列`(recipes下有demo)，持久化有序节点按序消费
>- `数据发布和订阅`，对节点进行watch
>- `命名服务`，利用节点存储服务信息 通过指定的名字来获取资源或者服务的地址

4. zk的数据复制
> 复制能提高容错性、系统扩展能力、性能
>- `容错`:一个节点出错，不致于让整个系统停止工作，别的节点可以接管它的工作;
>- `提高系统的扩展能力` :把负载分布到多个节点上，或者增加节点来提高系统的负载能力;
>- `提高性能`:让客户端本地访问就近的节点，提高用户访问速度

>可以采用写主和写任意模式，zk采用基于ZAB理论实现数据复制和master选举
>- 写主(WriteMaster) 方式，对数据的修改提交给指定的节点。
>- 写任意(Write Any)方式对数据的修改可提交给任意的节点
5. zk的工作原理
> zk通过zab协议来保证server之间的同步，zab协议分为恢复模式和广播模式
>- `恢复模式`是master服务挂了后进行master选举
>- `广播模式`是数据的同步

6. zk下server工作状态
>每个 Server 在工作过程中有三种状态:
>- LOOKING:当前 Server 不知道 leader 是谁，正在搜寻
>- LEADING:当前 Server 即为选举出来的 leader
>- FOLLOWING:leader 已经选举出来，当前 Server 与之同步

7. zk 如何选取leader
> 当 leader 崩溃或者 leader 失去大多数的 follower，这时 zk 进入恢复模式,
>- 基于 `basic paxos`算法,
>>+ 发起询问zxid，收到返回计算出最大zxid
>>+ 与最大zxid一致的server参与选举，当某个server得到票数超过一半，则选举为leader，否则继续
>- 基于 `fast paxos`算法,
>>+ 发起选举提议，其他服务解决 epoch 和 zxid 的冲突，并接受对方的提议，当超过半数的server
返回接收提议则选举成功，否则继续下一轮

8. zk同步流程
> 选完 Leader 以后，zk 就进入状态同步过程。
>- Leader 等待 server 连接;
>- Follower 连接 leader，将最大的 zxid 发送给 leader;
>- Leader 根据 follower 的 zxid 确定同步点;
>- 完成同步后通知 follower 已经成为 uptodate 状态;
>- Follower 收到 uptodate 消息后，又可以重新接受 client 的请求进行服务了。

9. 为什么要有leader
> 其他server可以共享结果，减少重复计算，提高性能

10. zk节点宕机如何处理
> 如果是leader宕机，则进行选举leader，follower集群继续使用，当超过半数宕机，
则将停止服务，所以follower宕机咋办，赶紧检查宕机原因，起来服务

11. zk和Nginx的负载区别
> zk 的负载均衡是可以调控，
nginx 只是能调权重，其他需要可控的都需要自己写插件;但是 nginx 的吞吐量比 zk 大很多，应该说按业务选择用哪种方式}{

12. zk的wath机制
>- zk的watch是一次性的，只会出发一次
>- Zookeeper 只能保证最终的一致性， 而无法保证强一致性
>- 通过getData、exists、getChildren注册watch
>- create、delete、setData 可出发watch
>- 服务连接可以watch，bu丢失连接没法watch，so需要心跳检测，就会存在弱一致性

13. 2PC、3PC
>都存在 单点问题、同步阻塞、数据不一致、容错不好

> 2PC(two-phase-commit两阶段提交)简单容易实现
>- 第一阶段 prepare 双方进行确认，会进行加锁
>- 第二阶段 commit 双方进行确认提交

> 3PC(相比较减少了单点故障，效率也更高)
>- cancommit，不锁表，避免等待响应时锁表阻塞
>- precommit，锁表
>- docommit，
14. CAP、BASE、ZAB
> CAP理论(三者不可同时兼得,需要做的是保证分区容错性的前提下,均衡一致性与可用性)
>- 一致性(强一致性、弱一致性)---需要考虑保证 最终一致性
>- 可用性(一直处于可用)---需要考虑保证基本可用,降级处理
>- 分区容错性(不出现网络分区现象)

> BASE理论
>- Basically available 基本可用
>- soft state 软状态,即三态的中间态,数据同步过程存在延迟，不影响系统可用性(消息队列排队)
>- eventually consistent 最终一致性,数据经过一定时间同步，能够达到最终一致的状态