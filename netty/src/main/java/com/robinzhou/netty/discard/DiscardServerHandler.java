package com.robinzhou.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by robinzhou on 2017/4/23.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler {
    int i = 0;

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (i++ % 10000 == 0) {
            System.out.println(((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
