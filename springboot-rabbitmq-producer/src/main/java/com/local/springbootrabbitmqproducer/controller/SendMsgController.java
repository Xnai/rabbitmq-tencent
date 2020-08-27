package com.local.springbootrabbitmqproducer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SendMsgController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RequestMapping("/sendMsg")
	public void sendMsg() {
		String message = "发送消息！";

		// 10节 17分16秒
	}
}
