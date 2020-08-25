package com.local.rabbitmqtencent.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class UtilMqConnection {

    public static Connection getConnection() throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 连接配置
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //factory.setVirtualHost("/mq_tencent");
        return factory.newConnection();
    }
}
