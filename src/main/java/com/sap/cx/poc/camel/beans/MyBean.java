package com.sap.cx.poc.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.PollingConsumer;


public class MyBean
{
	public void h(final Exchange h)
	{
		System.out.println("inside of a bean");

	}
}
