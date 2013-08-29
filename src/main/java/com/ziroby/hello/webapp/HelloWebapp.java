package com.ziroby.hello.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.ziroby.hello.service.HelloWorldService;

@Path("/hello")
public class HelloWebapp {
	private static HelloWorldService helloWorldService = new HelloWorldService();
	
	@GET()
	public String hello() {
		return helloWorldService.sayHello();
	}
}
