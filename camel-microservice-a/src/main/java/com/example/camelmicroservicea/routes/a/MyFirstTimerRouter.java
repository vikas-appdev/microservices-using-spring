package com.example.camelmicroservicea.routes.a;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
	
	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent processingComponent;
	

	@Override
	public void configure() throws Exception {
		// queue , timer
		// transformation
		// database, log
		from("timer:first-timer")
		.log("${body}")
		.transform().constant("My Constant Message")
		.log("${body}")
		//.transform().constant("Time now is: "+LocalDateTime.now())
		//.bean("getCurrentTimeBean")
		.bean(getCurrentTimeBean, "getCurrentTime")
		.log("${body}")
		.bean(processingComponent)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");
		
	}
	

}

@Component
class GetCurrentTimeBean{
	
	public String getCurrentTime() {
		return "TIme now is: "+LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent{
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcessor implements Processor{

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange);
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
		
	}
	
}
