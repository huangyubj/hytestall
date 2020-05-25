--
-- 锁定库存 + 1，如果已处理库存+锁定库存 == 库存，则秒杀完成
--
local totalKey = tostring(ARGV[1])
local soldKey = tostring(ARGV[2])
local soldLockKey = 'locked_' + soldKey
local total = tonumber(redis.call('get', totalKey))
local sold = tonumber(redis.call('get', soldKey))
local soldLock = redis.call('get', soldLockKey)
-- if
local strret = tostring(ret)