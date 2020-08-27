package com.local.rabbitmqtencent.topics;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 通配符模式,
 * Topic 类型与 Direct 相比，都是可以根据 routingKey 把消息路由到不同的队列，
 *  	区别是 Topic 类型可以让队列在绑定 routingKey 时使用通配符
 *
 * routingKey 一般是有一个或多个单词，以 . 分隔， eg. indent.insert
 *
 * 通配符规则：
 * 	# ： 匹配一个或多个词
 * 	* ： 匹配一个词
 *
 * 	item.# 可以匹配 item.insert & item.insert.abc
 * 	item.* 只能匹配 item.insert
 */
public class Producer {

	// 交换机
	public static final String TOPIC_EXCHANGE = "topic_exchange";
	// 队列名
	public static final String TOPIC_QUEUE_1 = "topic_queue_1";
	public static final String TOPIC_QUEUE_2 = "topic_queue_2";

	public static void main(String[] args) throws Exception {
		// 拿到连接
		Connection connection = UtilMqConnection.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();

		// 声明交换机
		channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

		// 发送消息
		String message = "通配符模式消息，routingKey 为 item.insert";
		channel.basicPublish(TOPIC_EXCHANGE, "item.insert", null, message.getBytes());

		message = "通配符模式消息，routingKey 为 item.update";
		channel.basicPublish(TOPIC_EXCHANGE, "item.update", null, message.getBytes());

		message = "通配符模式消息，routingKey 为 product.delete";
		channel.basicPublish(TOPIC_EXCHANGE, "product.delete", null, message.getBytes());

		System.out.println("发送消息结束！");

		channel.close();;
		connection.close();
	}

}
