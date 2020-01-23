package com.hy.controller;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.service.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "redis cache", description = "redis缓存相关测试api")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("lineServiceSimple")
    private LineService lineService;

    @Autowired
    @Qualifier("lineServiceBoot")
    private LineService lineServiceBoot;

    @Autowired
    @Qualifier("lineServiceAvalance")
    private LineService lineServiceAvalance;

    @Autowired
    @Qualifier("lineServiceBreakdown")
    private LineService lineServiceBreakdown;

    @ApiOperation(value="simple cache used")
    @RequestMapping(value = "/simple",method= RequestMethod.GET)
    public CscLine testSimpleCache(@ApiParam(value = "查询路线id") @RequestParam Long lineid){
        return lineService.queryLine(lineid);
    }

    @ApiOperation(value="springboot simple cache used")
    @RequestMapping(value = "/bootsimple",method= RequestMethod.GET)
    public CscLine testBootSimpleCache(@ApiParam(value = "查询路线id") @RequestParam Long lineid){
        return lineServiceBoot.queryLine(lineid);
    }

    @ApiOperation(value="缓存雪崩 cache used")
    @RequestMapping(value = "/avalance",method= RequestMethod.GET)
    public CscLine testAvalanceCache(@ApiParam(value = "查询路线id") @RequestParam Long lineid){
        return lineServiceAvalance.queryLine(lineid);
    }

    @ApiOperation(value="缓存击穿 cache used")
    @RequestMapping(value = "/breakdown",method= RequestMethod.GET)
    public CscLine testBreakDownCache(@ApiParam(value = "查询路线id") @RequestParam Long lineid){
        return lineServiceBreakdown.queryLine(lineid);
    }

    @ApiOperation(value="clean cache used")
    @RequestMapping(value = "/doUpdate",method= RequestMethod.POST)
    public int doUpdate(@ApiParam(value = "查询路线id") @RequestParam Long lineid, @RequestParam String lineName){
        CscLine cscLine = new CscLine();
        cscLine.setLineId(lineid);
        cscLine.setLineName(lineName);
        return lineService.update(cscLine);
    }

}
