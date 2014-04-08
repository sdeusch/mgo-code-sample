package mgo.controller;

import java.io.StringWriter;

import mgo.healthcheck.HealthCheckPoint;
import mgo.healthcheck.MongoDBHealthCheck;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Checks all dependent components 
 * @author deuscs01
 */
@Controller
public class HealthcheckController {
	private static final Logger LOG = Logger.getLogger(HealthcheckController.class);
		
	@Autowired
	private MongoDBHealthCheck mongoCheck;
	
	public void setMongoCheck(MongoDBHealthCheck mongoCheck) {
		this.mongoCheck = mongoCheck;
	}

	@RequestMapping("/health")
    public @ResponseBody Object checkHealth() {
		boolean netOK = false;
		StringWriter sw = new StringWriter();	
		sw.append("<H1>Healthcheck</h1>\n<pre>");
		String format = "%1$10s%2$80s%3$10s\n";
		sw.append(String.format(format,"","","Status"));
		
		/*
		 * Iterate through all health-check dependencies and build net result 
		 */
		HealthCheckPoint [] healthchecks = new HealthCheckPoint [] { mongoCheck };		
		for(HealthCheckPoint hc : healthchecks) {			
			netOK = hc.checkStatus();
			sw.append(String.format(format, hc.description(), hc.getMessage(), hc.checkStatus()?"OK":"NOT-OK"));
		}
		
		if(netOK) {
			sw.append(String.format(format, "", "", "ALL-OK"));
		} else {
			sw.append(String.format(format, "", "", "NOT-OK"));
		}
		sw.append("</pre>");
		return sw.toString();
	}
	
}
