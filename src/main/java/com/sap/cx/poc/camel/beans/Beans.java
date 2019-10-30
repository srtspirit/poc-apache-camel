package com.sap.cx.poc.camel.beans;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.component.rest.RestComponent;
import org.apache.camel.spi.RestConsumerFactory;
import org.apache.camel.spi.RestProducerFactory;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Beans
{
	@Bean
	public JmsConnectionFactory jmsConnectionFactory()
	{
		final JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory();
		jmsConnectionFactory.setRemoteURI("amqps://storemanager.servicebus.windows.net");
		jmsConnectionFactory.setUsername("RootManageSharedAccessKey");
		jmsConnectionFactory.setPassword("TGF+wMnnLKaUoG0O+ySdHCo+etfkm7g6xxw1UxtCEJY=");

		return jmsConnectionFactory;
	}
	@Bean("amqp")
	public AMQPComponent amqpComponent(JmsConnectionFactory jmsConnectionFactory)
	{
		return new AMQPComponent(jmsConnectionFactory);
	}

	@Bean
	public HttpComponent httpComponent()
	{
		return new HttpComponent();
	}

	@Bean
	public RestComponent restConsumerFactory()
	{
		return new RestComponent();
	}


}
