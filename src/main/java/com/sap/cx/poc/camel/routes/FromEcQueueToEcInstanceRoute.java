package com.sap.cx.poc.camel.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;


public class FromEcQueueToEcInstanceRoute extends RouteBuilder
{
	private String queueName;

	public FromEcQueueToEcInstanceRoute(final String queueName)
	{
		this.queueName = queueName;
	}

	public void configure() throws Exception
	{
		from("amqp:queue:" + queueName) //
				.id("===from a specific EC queue===")//
				.log("got from ec-outbound queue ${body}")
				.to("seda:saveMessageInfo") //
				.setExchangePattern(ExchangePattern.OutOnly) //
				.toD("rest:post:hybris-url/" + "${in.headers.tenantId}" + "/${in.headers.environmentId}?host=localhost:8081");

		from("seda:saveMessageInfo")
				.setBody(simple("${in.headers.tenantId}\n\r${in.headers.environmentId}"))
				.to("file:pending_updates?fileName=${header.messageId}");
	}
}
