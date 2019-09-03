package com.yaozou.mq.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.yaozou.mq.rabbitmq.ConnectUtils;

/**
 * @Description: 消费者
 * @Author yao.zou
 * @Date 2019/9/3 0003
 * @Version V1.0
 **/
public class Consumer2 {
    private static final String QUEUE_NAME = "test_topic_1";
    private static final String EXCHANGE_NAME = "topic";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectUtils.getConnect();
        // 建立通道
        Channel channel = connection.createChannel();
        // 设置队列类型
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"select.#");

        channel.basicQos(1);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        /**
         * 监听队列
         *  true 自动提交确认
         *  false 手动提交确认
         *  如果consumer一直没有确认消息，那么该消息将一直处于不可用的状态，并且broker认为此consumer已经挂掉，
         *  不会再给此consumer 发送消息直到该consumer反馈。
         */
        channel.basicConsume(QUEUE_NAME,false,queueingConsumer);

        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("consumer receive msg:"+msg);

            Thread.sleep(10);
            // 手动提交确认 当为false时
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
