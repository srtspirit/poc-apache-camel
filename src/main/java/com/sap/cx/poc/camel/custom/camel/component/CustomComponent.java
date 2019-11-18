package com.sap.cx.poc.camel.custom.camel.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;


public class CustomComponent extends DefaultComponent
{
	@Override
	protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters)
			throws Exception
	{
		return new CustomEndpoint(uri, this);
	}
}
