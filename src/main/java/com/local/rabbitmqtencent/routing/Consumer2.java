package com.local.rabbitmqtencent.routing;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

	public static void main(String[] args) throws Exception {
		// 创建连接
		Connection connection = UtilMqConnection.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		// 声明交换机， 类型为 direct
		channel.exchangeDeclare(Producer.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
		// 声明队列
		channel.queueDeclare(Producer.DIRECT_QUEUE_UPDATE, true, false, false, null);
		// 队列绑定交换机
		channel.queueBind(Producer.DIRECT_QUEUE_UPDATE, Producer.DIRECT_EXCHANGE, "update");
		// 创建消费者消费信息
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("路由Key:" + envelope.getRoutingKey());
				System.out.println("交换机为:" + envelope.getExchange());
				System.out.println("消息id为:" + envelope.getDeliveryTag());
				System.out.println("消息内容为:" + new String(body, "UTF-8"));
			}
		};
		channel.basicConsume(Producer.DIRECT_QUEUE_UPDATE, true, consumer);

	}
}
