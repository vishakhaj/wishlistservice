package com.wishlistservice.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.wishlistservice.domain.Item;
import com.wishlistservice.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired 
	private ItemRepository itemRepository;
	
	public void getCachedItems() throws URISyntaxException{
		itemRepository.cacheAllItems();
	}
	
	public Item getItemByItemId(String itemId){
		if(Strings.isNullOrEmpty(itemId)){
			return null;
		}
		return itemRepository.findItemByItemId(itemId);
	}
	
	public List<Item> getAllItems(){
		 return itemRepository.findAllItems();
	}
}
