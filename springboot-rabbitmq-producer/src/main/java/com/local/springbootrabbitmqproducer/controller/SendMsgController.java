package com.local.springbootrabbitmqproducer.controller;

import com.local.springbootrabbitmqproducer.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送一条消息
	 * @param msg 消息内容
	 * @param routingKey 绑定的routingKey
	 * @return
	 */
	@RequestMapping("/sendMsg")
	public String sendMsg(@RequestParam String msg, @RequestParam String routingKey) {
		// 发送消息
		rabbitTemplate.convertAndSend(RabbitConfig.ITEM_TOPIC_EXCHAGNE, routingKey, msg);

		return "发送消息成功";
	}
}
