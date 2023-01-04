package com.demo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Component
@Configuration
public class CreateTweetDelegate implements JavaDelegate {

	private final Logger LOGGER = LoggerFactory.getLogger(CreateTweetDelegate.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String content = "suraj Rk"; //(String) execution.getVariable("content");
	    if (content.equals("Network error")) {
	        throw new RuntimeException("simulated network error");
	      }
	    
		
		LOGGER.info("Publishing tweet: " + content);
		AccessToken accessToken = new AccessToken("220324559-YfhutOgjLKsgPFXGGkda4q72rfeEp2nprXZHDTQf",
				"QmlH4CUdDDav3u2rrE2MQCmWwKfVHkcc8qlGpu6SMn0uD");
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("XSQGUIOwiOs8p55NMijlObpPu", "ZXDVbURd1WMUFBmygQ4Je8PPR0gkaKwczkgQ6YfgPfF0MYnIzY");
		twitter.setOAuthAccessToken(accessToken);
		twitter.updateStatus(content);
	}
}
