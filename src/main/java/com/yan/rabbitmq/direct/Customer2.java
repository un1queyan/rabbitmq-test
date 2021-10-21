package com.yan.rabbitmq.direct;

import com.rabbitmq.client.*;
import com.yan.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Customer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-message-ttl", 50000);
        channel.exchangeDeclare("logs_direct", "direct");
        String queue = channel.queueDeclare("", false, false, true, arg).getQueue();

        channel.queueBind(queue, "logs_direct", "info");
        channel.queueBind(queue, "logs_direct", "error");
        channel.queueBind(queue, "logs_direct", "warning");

//        channel.basicConsume(queue, true, new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("消费者-2" + new String(body));
//            }
//        });
    }
}
