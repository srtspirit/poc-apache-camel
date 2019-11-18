package com.sap.cx.poc.camel.custom.camel.component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.IMessageSession;
import com.microsoft.azure.servicebus.ISessionHandler;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.SessionHandlerOptions;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;


public class CustomConsumer extends DefaultConsumer
{
	QueueClient queueClient;
	Endpoint endpoint;

	public CustomConsumer(final Endpoint endpoint, final Processor processor)
	{
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception
	{
		QueueClient receiveClient = new QueueClient(new ConnectionStringBuilder(
				"Endpoint=sb://storemanager.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=TGF+wMnnLKaUoG0O+ySdHCo+etfkm7g6xxw1UxtCEJY=" ,
				"ec-session-enabled-queue"), ReceiveMode.PEEKLOCK);

		receiveClient.registerSessionHandler(new ISessionHandler()
		{
			@Override
			public CompletableFuture<Void> onMessageAsync(final IMessageSession session, final IMessage message)
			{

				Exchange exchange = getEndpoint().createExchange(ExchangePattern.InOut);
				exchange.getIn().setMessageId(message.getMessageId());

				boolean endTransaction = false;

				try {
					String json = new String(message.getBody(), "UTF-8");
					exchange.getIn().setBody(json);
					exchange.getIn().setHeader("label", message.getLabel());
					exchange.getIn().setHeader("contentType", message.getContentType());

					log.info("Received message from topic " + " sessionId: " + message.getSessionId() + " label=" + message.getLabel() + " payload=" + json);

					System.out.println("=====Before sending to processor");
					getProcessor().process(exchange);
					System.out.println("=====Returned from processor");

					endTransaction = true;

				} catch (Exception e) {
					log.error("Failed processing exchange: " + e.getMessage(), e);
				}

				// TODO make this configurable
				if (endTransaction) {
					return receiveClient.completeAsync(message.getLockToken());
				} else {
					return receiveClient.abandonAsync(message.getLockToken());
				}
			}

			@Override
			public CompletableFuture<Void> OnCloseSessionAsync(final IMessageSession session)
			{
				return null;
			}

			@Override
			public void notifyException(final Throwable exception, final ExceptionPhase phase)
			{

			}
		}, new SessionHandlerOptions(1, true, Duration.ofMinutes(1)), Executors.newSingleThreadExecutor());
	}
}
