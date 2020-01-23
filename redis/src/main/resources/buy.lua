if tonumber(redis.call("get", "ticktsnum")) > 0 then
    return redis.call("decr", "ticktsnum")
else
    return 0
end