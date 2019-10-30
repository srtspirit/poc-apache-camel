package com.sap.cx.poc.camel;


import com.sap.cx.poc.camel.routes.MyRoute;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.main.Main;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application
{
	public static void main(final String args[]) throws Exception
	{
		SpringApplication.run(Application.class, args);
	}
}
