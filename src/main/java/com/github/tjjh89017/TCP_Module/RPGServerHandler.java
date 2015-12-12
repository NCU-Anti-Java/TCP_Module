package com.github.tjjh89017.TCP_Module;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class RPGServerHandler extends SimpleChannelInboundHandler<String>{

    private ServerEvent serverEvent;
    private ChannelGroup channels;

    public RPGServerHandler(ServerEvent serverEvent, ChannelGroup channels){

        this.serverEvent = serverEvent;
        this.channels = channels;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        serverEvent.processing(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
        //ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        addChannel(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {


        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " Inactive");

        delChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    private synchronized void addChannel(Channel channel){

        channels.add(channel);
    }

    private synchronized void delChannel(Channel channel){

        channels.remove(channel);
    }
}
