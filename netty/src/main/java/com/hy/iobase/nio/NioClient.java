package com.hy.iobase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioClient {

    public static void main(String[] args) {
        ClientHandller handller = new ClientHandller();
        new Thread(handller).start();
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                handller.sendMsg(scanner.next());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientHandller implements Runnable{
        private Selector selector;
        private SocketChannel socketChannel;
        private boolean started;
        public ClientHandller() {
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                started= true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                connectServer();
                while (started){
                    selector.select();
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //selector关闭后会自动释放里面管理的资源
            if(selector!=null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if(key.isValid()){
                if(key.isConnectable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    if(socketChannel.finishConnect()){
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                }
                if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int i = 0;
                    while ((i = socketChannel.read(buffer)) > 0){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        System.out.println("receive msg=>"+ new String(bytes, "utf-8"));
                    }
                    key.cancel();
//                    socketChannel.close();
                }
            }
        }

        private void connectServer() throws IOException {
            if(!socketChannel.connect(new InetSocketAddress(NioServer.PORT)))
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }

        public void stop(){
            started = false;
        }

        public void sendMsg(String msg) throws IOException {
            byte[] bytes = msg.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }
}
