package com.yan.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) {

        // 所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp协议
        // ip port

        // 1. 创建链接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("8.130.172.12");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
            // 2. 创建连接connection
            connection = connectionFactory.newConnection("生成者");

            // 3. 通过链接获取通道channel
            channel = connection.createChannel();

            // 4. 通过创建交换机，声明队列，绑定关系，路由key，发送消息和接受消息
            String queueName = "queue1";

            /*
             * 参数的意义：
             * 队列的名称，
             * 是否要持久化durable=false
             * 排他性，是否是独占独立
             * 是否自动删除，随着消费者消息完毕之后是否把队列自动删除
             * 携带一些附加参数
             */
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到的消息是：" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("接受消息失败了");
                }
        });
            System.out.println("开始接受消息");
            System.in.read();

            // 5. 准备消息内容
            String message = "hello wirsty";

            // 6. 发送消息给队列queue
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            // 7. 关闭链接
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // 8. 关闭通道
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }



    }
}
