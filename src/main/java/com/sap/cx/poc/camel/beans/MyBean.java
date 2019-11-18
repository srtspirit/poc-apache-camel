package com.sap.cx.poc.camel.beans;

import org.apache.camel.Exchange;


public class MyBean
{
	public Exchange hui(final Exchange h)
	{
		System.out.println("inside of a bean");
		return h;
	}
}
