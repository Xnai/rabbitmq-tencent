package com.local.springbootrabbitmqproducer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息确认
 */
public class MsgSendConfirmCallback implements RabbitTemplate.ConfirmCallback {

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if(ack) {
			System.out.println("消息确认成功...");
		}else {
			// 处理丢失的消息
			System.out.println("消息确认失败，" + cause);
		}
	}
}
