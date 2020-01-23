package com.hy.iobase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) {
        new Thread(new ServerHandller()).start();
    }

    public static int PORT = 12121;

    static class ServerHandller implements Runnable{
        private boolean isStart = false;
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;

        public ServerHandller() {
            try {
                this.selector = Selector.open();
                serverSocketChannel = ServerSocketChannel.open();
                //必须是非阻塞的
                serverSocketChannel.configureBlocking(false);
                //注册类型需要正确
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
                System.out.println(String.format("service is started for %d", PORT));
                isStart = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop(){
            isStart = false;
        }

        public void run() {
            while (isStart){
                try {
                    //阻塞,只有当至少一个注册的事件发生的时候才会继续.
                    selector.select();
//                    Set<SelectionKey> keys = selector.keys();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    //转换为迭代器
                    Iterator<SelectionKey> it = keys.iterator();
                    SelectionKey key = null;
                    while(it.hasNext()){
                        key = it.next();
                        it.remove();
                        try {
                            handleInput(key);
                        } catch (IOException e) {
                            e.printStackTrace();
                            if(key!=null){
                                key.cancel();
                                if(key.channel()!=null){
                                    key.channel().close();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //selector关闭后会自动释放里面管理的资源
            if(selector != null)
                try{
                    selector.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }

        private void handleInput(SelectionKey selectKey) throws IOException {
            if(selectKey.isValid()){
                //新接入的socket
                if(selectKey.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) selectKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    System.out.println(String.format("client connect localAdd-->%s,port-->%d", socketChannel.getLocalAddress().toString(), socketChannel.socket().getPort()));
                    //异步不阻塞
                    socketChannel.configureBlocking(false);
                    //注册到selector
                    socketChannel.register(selector, SelectionKey.OP_READ);

                }
                //可读状态
                if(selectKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int i = -1;
                    while ((i =socketChannel.read(byteBuffer)) > 0){
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bytes);
                        String msg = new String(bytes, "utf-8");
                        //收到消息
                        System.out.println(String.format("get message ->%s", msg));
                        //返回数据
                        byte[] receiveMsg = String.format("msg->%s to reveive", msg).getBytes();
                        ByteBuffer writeBuffer = ByteBuffer.allocate(receiveMsg.length);
                        writeBuffer.put(receiveMsg);
                        writeBuffer.flip();
                        socketChannel.write(writeBuffer);
                    }
                    selectKey.cancel();
                    socketChannel.close();
                }
                //可写状态
//                if(selectKey.isWritable()){
//
//                }
            }
        }
    }
}
