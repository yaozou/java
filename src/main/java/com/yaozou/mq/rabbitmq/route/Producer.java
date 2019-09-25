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
    private static final String EXCHANGE_NAME = "iot-api-dn";
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectUtils.getConnect();
        // 建立通道
        Channel channel = connection.createChannel();
        // 设置直连交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg = "{\"request-id\":\"test-111111111111\",\"client-id\":\"29a68dbdb66d45d6a2ee56ce9010acd0\",\"body\":{\"topic\":\"open battery\",\"commend\":{\"0\":{\"name\":\"operator\",\"type\":\"Byte\",\"value\":1},\"1\":{\"name\":\"time\",\"type\":\"Long\",\"value\":1569380403512},\"2\":{\"name\":\"num\",\"type\":\"Integer\",\"value\":2},\"3\":{\"name\":\"lt\",\"type\":\"Double\",\"value\":0.123},\"4\":{\"name\":\"desc\",\"type\":\"String\",\"value\":\"2222222\"}}}}\n";
        channel.basicPublish(EXCHANGE_NAME, "command", null, msg.getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
