package com.hy.aop;

import com.hy.SpringCodeDrawApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCodeDrawApplication.class)
public class AopTest {

    @Autowired
    private ExprService exprService;

    @Test
    public void test1(){
        exprService.add(1,3);
    }

    @Test
    public void testException(){
        exprService.divide(1,0);
    }
}
