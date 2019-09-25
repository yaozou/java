package com.yaozou.mq.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Description: rabbitmq 连接工具
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class ConnectUtils {
    /**
     * 建立连接
     * @param host broker地址
     * @param port 端口
     * @param vhost 虚拟主机地址
     * @param username 用户名称
     * @param password 密码
     * @return
     * @throws IOException
     */
    public static Connection getConnect() throws IOException {
        String host="192.168.98.81",vhost="",username="admin",password="admin";
        int port = 5672;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);

        if (!vhost.equals("")){
            // 设置虚拟主机
            factory.setVirtualHost(vhost);
        }

        factory.setUsername(username);
        factory.setPassword(password);

        Connection connection = factory.newConnection();

        return connection;
    }
}
