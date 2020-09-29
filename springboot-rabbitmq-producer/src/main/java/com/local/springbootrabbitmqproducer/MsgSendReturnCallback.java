package com.local.springbootrabbitmqproducer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息失败回调方法
 */
public class MsgSendReturnCallback implements RabbitTemplate.ReturnCallback {

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		String msgJson = new String(message.getBody());
		System.out.println("Returned Message: " + msgJson);
	}

}
