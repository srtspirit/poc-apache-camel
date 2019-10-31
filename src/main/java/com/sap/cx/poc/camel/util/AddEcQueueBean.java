package com.sap.cx.poc.camel.util;

import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("addEcQueueBean")
public class AddEcQueueBean
{
	@Autowired
	private CamelConfiguration camelConfiguration;

	public void process(final String body) throws Exception
	{
		getCamelConfiguration().addEcRoute(body.replace("\"", ""));
	}

	public CamelConfiguration getCamelConfiguration()
	{
		return camelConfiguration;
	}

	public void setCamelConfiguration(final CamelConfiguration camelConfiguration)
	{
		this.camelConfiguration = camelConfiguration;
	}
}
