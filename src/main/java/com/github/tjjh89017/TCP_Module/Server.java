package com.github.tjjh89017.TCP_Module;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;

public class Server {

    private String host = "127.0.0.1";
    private int port = 5566;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap serverBootstrap;

    private ServerEvent serverEvent;
    private RPGServerInitializer rpgServerInitializer;


    public Server(ServerEvent serverEvent){

        this.serverEvent = serverEvent;
    }

    public void start() throws InterruptedException {

        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        rpgServerInitializer = new RPGServerInitializer(serverEvent);
        serverBootstrap.childHandler(rpgServerInitializer);

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
    }

    public void initServer() throws InterruptedException {

        start();
    }

    public ArrayList<String> getClientIPTable(){

        ArrayList<String> arrayList = new ArrayList<String>();
        ChannelGroup channels = rpgServerInitializer.getChannels();
        for(Channel channel : channels){
            arrayList.add(channel.remoteAddress().toString());
        }
        return arrayList;
    }

    public void close(){

        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();

    }
}
