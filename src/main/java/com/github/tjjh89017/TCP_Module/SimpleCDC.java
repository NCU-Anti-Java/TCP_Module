package com.github.tjjh89017.TCP_Module;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Date on 2015/12/16.
 */
public class SimpleCDC extends ServerEvent{


    @Override
    public void processing(ChannelHandlerContext ctx, String msg) {

        int val = Integer.getInteger(msg);
        if(val == 5){
            getItem();
        }
        else if(1 <= val && val <= 4){
            updateDirection(msg);
        }
        else{

        }

    }

    public void getItem(){
        // do nothing
    }

    public void updateDirection(String MovdCode){

        // do nothing
    }

    public void doNothing(){

        // do nothing
    }
}
