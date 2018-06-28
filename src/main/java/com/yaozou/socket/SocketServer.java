package com.yaozou.socket;/**
 * created by yaozou on 2018/6/28
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket服务端
 * @author yaozou
 * @create 2018-06-28 9:53
 **/
public class SocketServer implements Runnable{
    private int port;
    public SocketServer(int port){
        this.port = port;
    }

    public void run() {
        try{
            System.out.println("waiting...");
            ServerSocket serverSocket = new ServerSocket(this.port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                //接收客户端数据
                DataInputStream in = new DataInputStream(socket.getInputStream());
                String string = in.readUTF();
                System.out.println("client:" + string);
                // 发送给客户端数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("hi,i am hserver!i say:" + string);
                socket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        SocketServer serverSocket = new SocketServer(9050);
        serverSocket.run();
    }
}
