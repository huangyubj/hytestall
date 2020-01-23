package com.hy.test;

import com.hy.mybatis.entity.CscLine;
import com.hy.mybatis.entity.CscLineStation;
import com.hy.mybatis.mapper.CscLineMapper;
import com.hy.mybatis.mapper.CscLineStationMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNomal {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void testBefor(){
        InputStream inputStream = null;
        try {
            String resource = "mybatis-config.xml";
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void testOne(){
        SqlSession session = sqlSessionFactory.openSession();
        CscLineMapper mapper = session.getMapper(CscLineMapper.class);
        CscLine cscLine = mapper.selectByPrimaryKey(1000463l);
        System.err.println(cscLine.toString());
    }

    @Test
    public void testMany(){
        SqlSession session = sqlSessionFactory.openSession();
        CscLineStationMapper mapper = session.getMapper(CscLineStationMapper.class);
        Map<String, Long> map = new HashMap<>();
        map.put("line_id", 28l);
//        List<CscLineStation> list = mapper.selectByLineId(28l);
        List<CscLineStation> list = mapper.selectByLineIdParamMap(map);
        for (CscLineStation data : list){
            System.err.println(data.toString());
        }
    }
}
