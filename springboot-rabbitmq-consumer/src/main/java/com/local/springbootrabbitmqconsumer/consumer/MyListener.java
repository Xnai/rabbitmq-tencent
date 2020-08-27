package com.local.springbootrabbitmqconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @RabbitListener(queues = "item_queue")
    public void receiveMsg(String msg) {
        System.out.println("消费者接收到消息：" + msg);
    }
}
