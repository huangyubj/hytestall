--
-- Created by IntelliJ IDEA.
-- User: huang
-- 释放锁
--
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return redis.call("get",KEYS[1])
end