package com.github.tjjh89017.TCP_Module;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertFalse;

/**
 * Created by Date on 2015/12/16.
 */
public class ClientTest {

    private Client client = new Client();
    private InetAddress inetAddress;
    final static private int port = 5566;

    @Before
    public void setUp() throws Exception {

        inetAddress = InetAddress.getByName("127.0.0.1");
    }

    @After
    public void tearDown() throws Exception {

        client.close();
    }

    @Test
    public void testConnectServer() throws Exception {

        boolean result = client.connectServer(inetAddress);
        assertFalse(result);

    }

    @Test
    public void testInputMoves() throws Exception {

        final PipedInputStream pipedInputStream = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(pipedOutputStream));
        final BufferedReader in = new BufferedReader(new InputStreamReader(pipedInputStream));

        client = new Client(){

            @Override
            public synchronized void send(String msg) throws IOException {
                out.write(msg);
                out.flush();
            }
        };

        for(int i = 1; i <= 5; i++){
            client.inputMoves(i);
            assertEquals("" + i, in.readLine());
        }
    }
}