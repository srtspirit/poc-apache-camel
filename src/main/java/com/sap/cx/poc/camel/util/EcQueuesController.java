package com.sap.cx.poc.camel.util;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EcQueuesController extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		from("amqp:queue:camel-util")
				.bean("addEcQueueBean");
	}
}
