package com.yaozou.mq.rabbitmq.route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yaozou.mq.rabbitmq.ConnectUtils;

/**
 * @Description: 路由模式  Direct exchange（直连交换机）
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class Producer {
    private static final String EXCHANGE_NAME = "direct";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectUtils.getConnect();
        // 建立通道
        Channel channel = connection.createChannel();
        // 设置直连交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg = "You are stupid!!!!idiot!!!";
        channel.basicPublish(EXCHANGE_NAME, "add", null, msg.getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
