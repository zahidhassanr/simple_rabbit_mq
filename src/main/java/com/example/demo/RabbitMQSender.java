package com.example.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;
	
	
	public void send(Employee company, String queue) {
		rabbitTemplate.convertAndSend(exchange, DemoApplication.queue_routingKey_map.get(queue), company);
		System.out.println("Send msg to queue " + queue + " is " + company);  
	}
}

