package com.local.rabbitmqtencent.simple;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws Exception {
        // 获取连接
        Connection connection = UtilMqConnection.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();
        // 创建消费者，设置消息处理
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);
        // 监听消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * @param consumerTag 消费者标签，在 channel.basicConsume 时可以指定
             * @param envelope 消息包内容，包含消息 id, 消息 routingKey, 交换机， 消息和重转标记（收到消息失败后是否重新发送
             * @param properties 消息属性
             * @param body 消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由Key:" + envelope.getRoutingKey());
                System.out.println("交换机:" + envelope.getExchange());
                System.out.println("消息ID:" + envelope.getDeliveryTag());
                System.out.println("消息内容:" + new String(body, "UTF-8"));
            }
        };
        // 鉴定消息
        channel.basicConsume(Producer.QUEUE_NAME, true, consumer);
        // 不在关闭连接，应该一直监听消息
        // channel.close();
        // connection.close();
    }
}
