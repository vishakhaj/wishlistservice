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

	private static final String CONSUL_ITEM_PATH = "domaindriven/wishlistservice/urls/items";
	
	@Autowired
	private ConsulRepository consulRepository;
	
	@Override
	public URI createItemUrl() throws URISyntaxException {
		Optional<List<ConsulConfigItem>> consulItemList = consulRepository.requestConsulValuesRecursively(CONSUL_ITEM_PATH);
		
		for(ConsulConfigItem item : consulItemList.get()){
			String decodedConsulValue = consulRepository.decodedConsulValue(item.getKey(), item.getValue());
			return new URI(decodedConsulValue);
		}
		return null;
	}

}
