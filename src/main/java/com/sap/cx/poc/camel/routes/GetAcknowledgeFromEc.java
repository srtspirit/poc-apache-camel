package com.sap.cx.poc.camel.routes;

import com.sap.cx.poc.camel.beans.DebugBean;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;


@Component
public class GetAcknowledgeFromEc extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		restConfiguration().component("restlet").port(8082).host("localhost");

		rest("get-news-from-hybris")
				.post("{corelId}")
				.to("direct:handleHybrisResponse");
		from("direct:handleHybrisResponse")
				.log("response from hybris: ${body}")
				.log("corelId: ${header.corelId}")
				//.setExchangePattern(ExchangePattern.InOut)
				.pollEnrich("file:pending_updates?fileName=${header.corelId}&charset=utf-8&noop=true")
				//.bean(new DebugBean())
				.process(exch -> {
					System.out.println("inside of processor");
					String body = exch.getMessage().getBody(String.class);
					String values [] = body.split("\n");
					exch.getIn().setHeader("tenantId", values[0].trim());
					exch.getIn().setHeader("environmentId", values[1].trim());
					exch.getIn().setBody(null);
				})
				.log("restored headers: ${in.headers.tenantId}-${in.headers.environmentId}")
				.setExchangePattern(ExchangePattern.InOnly)
				.to("amqp:topic:scc-internal")
				.end();

		/*from("file:pending_updates?fileName=${header.corelId}&charset=utf-8&noop=true").id("hui")
				//.bean(new DebugBean())
				.log("${body}");*/
	}
}
