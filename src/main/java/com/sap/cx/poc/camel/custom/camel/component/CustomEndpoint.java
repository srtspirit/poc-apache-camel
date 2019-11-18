package com.sap.cx.poc.camel.custom.camel.component;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;


public class CustomEndpoint extends DefaultEndpoint
{
	public CustomEndpoint(final String endpointUri, final Component component)
	{
		super(endpointUri, component);
	}

	@Override
	public Producer createProducer() throws Exception
	{
		return null;
	}

	@Override
	public Consumer createConsumer(final Processor processor) throws Exception
	{
		return new CustomConsumer(this, processor);
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
}
