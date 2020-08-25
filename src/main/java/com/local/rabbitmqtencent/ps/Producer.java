package com.local.rabbitmqtencent.ps;

import com.local.rabbitmqtencent.util.UtilMqConnection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布与订阅模式
 * 使用了广播模式 fanout
 */
public class Producer {
    // 交换机名称
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    // 队列1
    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    // 队列2
    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args) throws Exception {
        Connection connection = UtilMqConnection.getConnection();

        Channel channel = connection.createChannel();
        // 声明交换机
        // 交换机类型共四种： fanout、topic、direct、headers
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        // 声明队列
        channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);
        channel.queueDeclare(FANOUT_QUEUE_2, true, false, false, null);
        // 队列绑定交换机
        channel.queueBind(FANOUT_QUEUE_1, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_2, FANOUT_EXCHANGE, "");
        // 发送消息
        for(int i = 1; i <= 10; i++) {
            String message = "发送/订阅模式：广播发送的消息（" + i + ")";
            channel.basicPublish(FANOUT_EXCHANGE, "", null, message.getBytes());
            System.out.println("已发送消息：" + message);
        }
        // 关闭资源
        channel.close();
        connection.close();
    }
}
