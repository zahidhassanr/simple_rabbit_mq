package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javainuse-rabbitmq/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("empName") String empName, @RequestParam("empId") String empId) {

		Employee emp = new Employee();
		for (int i = 0; i <= 20000; i++) {
			emp.setEmpId(empId + i);
			emp.setEmpName(empName + i);
			int remainder = i%3;
			if(remainder == 0) {
				rabbitMQSender.send(emp, "first_queue1");
			} else if(remainder == 1) {
				rabbitMQSender.send(emp, "first_queue2");
			} else {
				rabbitMQSender.send(emp, "first_queue3");
			}
		}
		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}

}
