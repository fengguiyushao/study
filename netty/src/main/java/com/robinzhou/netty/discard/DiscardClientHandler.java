package com.robinzhou.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * Created by robinzhou on 2017/4/23.
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;

        content = ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);

        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }


    private void generateTraffic() {
        ctx.writeAndFlush(content.duplicate().retain()).addListener(trafficGenerator);
    }

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            if (future.isSuccess()) {
                generateTraffic();
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };


    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
