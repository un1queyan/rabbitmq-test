package com.yan.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Provider {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        // 将通道声明指定的交换机
        /*
        . @param1: 交换机名称
        . @param2: 交换机类型  fanout 广播类型
         */
        channel.exchangeDeclare("logs", "fanout");


        // 发送消息
        channel.basicPublish("logs", "", null, "fanout type message".getBytes(StandardCharsets.UTF_8));

        RabbitMQUtil.closeConnectionAndChanel(channel, connection);
    }

}
