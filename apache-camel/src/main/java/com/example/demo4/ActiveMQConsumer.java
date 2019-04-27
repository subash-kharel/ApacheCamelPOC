package com.example.demo4;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMQConsumer {

	public static void main(String[] args) throws Exception {
		
		CamelContext camelContext= new DefaultCamelContext();
		ConnectionFactory connectionFactory= new ActiveMQConnectionFactory();
		camelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		camelContext.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("activemq:queue:my_queue")
				.to("seda:end");
				
				
			}
		});
		camelContext.start();
		
		ConsumerTemplate consumerTemplate= camelContext.createConsumerTemplate();
		String message=consumerTemplate.receiveBody("seda:end", String.class);
		System.out.println("This is my message---->"+ message);

	}

}
