package com.hy.mybatis;

import org.junit.Test;

import java.sql.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class JdbcTest {

    @Test
    public void testThread(){
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.countDown();
                        countDownLatch.await();
                        new JdbcTest().test();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        Connection connection = null;
        try {
            connection = ConnectPools.getConnect();
            System.out.println(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from csc_line where line_id=1000463");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("line_id"));
                System.out.println(resultSet.getString("line_code"));
                System.out.println(resultSet.getString("line_name"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection != null)
                ConnectPools.release(connection);
        }
    }
}
