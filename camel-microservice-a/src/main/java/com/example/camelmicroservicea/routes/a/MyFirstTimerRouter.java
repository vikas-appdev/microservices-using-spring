package com.example.camelmicroservicea.routes.a;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// queue , timer
		// transformation
		// database, log
		from("timer:first-timer")
		//.transform().constant("My Constant Message")
		//.transform().constant("Time now is: "+LocalDateTime.now())
		.bean("getCurrentTimeBean")
		.to("log:first-timer");
		
	}
	

}

@Component
class GetCurrentTimeBean{
	
	public String getCurrentTime() {
		return "TIme now is: "+LocalDateTime.now();
	}
}
