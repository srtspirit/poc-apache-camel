package com.sap.cx.poc.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Message;


public class DebugBean
{
	public Message process(Message exchange)
	{
		System.out.println("inside of a bean");
		return exchange;
	}
}
