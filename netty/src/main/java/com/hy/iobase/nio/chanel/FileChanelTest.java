package com.hy.iobase.nio.chanel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * FileChannel： 用于文件的数据读写
 *
 * DatagramChannel： 用于UDP的数据读写
 *
 * SocketChannel： 用于TCP的数据读写，一般是客户端实现
 *
 * ServerSocketChannel: 允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel，一般是服务器实现
 * 1.开启channel
 * 2.进行读写数据
 * 3.关闭channel
 */
public class FileChanelTest {

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("F://test.txt", "rw");
            String writeInfo = null;
            FileChannel fileChannel = file.getChannel();
            while (!"over".equals(writeInfo)){
                writeInfo = new Scanner(System.in).next();
                byte[] bytes = writeInfo.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                fileChannel.write(writeBuffer);
                writeBuffer.clear();
            }
            fileChannel.position(0);
            ByteBuffer readBuffer = ByteBuffer.wrap(new byte[128]);
            int i;
            while ((i = fileChannel.read(readBuffer)) != -1){
                System.out.println("read->"+i);
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                System.out.println(new String(bytes, "utf-8"));
                readBuffer.flip();
                readBuffer.clear();
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
