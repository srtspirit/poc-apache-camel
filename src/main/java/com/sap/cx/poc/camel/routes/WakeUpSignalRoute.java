package com.sap.cx.poc.camel.routes;

import com.sap.cx.poc.camel.beans.MyBean;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class WakeUpSignalRoute extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		restConfiguration().component("restlet").port(8082).host("localhost");

		rest("wake-up").post("{queueName}").to("direct:wakeUp");


		//try activating route via controlbus component
		//drawbacks: how to stop?
		/*from("direct:wakeUp")
				//.setExchangePattern(ExchangePattern.InOut)
				.log("${header.queueName}")
				.to("controlbus:route?routeId=fromRetryQueue&action=start");

		from("amqp:queue:wake-up-signal-test").autoStartup(false)
				.routeId("fromRetryQueue")
			.bean(new MyBean())

			.log("${body}")
				.onCompletion().process(e -> {
			System.out.println("pnComplete");
		});*/


		//pollEnrich
	from("direct:wakeUp")
				//.setExchangePattern(ExchangePattern.InOut)
				.log("${header.queueName}")
			.bean(new MyBean())
			.transacted()
				.pollEnrich("amqp:queue:wake-up-signal-test?acknowledgementModeName=AUTO_ACKNOWLEDGE&transacted=true", 1000L)
			.setExchangePattern(ExchangePattern.InOnly)
			.bean(new MyBean())
				.log("Enriched message: ${body}")
			.end()

			.choice()
				.when(simple("${body} != null"))
					.log("message is not null. Next iteration")
					.to("direct:wakeUp")
				.endChoice()
				.otherwise()
					.log("NULL. END")
				.end();


				//.to("direct:wakeUp");

	}
}
