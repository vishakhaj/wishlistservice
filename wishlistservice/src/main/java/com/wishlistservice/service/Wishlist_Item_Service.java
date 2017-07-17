package com.wishlistservice.service;

import java.net.URISyntaxException;
import java.util.Optional;

import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.repository.CustomWishlistRepository;
import com.wishlistservice.repository.ItemRepository;
import com.wishlistservice.repository.Wishlist_Item_Repository;

@Service
public class Wishlist_Item_Service {

	private static final Logger logger = LoggerFactory.getLogger(Wishlist_Item_Service.class);
	
	@Autowired
	private Wishlist_Item_Repository wishlist_itemRepository;
	
	@Autowired
	private CustomWishlistRepository wishlistRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	public Optional<WriteResult> addItemToWishlist(String userId, String wishlistId, String itemId) throws URISyntaxException{
		if(Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(wishlistId) || Strings.isNullOrEmpty(itemId)){
			logger.error("Empty or null parameter");
			return Optional.empty();
		}
		
		Wishlist wishlist = getWishlistById(wishlistId);
	
		if(wishlist == null){
			logger.error("Empty Wishlist Object");
			return Optional.empty();
		}
	
		Item item = getItemById(itemId);
		
		if(item == null){
			logger.error("Empty Item Object");
			return Optional.empty();
		}
		
		if(wishlist.getUserId().equals(userId) && wishlist.getWishlistId().equals(wishlistId)){
			return wishlist_itemRepository.addItemToWishlist(wishlist, item);
		}
		
		return Optional.empty();
	}
	
	public Optional<WriteResult> deleteItemFromWishlist(String userId, String wishlistId, String itemId) throws URISyntaxException{
		if(Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(wishlistId) || Strings.isNullOrEmpty(itemId)){
			logger.error("Empty or null parameter");
			return Optional.empty();
		}
		
		Wishlist wishlist = getWishlistById(wishlistId);
		
		if(wishlist == null){
			logger.error("Empty Wishlist Object");
			return Optional.empty();
		}
		
		Item item = getItemById(itemId);
		
		if(item == null){
			logger.error("Empty Item Object");
			return Optional.empty();
		}
		
		if(wishlist.getUserId().equals(userId) && wishlist.getWishlistId().equals(wishlistId) && 
				item.getItemId().equals(itemId)){
			return wishlist_itemRepository.deleteItemFromWishlist(wishlist, item);
		}
		
		return Optional.empty();
	}
	
	private Wishlist getWishlistById(String wishlistId){
		wishlistRepository.cacheAllWishlists();
		Optional<Wishlist> wishlist = wishlistRepository.findWishlistByUserIdAndWishlistId(wishlistId);
		return wishlist.get();
	}
	
	private Item getItemById(String itemId) throws URISyntaxException{
		itemRepository.cacheAllItems();
		Item item = itemRepository.findItemByItemId(itemId);
		return item;
	}
}
