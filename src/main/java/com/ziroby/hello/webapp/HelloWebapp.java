package com.ziroby.hello.webapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWebapp {
	@GET()
	public String hello() {
		return "";
	}
}
