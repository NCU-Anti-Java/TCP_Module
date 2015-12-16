package com.github.tjjh89017.TCP_Module;



import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client{


    private int id = 0;
    private String name = null;
    private String host = "127.0.0.1";
    private int port = 5566;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;


    public Client(){

        this(0, null);
    }

    public Client(int id, String name){

        this.id = id;
        this.name = name;

    }

    public void setHost(String host, int port){

        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException{

        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public boolean connectServer(InetAddress inetAddress){

        try{
            setHost(inetAddress.getHostAddress(), port);
            connect();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public synchronized void send(String msg) throws IOException{

        out.write(msg);
        out.flush();
    }

    public synchronized String read() throws IOException{

        return in.readLine();
    }

    public synchronized void inputMoves(int MoveCode) throws IOException {

        send("" + MoveCode + "\n");
    }

    public void close() throws IOException{

        if(socket != null) {
            socket.close();
        }
    }


    private int StringToInt(String string){

        return string.charAt(0) - '0';
    }

    private String IntToString(int i){

        return "" + (char)('0' + i);
    }
}
