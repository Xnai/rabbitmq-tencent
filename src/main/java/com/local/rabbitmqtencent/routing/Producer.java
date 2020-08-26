package com.local.rabbitmqtencent.routing;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式，
 * 路由模式 与 发布/订阅模式 最大的区别在于交换机的类型为 direct，队列在绑定交换机时需要制定 routingKey
 */
public class Producer {
	// 交换机名称
	public static final String DIRECT_EXCHANGE = "direct_exchange";
	// 队列名称
	public static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
	public static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

	public static void main(String[] args) throws Exception {
		// 创建连接
		Connection connection = UtilMqConnection.getConnection();
		// 创建频道
		Channel channel = connection.createChannel();
		// 声明交换机， 类型为 direct
		channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
		// 声明队列
		channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
		channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);
		// 队列绑定交换机
		channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
		channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");
		// 创建消息
		String message = "新增商品！routingKey 为 insert";
		// 发送消息
		channel.basicPublish(DIRECT_EXCHANGE, "insert", null, message.getBytes());
		System.out.println("已发送消息：" + message);
		// 修改信息
		message = "更新商品！routingKey 为 update";
		// 发送消息
		channel.basicPublish(DIRECT_EXCHANGE, "update", null, message.getBytes());
		System.out.println("已发送消息：" + message);
		// 关闭资源
		channel.close();
		connection.close();
	}

}
