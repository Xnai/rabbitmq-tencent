package com.local.springbootrabbitmqproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用配置的过期时间
     */
    @Test
    public void test1() {
        rabbitTemplate.convertAndSend("my_ttl_queue", "测试6秒钟过期消息");
    }

    // 程序设定过期时间
    @Test
    public void test2() {
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("5000"); // 设定消息过期时间为5s
        Message message = new Message("过期时间为5s的消息".getBytes(), properties);
        rabbitTemplate.convertAndSend("my_ttl_queue", message);
        /*
        expiration 字段以微妙为单位表示ttl值。且与 x-message-ttl 具有相同的约束条件。
        因为 expiration 字段必须指定为字符串类型，broker将只会接受以字符串形式表达的数字。

        当同时指定 queue 和 message 的ttl时，则两者中较小的会起作用。
         */
    }
}
