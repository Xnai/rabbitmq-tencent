package com.local.rabbitmqtencent.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        // 连接工程
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂属性
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //factory.setVirtualHost("/mq_tencent");  // 虚拟主机
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建频道
        Channel channel = connection.createChannel();
        // 创建队列
        /**
         * 队列名， 是否持久化， 是否独占本次连接， 是否在不使用是自动删除队列， 队列的其他参数
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 消息声明
        String message = "孙悟空大战宋江！";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息已发送！");
        // 释放资源
        channel.close();
        connection.close();
    }
}
