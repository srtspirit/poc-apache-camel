package com.sap.cx.poc.camel.util;

import javax.annotation.PostConstruct;

import com.sap.cx.poc.camel.routes.FromEcQueueToEcInstanceRoute;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CamelConfiguration
{
	@Autowired
	private CamelContext camelContext;

	public void addEcRoute(final String ecQueueName) throws Exception
	{
		getCamelContext().addRoutes(new FromEcQueueToEcInstanceRoute(ecQueueName));
	}

	@PostConstruct
	public void init() throws Exception
	{
		addEcRoute("ec-outbound-tenantId-envId");
	}

	public CamelContext getCamelContext()
	{
		return camelContext;
	}

	public void setCamelContext(final CamelContext camelContext)
	{
		this.camelContext = camelContext;
	}
}
