package com.hy.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class ConnectPools {
    private static final int MAX_NUM = 5;
//    private Semaphore semaphore = new Semaphore(MAX_NUM);
    private static ArrayBlockingQueue<Connection> queue = new ArrayBlockingQueue(5);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < MAX_NUM; i++) {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://192.168.6.32:3307/hap_csc?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai",
                        "hap", "1");
                queue.put(connection);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ConnectPools(){
    }

    public static Connection getConnect(){
        try {
            return ConnectPools.queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void release(Connection connection){
        try {
            if(connection != null)
                ConnectPools.queue.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
