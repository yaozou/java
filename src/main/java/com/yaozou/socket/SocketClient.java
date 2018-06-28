package com.yaozou.socket;/**
 * created by yaozou on 2018/6/28
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * socket客户端
 * @author yaozou
 * @create 2018-06-28 9:54
 **/
public class SocketClient {
    public static void main(String[] args){
        while(true){
            try {
                Socket socket = new Socket("192.168.0.198",9050);
                System.out.println("please input...");
                Scanner scanner = new Scanner(System.in);
                String p = scanner.nextLine();
                if (p.equals("bye")) {
                    socket.close();
                    break;
                }
                // 发送给服务器的数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(p);
                // 接收服务器的返回数据
                DataInputStream in = new DataInputStream(socket.getInputStream());
                System.out.println("hserver:" + in.readUTF());
                socket.close();
            }catch (Exception e){
               e.printStackTrace();
            }
        }
    }
}
