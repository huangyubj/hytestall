1. Synchronized原理
> Syn是由JVM实现的一种互斥同步的方式，通过指令`monitorenter`和`monitorexit`两个字节码
> 指令控制，执行menter指令时，如果这个对象没有锁定或者当前线程已经锁定这个对象，会把锁计数器
>+1，当执行mexit时，会-1，当计数器为0时，锁就被释放了
2. Syn锁定的对象
> Syn可以锁定`对象`、锁定`代码块`、锁定`方法`
3. jvm对java原生锁进行哪些优化
>- 进行短时间自旋`减少了用户态和到内核态的切换`，
>- 通过`偏向锁`(在锁头标记线程ID)、轻量锁（CAS）、重量锁的升级、降级减少锁的竞争开销
4. 为什么Syn是非公平锁
> 非公平主要表现在获取锁的行为上，并非是按照申请锁的时间
> 前后给等待线程分配锁的，每当锁被释放后，任何一个线程都有机会竞争到锁，
> 这样做的目的是为了提高执行性能，缺点是可能会产生线程饥饿现象。
5. 什么是锁消除和锁粗化
>- 锁消除是JVM在编译时检测到不可能存在锁竞争的情况，进行锁消除，主要根据逃逸分析
>- 锁粗化指减少没必要的重复加锁和解锁，增大锁的作用域
6. 为什么syn是悲观锁，乐观锁是什么，CAS原理和特性是什么，
>- syn不管是否有竞争，任何数据都必须进行加锁、用户核心态转换、维护锁计数器、检查阻塞等情况
>- 乐观锁的核心算法是CAS（compare and swap），通过volatile在内存中公开一个对象，
> 当且仅当`内存值`为`预期值`时，才赋予`新值`，否则自旋继续进行非阻塞式同步，
>- CAS优缺点：提高了并发性能，但只能保证共享一个变量的原子操作，存在ABA问题，需要用版本戳，长期
>- 自旋产生性能消耗
7. Syn和显示锁Lock
> 显示锁具有更高的灵活性，提供中断、获取超时时间等操作方法，实现基于AQS实现，<br>
> Syn竞争不激烈的情况下效率高于ReetrantLock<br>
> ReetrantLock为公平锁，
8. AQS是什么
> AQS一种实现`阻塞锁`和一系列`依赖FIFO`等待队列的`同步器`的框架,
>- 定义了一个volatile int state 变量，表示同步状态,=0时表示没有锁，加锁对state进行+1，释放锁进行-1<br>
>- 构建Node双向链表同步队列，进行锁排队(so是个公平锁)<br>
>> 一. 进入`acquire`可以看到`addWaiter()`将本次aquire请求打包为一个node添加到链表中  <br>
>> 二.`自旋`尝试获取锁，当前驱结点为`头结点`，参与锁竞争<br>
>> 三.否则需要会被LockSupport.`park`(this) 进行阻塞<br>
>> 四.当release的时候，LockSupport.`unpark`(s.thread)释放当前节点的next节点
9. 常用的JUC并发工具
> CountDownLatch、 CyclicBarrier、 Semaphore 等同步工具<br>
> ConcurrentHashMap、有序的ConcunrrentSkipListMap、CopyOnWriteArrayList等安全容器<br>
> ArrayBlockingQueue、SynchorousQueue等阻塞队列<br>
> 强大的 Executor 框架,各种线程池
10. ReadWriteLock 和 StampedLock
> StampedLock是1.8提供的读写锁，支持读优化，validate()方法可以确认是否进如了读模式，避免重复锁开销
11. 如何让线程彼此同步，有哪些同步器
>- `CountDownLatch`，一个/多个线程通过await进入等待，在通过countDown进行释放，当计数器变为0后可执行
>- `CyclicBarrirer `,一组线程全部到达await()屏障后放开屏障执行
>- `Semaphore` 构建固定连接池，进行acquire池中没有连接进入等待，当release释放会唤醒等待，通常用在流量控制
12. java中线程池是如何实现的
> 通过构建`一组worker线程`来执行`BlockingQueue中`的`Runnable`<br>
> 通常需要corePoolSize、最大线程数、超过核心线程数的线程存活时间、工作队列<br>
> 常用线程池有以下这些
>- SingleThreadExecutor，单线程串行工作
>- FixedThreadPool,固定大小线程池
>- ScheduledThreadPool,执行一些周期性任务线程池
13. volatile的特点
> volatile是jvm提供的轻量同步机制，volatile修饰的变量对所有线程都是可见的，能够阻止
> JVM进行重排序优化，但不保证代码执行顺序(遵循happen before 原则)
14. ThreadLocal 怎么解决并发安全
> java提供的一种线程私有信息机制，线程为key，使用需要注意remove，否则会一直伴随着
> 线程共生，造成OOM
15. 影响线程性能有哪些因素,怎么优化
>- 上下文切换(线程之间的切换)
>- 内存同步(锁指令,需要刷新缓存)
>- 阻塞(导致挂起，唤醒，重新排队，导致多余上下文切换)<br>

> 优化方案
>- 减小锁粒度
>- 减小锁的范围(如：锁代码块)
>- 锁分段(如：CurrentHashMap)
>- 替换独占锁(根据实际情况采用CAS、读写锁、并发容器等工具)
16. 线程之间怎么通信
>- 共享内存
>- 消息传递java中通常通过 wait()和notify()传递消息

17. WAITING状态
>- 线程通过Object.wait()方法进入WAINTING状态，只有当Object.notify()/Object.notifyAll()
>的时候唤醒，进入RUNNABLE
>- 线程通过Thread.join() 将自己加入到线程B中，进入WAITING状态，直到线程B执行完成，当前
>线程进入RUNNABLE
>- ThreadSupport.park(thread),指定线程进入WAITING状态，通过ThreadSupport.unPark(thread)
> 唤醒进入RUNNABLE状态