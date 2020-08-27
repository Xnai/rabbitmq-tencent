package com.local.rabbitmqtencent.topics;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

	public static void main(String[] args) throws Exception {

		Connection connection = UtilMqConnection.getConnection();

		Channel channel = connection.createChannel();

		// 声明交换机
		channel.exchangeDeclare(Producer.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

		// 创建队列
		channel.queueDeclare(Producer.TOPIC_QUEUE_2, true, false, false, null);

		// 队列绑定交换机，通配符绑定的重点在本行
		 channel.queueBind(Producer.TOPIC_QUEUE_2, Producer.TOPIC_EXCHANGE, "*.delete");

		// 创建消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("路由Key为：" +  envelope.getRoutingKey());
				System.out.println("交换机为：" + envelope.getExchange());
				System.out.println("消息ID为：" + envelope.getDeliveryTag());
				System.out.println("消息内容为：" + new String(body, "UTF-8"));
			}
		};

		// 监听消息
		channel.basicConsume(Producer.TOPIC_QUEUE_2, true, consumer);
	}
}
