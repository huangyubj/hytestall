package com.hy.iobase.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * 一个BIO客户端的自白
 * 1.连接一个服务
 * 2.给服务发送消息
 * 3.等待服务返回消息
 */
public class BioClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", BioServer.PORT);
            new SocketClient(socket).start();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);;
            while (true){
                String msg = new Scanner(System.in).next();
                if(!"over".equals(msg)){
                    System.out.println("send--->" + msg);
                    out.println(msg);
                    out.flush();
                }else{
                    socket.close();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class SocketClient extends Thread{
        private Socket socket;

        public SocketClient(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receiveInfo;
                while ((receiveInfo = reader.readLine()) != null){
                    System.out.println("get receive message--->" + receiveInfo);
                }
                System.out.println("disconnect socket");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
