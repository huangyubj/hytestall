package com.hy.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

public class BusiHttpHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String result;
        FullHttpRequest request = (FullHttpRequest) msg;
        try {
            String body = request.content().toString(CharsetUtil.UTF_8);
            String uri = request.uri();
            if(uri.indexOf("/hy" ) == -1){
                result = "非法请求";
                responseInfo(ctx, result, HttpResponseStatus.BAD_GATEWAY);
                return;
            }
            HttpMethod method = request.method();
            if(HttpMethod.GET.equals(method)){
                result = String.format("收到小姐姐的信息%s，好开心呢！", body);
                result=String.format("Get request,Response=收到小姐姐的信息%s，好开心呢！", body);
                responseInfo(ctx, result, HttpResponseStatus.OK);
            }
        }catch(Exception e){
            System.out.println("处理请求失败!");
            e.printStackTrace();
        }finally {
            request.release();
        }
    }

    private void responseInfo(ChannelHandlerContext ctx, String result, HttpResponseStatus status) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(result, CharsetUtil.UTF_8);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
//        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
    }
}
