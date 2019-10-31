package com.sap.cx.poc.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class FromSccTopicRoute extends RouteBuilder
{
	private static final String TOPIC_SUBSCRIPTION_NAME = "scc-internal/subscriptions/camel";

	public void configure() throws Exception
	{
		from("amqp:topic:" + TOPIC_SUBSCRIPTION_NAME)//
				.id("===from scc-internal topic===")//
				.log("got from scc-intrenal topic ${in.headers.tenantID}-${in.headers.environmentId} ${body}") //
				.choice() //
				.when(header("type").contains("store")) //
				.log("type contains store. Send it to hybris")
				.toD("amqp:queue:ec-outbound-${in.headers.tenantID}-${in.headers.environmentId}") //
				.endChoice()//
				.end();
	}
}
