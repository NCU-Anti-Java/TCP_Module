package com.github.tjjh89017.TCP_Module;


import io.netty.channel.ChannelHandlerContext;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;


public abstract class ServerEvent{

    public abstract void processing(ChannelHandlerContext ctx, String msg);


    private static int StringToInt(String string){

        return string.charAt(0) - '0';
    }

    private static String IntToString(int i){

        return "" + (char)('0' + i);
    }


}
