package com.example.demo;

import java.util.HashMap;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {


	@Value("${javainuse.rabbitmq.exchange}")
	String exchange;

	public static final HashMap<String, String> queue_routingKey_map = new HashMap<>();

	@Bean
	Queue queue1() {
		return new Queue("first_queue1", false);
	}
	
	@Bean
	Queue queue2() {
		return new Queue("first_queue2", false);
	}
	
	@Bean
	Queue queue3() {
		return new Queue("first_queue3", false);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(exchange);
	}

	@Bean
	Binding binding1(FanoutExchange exchange) {
	    return BindingBuilder.bind(queue1()).to(exchange); //.with(queue_routingKey_map.get(queue1().getName()));
	}

	@Bean
	Binding binding2(FanoutExchange exchange) {
	    return BindingBuilder.bind(queue2()).to(exchange);  //.with(queue_routingKey_map.get(queue2().getName()));
	}
	
	@Bean
	Binding binding3(FanoutExchange exchange) {
	    return BindingBuilder.bind(queue3()).to(exchange); //.with(queue_routingKey_map.get(queue3().getName()));
	}


	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	@RabbitListener(queues = "first_queue1")
	public void listen1(Employee in) {
	    System.out.println("Message read from first_queue1: " + in);
	}
	
	@RabbitListener(queues = "first_queue2")
	public void listen2(Employee in) {
	    System.out.println("Message read from first_queue2 : " + in);
	}
	
	@RabbitListener(queues = "first_queue3")
	public void listen3(Employee in) {
	    System.out.println("Message read from first_queue3 : " + in);
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	public static void main(String[] args) {

		queue_routingKey_map.put("first_queue1", "first_queue1_routing_key");
		queue_routingKey_map.put("first_queue2", "first_queue2_routing_key");
		queue_routingKey_map.put("first_queue3", "first_queue3_routing_key");
		SpringApplication.run(DemoApplication.class, args);
	}

}