#### 数据库事物
>ACID
>+ 原子性，每一个操作都是原子操作
>+ 隔离性(isolation)
```mysql
show variable like '%tx_isolation%'
set session transaction isolation level read uncommit
```

1. 未提交读(READ-UNCOMMITED)
2. 提交读(READ-COMMITED),解决脏读,优先
3. 可重复读(REPEATABLE-READ)解决不可重复读
4. 串行读(SERIALIZABLE)解决幻读，锁表
>+ 一致性
>+ 持久性

#### 分布式事物
**1111**