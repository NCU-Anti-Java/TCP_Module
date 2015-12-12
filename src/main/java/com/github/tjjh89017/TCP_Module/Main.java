package com.github.tjjh89017.TCP_Module;


import java.io.IOException;
import java.net.InetAddress;

public class Main{


    public static void main(String[] args) throws IOException{

        //System.out.println("Hello World!");

        if(args.length < 1)
            return;


        Server server = null;
        Client client = null;
        try {
            if (args[0].charAt(0) == 's') {
                System.out.println("Server");
                server = new Server(new ExampleEcho());
                server.initServer();
            }
            else if (args[0].charAt(0) == 'c') {
                System.out.println("Client");
                client = new Client();
                client.connectServer(InetAddress.getByName("127.0.0.1"));

                int i = 0;
                while(true){
                    client.inputMoves(i);
                    System.out.println("Local " + i);
                    String s = client.read();
                    System.out.println("Remote " + s);
                    if(!s.equals("" + i)){
                        System.out.println("Error");
                        break;
                    }
                    i = (i + 1) % 5;
                    Thread.sleep(1000);
                }
            }
        }
        catch (InterruptedException e){
            if(server != null){

            }
            else if(client != null){
                client.close();
            }
        }

    }

}
