package com.sap.cx.poc.camel.routes;

import com.sap.cx.poc.camel.models.CamelStore;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.stereotype.Component;


@Component
public class MyRoute extends RouteBuilder
{
	TransactionAutoConfiguration transactionAutoConfiguration;

	@Override
	public void configure() throws Exception
	{
		from("amqp:queue:camel")
				//.setBody(constant("hi"))
				.log("${body}")
				.log("${in.headers.tenantId}")
				//.toD("rest:put:hybris-url/" + simple("${in.headers.tenantId}").getText() + "/${in.headers.environmentId}?host=localhost:8081");
				.to("rest:put:hybris-url");
	}
}
