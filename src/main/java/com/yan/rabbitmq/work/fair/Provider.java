package com.yan.rabbitmq.work.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 通过通道去声明队列
        channel.queueDeclare("work", true, false, false, null);

        for (int i = 0; i < 100; i++) {
            // 生产消息
            channel.basicPublish("", "work", null, ("hello work queue" + i).getBytes(StandardCharsets.UTF_8));

        }

        // 关闭资源
        RabbitMQUtil.closeConnectionAndChanel(channel, connection);
    }

}
