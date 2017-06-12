package com.wishlistservice.urls;

import java.net.URI;
import java.net.URISyntaxException;

public interface UrlFactory {

	public URI createProducts() throws URISyntaxException;
	
	public URI createUserUrl() throws URISyntaxException;
}
