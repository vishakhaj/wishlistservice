package com.wishlistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableAutoConfiguration
@EnableDiscoveryClient
public class WishlistserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistserviceApplication.class, args);
	}
}
