package com.yan.rabbitmq.work.fair;

import com.rabbitmq.client.*;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Customer1 {

    public static void main(String[] args) throws IOException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        final Channel channel = connection.createChannel();

        channel.basicQos(1);

        // 通过通道去声明队列
        channel.queueDeclare("work", true, false, false, null);

        channel.basicConsume("work", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者-1" + new String(body));

            }
        });

    }

}
