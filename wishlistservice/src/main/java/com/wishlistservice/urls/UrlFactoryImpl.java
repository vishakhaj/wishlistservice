package com.wishlistservice.urls;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wishlistservice.config.consul.ConsulConfigItem;
import com.wishlistservice.config.consul.ConsulRepository;

@Component
public class UrlFactoryImpl implements UrlFactory{

	private static final String CONSUL_PRODUCT_KEY = "wishlistservice/urls/products";
	
	@Autowired
	private ConsulRepository consulRepository;
	
	@Override
	public URI createProducts() throws URISyntaxException {
		List<ConsulConfigItem> consulItemList = consulRepository.requestConsulValuesRecursively(CONSUL_PRODUCT_KEY);
			
		for(ConsulConfigItem item : consulItemList){
			String decodedConsulValue = consulRepository.decodedConsulValue(item.getKey(), item.getValue());
			return new URI(decodedConsulValue);
		}
		return null;	
	}

}
