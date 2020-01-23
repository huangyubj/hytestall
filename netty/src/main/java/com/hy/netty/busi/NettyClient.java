package com.hy.netty.busi;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(NettyServer.SER_PORT))
                    .handler(new BusiClientHandller());
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    static class BusiClientHandller extends SimpleChannelInboundHandler<ByteBuf>{

        //读取到数据后业务操作
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            System.out.println("Client accetp:"+msg.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            super.channelActive(ctx);
            for (int i = 0; i < 100; i++) {
                ctx.writeAndFlush(getSendMsg(ctx, i));
            }
        }
        private ByteBuf getSendMsg(ChannelHandlerContext ctx, int i){
            String msg = String.format("你好啊，小%d我是刺客5670，我是刺客567", i);
            ByteBuf byteBuf = ctx.alloc().buffer();
            try {
                byteBuf.writeBytes(msg.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return byteBuf;
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
