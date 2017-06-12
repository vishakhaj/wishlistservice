package com.wishlistservice.urls;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wishlistservice.config.consul.ConsulConfigItem;
import com.wishlistservice.config.consul.ConsulRepository;

@Component
public class UrlFactoryImpl implements UrlFactory{

	private static final String CONSUL_PRODUCT_KEY = "wishlistservice/urls/products";
	
	private static final String CONSUL_USER_PATH = "wishlistservice/urls/users";
	
	@Autowired
	private ConsulRepository consulRepository;
	
	@Override
	public URI createProducts() throws URISyntaxException {
		Optional<List<ConsulConfigItem>> consulItemList = consulRepository.requestConsulValuesRecursively(CONSUL_PRODUCT_KEY);
			
		for(ConsulConfigItem item : consulItemList.get()){
			String decodedConsulValue = consulRepository.decodedConsulValue(item.getKey(), item.getValue());
			return new URI(decodedConsulValue);
		}
		return null;	
	}

	@Override
	public URI createUserUrl() throws URISyntaxException {
		Optional<List<ConsulConfigItem>> consulItemList = consulRepository.requestConsulValuesRecursively(CONSUL_USER_PATH);
		
		for(ConsulConfigItem item : consulItemList.get()){
			String decodedConsulValue = consulRepository.decodedConsulValue(item.getKey(), item.getValue());
			return new URI(decodedConsulValue);
		}
		return null;
	}

}
