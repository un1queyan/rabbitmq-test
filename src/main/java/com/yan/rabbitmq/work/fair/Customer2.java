package com.yan.rabbitmq.work.fair;
import com.rabbitmq.client.*;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Customer2 {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        final Channel channel = connection.createChannel();

        // 每次只能消费一个消息
        channel.basicQos(1);
        // 通过通道去声明队列
        channel.queueDeclare("work", true, false, false, null);

        channel.basicConsume("work", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2" + new String(body));

                // 手动确认 参数一： 手动确认消息标识  参数2： false 每次只确认一个 是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }

}
