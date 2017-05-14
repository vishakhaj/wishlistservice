package com.wishlistservice.config.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
