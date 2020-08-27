package com.local.springbootrabbitmqproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// 导入配置文件
@ImportResource("classpath:/spring/spring-rabbitmq.xml")
public class SpringbootRabbitmqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitmqProducerApplication.class, args);
	}

}
