package com.github.tjjh89017.TCP_Module;

import io.netty.channel.ChannelHandlerContext;

public class ExampleEcho extends ServerEvent {

    @Override
    public void processing(ChannelHandlerContext ctx, String msg) {

        ctx.writeAndFlush(msg + "\n");
    }
}
