package com.hy.netty.busi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class NettyServer {

    public static final int SER_PORT = 12122;
    public static final String DELIMITER = "$%^&";

    public static void main(String[] args) throws InterruptedException {
        //包含mainReactor在内的线程池
        EventLoopGroup group = new NioEventLoopGroup();
        //处理业务与工作线程的线程池
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(group, worker).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(SER_PORT))
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //TCP协议特性，发送的数据包大于或小于TCP滑动窗口，会对数据进行拆包、粘包，
                            // 发送数据包大于缓冲区会对数据进行拆包
                            // 粘包/半包问题处理方案
                            //1、换行处理，传输数据以System.getProperty("line.separator")、"/r/n" 结尾，Decoder进行拆包
                            //ch.pipeline().addLast(new LineBasedFrameDecoder(5 * 1024));
                            //2、指定分割字符，传输字符以固定字符结尾，Decoder进行拆包
                            //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(5 * 1024, Unpooled.copiedBuffer(DELIMITER.getBytes())));
                            //3、固定长度处理，Decoder进行拆包
                            //ch.pipeline().addLast(new FixedLengthFrameDecoder(5 * 1024));
                            //4、长度域解决 new LengthFieldBasedFrameDecoder()
                            ch.pipeline().addLast(new BusiChannelHanddle());
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }

    static class BusiChannelHanddle extends ChannelInboundHandlerAdapter {
        //被添加到pipeline
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
        }
        //被移除出pipeline
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            super.handlerRemoved(ctx);
        }


        //服务获取到数据后处理，一次数据读取完成
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg){
            try {
                //IllegalReferenceCountException: refCnt: 0 ByteBuf引用计数器被release为0，继续release会抛此错
//                super.channelRead(ctx, msg);
                ByteBuf byteBuf = (ByteBuf) msg;
                System.out.println("server get message-->"+byteBuf.toString(CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //获取完网络数据处理，一次完整的数据包，TCP一次报文发送完成
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//            super.channelReadComplete(ctx);
            //关闭客户端连接
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        //异常处理
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            cause.printStackTrace();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }
    }
}
