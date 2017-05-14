package com.wishlistservice.repository;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wishlistservice.config.resttemplate.RestTemplateConfig;
import com.wishlistservice.domain.Product;
import com.wishlistservice.urls.UrlFactory;

@RestController
public class ProductRepository {

	@Autowired
	private UrlFactory urlFactory;
	
	@Autowired
	private RestTemplateConfig restTemplateConfig;
	
	@RequestMapping("rest/products")
	public Product[] getProducts() throws URISyntaxException{
		URI productsUri = urlFactory.createProducts();
		Product[] products = restTemplateConfig.restTemplate().getForObject(productsUri, Product[].class);
		return products;
	}
}
