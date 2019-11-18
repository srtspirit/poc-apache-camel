package com.sap.cx.poc.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.stereotype.Component;


@Component
public class MyRoute extends RouteBuilder
{
	TransactionAutoConfiguration transactionAutoConfiguration;

	@Override
	public void configure() throws Exception
	{
		from("custom:ec-session-enabled-queue")
				.bean(new MyRoute())
				.log("${body}");
	}
}
