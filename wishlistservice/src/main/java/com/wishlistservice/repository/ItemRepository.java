package com.wishlistservice.repository;

import java.net.URISyntaxException;

import com.wishlistservice.domain.Item;

public interface ItemRepository {

	public void cacheAllItems() throws URISyntaxException;
	
	public Item findItemByItemId(String itemId);
	
}
