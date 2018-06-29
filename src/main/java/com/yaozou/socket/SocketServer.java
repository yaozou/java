package com.yaozou.socket;/**
 * created by yaozou on 2018/6/28
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket服务端
 *  判断Socket是否可用 判断Socket是否可用:
 *      bound：是否绑定
        closed：是否关闭
        connected：是否连接
        shutIn：是否关闭输入流
        shutOut：是否关闭输出流
 *
 拆包：当一次发送（Socket）的数据量过大，而底层（TCP/IP）不支持一次发送那么大的数据量，则会发生拆包现象。
 黏包：当在短时间内发送（Socket）很多数据量小的包时，底层（TCP/IP）会根据一定的算法（指Nagle）把一些包合作为一个包发送。
 *
    https://www.cnblogs.com/yiwangzhibujian/p/7107785.html#q2
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
            //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
            ExecutorService threadPool = Executors.newFixedThreadPool(5);
            while (true){
                Socket socket = serverSocket.accept();
                Runnable runnable = ()->{
                   try{
                       //接收客户端数据
                       DataInputStream in = new DataInputStream(socket.getInputStream());
                       String string = in.readUTF();
                       System.out.println("client:" + string);
                       // 发送给客户端数据
                       DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                       out.writeUTF("hi,i am hserver!i say:" + string);
                       socket.close();
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                };
                threadPool.submit(runnable);
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
