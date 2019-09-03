package com.yaozou.mq.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yaozou.mq.rabbitmq.ConnectUtils;

/**
 * @Description: 简单队列 一对一
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class Producer {
    private static final String QUEUE_NAME = "test";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectUtils.getConnect("",5672,"","","");
        // 建立通道
        Channel channel = connection.createChannel();
        // 设置队列类型
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "You are stupid!!!!idiot!!!";
        //简单队列 一对一
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("producer send msg:"+msg);

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
