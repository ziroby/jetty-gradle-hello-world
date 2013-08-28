package com.ziroby.hello.webapp;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import org.junit.Test;

public class HelloIntegrationTest {
	private static String HELLO_URL = "http://localhost:8080/hello";
	
	@Test
	public void testHello() throws Exception {
		Client client = Client.create();
		WebResource webResource = client.resource(HELLO_URL);
		String response = webResource.get(String.class);
		
		assertThat(response, is("Hello, World!"));
	}
}
