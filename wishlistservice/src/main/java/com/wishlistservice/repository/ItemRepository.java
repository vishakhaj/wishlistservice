package com.wishlistservice.repository;

import java.net.URISyntaxException;
import java.util.List;

import com.wishlistservice.domain.Item;

public interface ItemRepository {

	public void cacheAllItems() throws URISyntaxException;
	
	public Item findItemByItemId(String itemId);
	
	public List<Item> findAllItems();
	
}
