package com.hy.service.imp;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hy.demo.mybatis.entity.CscLine;
import com.hy.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 缓存存击穿
 */
@Service("lineServiceBreakdown")
public class LineServiceImpBeakdown extends LineServiceImp1 implements LineService {
    @Autowired
    private RedisTemplate redisTemplate;

    private BloomFilter<String> bloomFilter =  null;

    //初始化布隆过滤器
    @PostConstruct
    public void init(){
        List<CscLine> list = this.list();
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size());
        for (CscLine cscLine: list) {
            bloomFilter.put(CACHENAME + cscLine.getLineId());
        }
    }

    @Override
    public CscLine queryLine(long lineId) {
        String cacheKey = CACHENAME + lineId;
        if(!bloomFilter.mightContain(CACHENAME + lineId)){
            System.out.println("木得东西--------"+System.currentTimeMillis());
            return null;
        }
        return super.queryLine(lineId);
    }

}
