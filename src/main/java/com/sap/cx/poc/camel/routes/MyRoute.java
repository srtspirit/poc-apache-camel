package com.sap.cx.poc.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.stereotype.Component;


@Component
public class MyRoute extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		from("custom:ec-session-enabled-queue")
				.bean(new MyRoute())
				.log("${body}")
				.toD("rest:post:hybris-url/" + "${in.headers.tenantId}" + "/${in.headers.environmentId}?host=localhost:8081");

		onException(RestletOperationException.class, HttpHostConnectException.class)
				.redeliveryPolicyRef("redelivery") // should retry the failed step according to the policy (defined in bean) then proceed to this failure branch
				.log("RestletOperationException")
				.to("file:ec-retry-later-queues")
				.handled(true);
	}
}
