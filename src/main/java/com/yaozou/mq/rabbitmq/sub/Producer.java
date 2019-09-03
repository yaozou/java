package com.yaozou.mq.rabbitmq.sub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yaozou.mq.rabbitmq.ConnectUtils;

/**
 * @Description: 发布/订阅模式  Fanout exchange（扇型交换机）
 * <P>如果所有consumer的队列都是绑定的同一个交换机，那么producer发送的消息都会被所有的consumer消费</P>
 *<ul>
 *     <li>1、消费者将消息发布到交换机</li>
 *     <li>2、交换机绑定多个队列</li>
 *     <li>3、被监听该队列的消费者所接收并消费</li>
 * <ul/>
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class Producer {
    private static final String EXCHANGE_NAME = "fanout";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectUtils.getConnect("",5672,"","","");
        // 建立通道
        Channel channel = connection.createChannel();
        // 设置交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg = "You are stupid!!!!idiot!!!";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
