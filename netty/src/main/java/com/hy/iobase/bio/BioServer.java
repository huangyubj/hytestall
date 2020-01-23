package com.hy.iobase.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个BIO服务的自白
 * 1.一个线程池
 * 2.起一个服务
 * 3.监听socket连接
 * 4.线程池执行处理连接
 */
public class BioServer {

    public static final int PORT = 19998;
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        ServerSocket server;
        try {
            server = new ServerSocket(PORT);
            System.out.println(String.format("start server port：%d", PORT));
            while (true){
                Socket socket = server.accept();
                System.out.println(String.format("client connect localAdd-->%s,port-->%d", socket.getLocalAddress().toString(), socket.getPort()));
                executorService.execute(new BioThread(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class BioThread implements Runnable{
        private Socket socket;

        public BioThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            BufferedReader bufferedReader = null;
            PrintWriter out = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str;
                //采用了整行读取，发送消息需要println换行
                while(null != (str = bufferedReader.readLine())){
                    System.out.println(str);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(String.format("receive message: 收到", str));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                    try {
                        if(socket != null)
                            socket.close();
                        if (bufferedReader != null)
                            bufferedReader.close();
                        if (out != null)
                            out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
