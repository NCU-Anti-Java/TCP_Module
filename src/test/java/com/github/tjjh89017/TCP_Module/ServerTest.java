package com.github.tjjh89017.TCP_Module;

import io.netty.channel.ChannelHandlerContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Date on 2015/12/16.
 */
public class ServerTest{

    private static class Run extends Thread{

        private Server server;

        public Run(Server server){

            this.server = server;
        }

        @Override
        public void run() {

            try {
                server.initServer();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    private Client client;
    private Client[] clients;
    private Server server;
    private Run run;

    @Before
    public void setUp() throws Exception {

        SimpleCDC simpleCDC = mock(SimpleCDC.class);
        server = new Server(simpleCDC);

        doCallRealMethod().when(simpleCDC).processing(any(ChannelHandlerContext.class), anyString());
        doThrow(new RuntimeException()).when(simpleCDC).getItem();
        doThrow(new RuntimeException()).when(simpleCDC).updateDirection(anyString());
        doNothing().when(simpleCDC).doNothing();

        run = new Run(server);
        run.start();
        client = new Client();
        clients = new Client[10];

        for(int i = 0; i < 10; i++){
            clients[i] = new Client();
        }
    }

    @After
    public void tearDown() throws Exception {

        for(int i = 0; i < 10; i++){
            if(clients[i] != null) {
                clients[i].close();
            }
        }
        clients = null;

        client.close();
        server.close();
        run.join();
    }

    @Test(expected = RuntimeException.class)
    public void testGetItem() throws Exception {

        client.connect();
        client.send("5\n");
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateDirection() throws Exception {

        client.connect();
        client.send("1\n");
    }


    @Test
    public void testGetClientIPTableZero() throws Exception {

        Thread.sleep(1000);
        ArrayList arrayList = server.getClientIPTable();
        assertEquals(0, arrayList.size());

    }

    @Test
    public void testGetClientIPTable() throws Exception {

        ArrayList<String> arrayList;

        for(int i = 0; i < 10; i++){
            clients[i].connect();
            clients[i].send("0\n");
        }

        Thread.sleep(1000);

        arrayList = server.getClientIPTable();
        assertEquals(10, arrayList.size());
    }

}