package com.sap.cx.poc.camel.beans;

import org.apache.camel.component.amqp.AMQPComponent;

import org.apache.camel.processor.RedeliveryPolicy;
import org.apache.qpid.jms.JmsConnectionFactory;
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

	@Bean
	public HttpComponent httpComponent()
	{
		return new HttpComponent();
	}

	@Bean
	public AMQPComponent amqpComponent(JmsConnectionFactory jmsConnectionFactory)
	{
		return new AMQPComponent(jmsConnectionFactory);
	}

	@Bean("redelivery")
	public RedeliveryPolicy redeliveryPolicy()
	{
		final RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setMaximumRedeliveries(5);
		redeliveryPolicy.setRedeliveryDelay(5000L);
		redeliveryPolicy.setBackOffMultiplier(2D);
		return redeliveryPolicy;
	}
}
