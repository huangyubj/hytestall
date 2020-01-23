package com.hy;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.demo.mybatis.mapper.CscLineMapper;
import com.hy.service.LineService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class MybatisTest {
    @Autowired
    private CscLineMapper cscLineMapper;

    @Autowired
    private LineService lineService;

    @org.junit.Test
    public void mybatisTest(){
        CscLine cscLine = cscLineMapper.selectByPrimaryKey(28l);
        System.err.println(cscLine.toString());
    }
    @org.junit.Test
    public void redisCacheTest(){
        CscLine cscLine = lineService.queryLine(28l);
        System.err.println(cscLine.toString());
    }
}
