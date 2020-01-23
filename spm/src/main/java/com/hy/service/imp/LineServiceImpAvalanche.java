package com.hy.service.imp;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过锁防止缓存雪崩
 */
@Service("lineServiceAvalance")
public class LineServiceImpAvalanche extends LineServiceImp implements LineService {
    @Autowired
    private RedisTemplate redisTemplate;

    //存放锁，
    private ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();
    @Override
    public CscLine queryLine(long lineId) {
        String cachecode = CACHENAME + lineId;
        CscLine cscLine = (CscLine) redisTemplate.opsForValue().get(cachecode);
        if(null != cscLine){
            System.err.println("缓存获取数据");
            return cscLine;
        }
        try {
            //大量请求进来，每一个key只有一个请求能获取到锁
            doLock(cachecode);
            cscLine = super.queryLine(lineId);
            if(null != cscLine)
                redisTemplate.opsForValue().set(CACHENAME + lineId, cscLine);
        }catch (Exception e){
            return null;
        }finally {
            release(cachecode);
        }
        return cscLine;
    }

    private void doLock(String cachecode) {
        ReentrantLock newLock = new ReentrantLock();
        ReentrantLock lock = (ReentrantLock) lockMap.putIfAbsent(cachecode, newLock);
        if(lock != null){
            lock.lock();
        }else{
            newLock.lock();
        }
    }

    private void release(String cachecode) {
        ReentrantLock lock = (ReentrantLock) lockMap.get(cachecode);
        if(lock != null){
            lock.unlock();
        }
    }


    @Override
    public int update(CscLine record){
        int i = super.update(record);
        if(i > 0){
            redisTemplate.delete(CACHENAME + record.getLineId());
        }
        return i;
    }

    @Override
    public int delete(long lineId) {
        int i = super.delete(lineId);
        if(i > 0){
            redisTemplate.delete(CACHENAME + lineId);
        }
        return i;
    }
}
