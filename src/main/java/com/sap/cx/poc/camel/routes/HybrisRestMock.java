package com.sap.cx.poc.camel.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class HybrisRestMock extends RouteBuilder
{
	public void configure() throws Exception
	{
		restConfiguration().component("restlet").port(8081).host("localhost");

		rest("hybris-url")
				.post("{tenantId}/{environmentId}")
				.to("direct:resttest");
		from("direct:resttest")
				.id("===hybris rest mock===")//
				.log("restServer: ${body}")
				.to("file://created_stores?fileName=${header.tenantId}-${header.environmentId}")
				.end();
	}
}
