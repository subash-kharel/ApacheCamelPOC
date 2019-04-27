package com.example.demo3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class ProdAndConsumerExample {

	public static void main(String[] args) throws Exception {
	
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				from("direct:start")
				.process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println("I am the processor----------------");
						String exchangeMessage=exchange.getIn().getBody(String.class);
						exchangeMessage= exchangeMessage + " mesage by subash";
						System.out.println("In processor message---->"+ exchangeMessage);
						exchange.getOut().setBody(exchangeMessage, String.class);
					}
				})
				.to("seda:end");
				
			}
		});
		context.start();
	ProducerTemplate producerTemplate= context.createProducerTemplate();
	System.out.println("Sending Message-------->");
	producerTemplate.sendBody("direct:start", "Hello EveryOne");
	
	
	ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
	System.out.println("Receiving Message------------>");
	String message= consumerTemplate.receiveBody("seda:end", String.class);
	System.out.println("hello---------");
	System.out.println("Received message is ---->"+ message);
	}

}
