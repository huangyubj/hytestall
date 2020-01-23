package com.hy.netty.busi;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class IdleStateInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new IdleStateHandler(0, 0, 6));
        ch.pipeline().addLast(new HeartbeatHandler());
    }

    private static class HeartbeatHandler extends ChannelInboundHandlerAdapter {

        private static final ByteBuf HEART_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEART_BEAT_INFO".getBytes()));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            //心跳发送失败关闭连接
            if(evt instanceof IdleStateEvent){
                ctx.writeAndFlush(HEART_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                return;
            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
