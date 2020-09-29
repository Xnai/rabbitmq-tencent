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

    /**
     * 过期消息被投递到死信队列
     * 投递一个正常的消息对了，但是该队列有过期时间，到了过期时间之后，消息会被发送到指定的死信队列
     */
    @Test
    public void test3() {
        rabbitTemplate.convertAndSend("my_normal_exchange", "my_ttl_dlx", "测试过期消息被转发到死信队列，过期时间为6s");
    }

    /**
     * 超过队列长度被投递到死信队列
     * 消息长度超过2，最早的消息会被投递到死信队列
     */
    @Test
    public void test4() {
        for (int i = 1; i < 4; i++) {
            String message = "测试超过队列长度被投递到死信队列，当前消息编号：" + i;
            rabbitTemplate.convertAndSend("my_normal_exchange", "my_max_dlx", message);
        }
    }

    /**
     * 消息确认测试，该确认由服务端返回，而不是接收端，调用 MsgSendConfirmCallback
     *
     */
    @Test
    public void test5() {
        // 路由键与队列同名
        rabbitTemplate.convertAndSend("spring_queue", "只发队列 spring_queue 的消息");
    }

    /**
     * 消息确认失败回调，由服务端返回，调用MsgSendReturnCallback
     * 刚开始，没有交换机叫 test_fail_exchange， 会打印：
     *      消息确认失败，channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'test_fail_exchange' in vhost '/', class-id=60, method-id=40)
     * 如果创建该交换机，则会打印：
     *      Returned Message: 测试消息发送失败进行确认应答
     *      消息确认成功...
     */
    @Test
    public void test6() {
        // exchange 正确， queue 错误， confirm 被回调， ack=true: return 被回调 replyText="NO_ROUTE"
        rabbitTemplate.convertAndSend("test_fail_exchange", "", "测试消息发送失败进行确认应答");
    }

}
