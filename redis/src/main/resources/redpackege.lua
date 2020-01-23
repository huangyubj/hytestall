--
-- Created by IntelliJ IDEA.
-- User: huang
--redpkpool
--redpkuserpool
--redpkdetailpool
--
local userid = KEYS[1]
local readpckname = KEYS[2]
local usersetname = KEYS[3]
local detailname = KEYS[4]
-- is exist
local saddcode = redis.call('sadd', usersetname, userid)
if saddcode == 1 then
    --get
    local info = redis.call('lpop', readpckname)
    if info then
        return info
    else
--        redis.call("rpush", detailname, info)
        return 'none'
    end
else
    return saddcode
end


