package com.yan.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {
    private static ConnectionFactory connectionFactory;

    static {
        // 重量级资源
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("8.130.172.12");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
    }

    // 定义提供链接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭通道和关闭链接工具的方法
    public static void closeConnectionAndChanel(Channel channel, Connection conn) {
        try {
            if (channel != null) {
                channel.close();

            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
