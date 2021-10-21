package com.yan.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        // 将通道声明指定的交换机
        /*
        . @param1: 交换机名称
        . @param2: 交换机类型  direct 路由模式
         */
        channel.exchangeDeclare("logs_direct", "direct");

        String routingKey = "error";
        // 发送消息


        channel.basicPublish("logs_direct", routingKey, null, ("这是direct模式发布的基于route key：" + routingKey + "]发送的消息").getBytes(StandardCharsets.UTF_8));

        RabbitMQUtil.closeConnectionAndChanel(channel, connection);
    }
}
