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
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);

        String msg = "{\"request-id\":\"9449985b286a4a98aadc3e62f2c1ada0\",\"client-id\":\"SN12345678\",\"body\":\"{\\\"topic\\\":\\\"open_battery\\\",\\\"command\\\":{\\\"0\\\":{\\\"name\\\":\\\"result\\\",\\\"type\\\":\\\"Byte\\\",\\\"value\\\":2},\\\"1\\\":{\\\"name\\\":\\\"error\\\",\\\"type\\\":\\\"Byte\\\",\\\"value\\\":1}}}\"}";
        channel.basicPublish(EXCHANGE_NAME, "command", null, msg.getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
