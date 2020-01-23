package com.hy.service.imp;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.demo.mybatis.mapper.CscLineMapper;
import com.hy.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 简单cash使用
 */
@Service("lineServiceSimple")
public class LineServiceImp1 extends LineServiceImp implements LineService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public CscLine queryLine(long lineId) {
        CscLine cscLine = (CscLine) redisTemplate.opsForValue().get(CACHENAME + lineId);
        if(null != cscLine){
            System.err.println("缓存获取数据");
            return cscLine;
        }
        cscLine = super.queryLine(lineId);
        if(null != cscLine)
            redisTemplate.opsForValue().set(CACHENAME + lineId, cscLine);
        return cscLine;
    }


    @Override
    public int update(CscLine record){
        int i = super.update(record);
        if(i > 0){
            //亦可更新缓存
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
