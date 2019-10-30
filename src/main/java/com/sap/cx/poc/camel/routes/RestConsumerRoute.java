package com.sap.cx.poc.camel.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class RestConsumerRoute extends RouteBuilder
{
	public void configure() throws Exception
	{
		//ExchangePattern
		restConfiguration().component("restlet").port(8081).host("localhost");

		rest("hybris-url")
				.put()
				.to("direct:resttest");
		from("direct:resttest")
				.log("restServer: ${body}")
				.setBody(constant("acknowledged"))
				.to("amqp:queue:camel-in?replyTo=camel");

	}
}
