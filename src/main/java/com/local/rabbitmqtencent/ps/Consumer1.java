package com.local.rabbitmqtencent.ps;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = UtilMqConnection.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();
        // 声明交换机
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        // 声明队列
        channel.queueDeclare(Producer.FANOUT_QUEUE_1, true, false, false, null);
        // 队列绑定交换机
        channel.queueBind(Producer.FANOUT_QUEUE_1, Producer.FANOUT_EXCHANGE, "");
        // 创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由Key:" + envelope.getRoutingKey());
                System.out.println("交换机为:" + envelope.getExchange());
                System.out.println("消息id为:" + envelope.getDeliveryTag());
                System.out.println("消息内容为:" + new String(body, "UTF-8"));
            }
        };
        // 监听消息
        channel.basicConsume(Producer.FANOUT_QUEUE_1, true, defaultConsumer);
    }
}
