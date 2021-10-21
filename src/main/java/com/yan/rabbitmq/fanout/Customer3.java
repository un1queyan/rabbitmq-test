package com.yan.rabbitmq.fanout;

import com.rabbitmq.client.*;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;

public class Customer3 {
    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        final Channel channel = connection.createChannel();

        // 通过通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");

        // 临时队列
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queue, "logs", "");

        // 消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-3" + new String(body));
            }
        });
    }
}
