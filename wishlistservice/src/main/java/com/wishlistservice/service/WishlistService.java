package com.wishlistservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.mongodb.WriteResult;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.CustomWishlistRepository;
import com.wishlistservice.viewbean.WishlistViewBean;

@Service
public class WishlistService {

	private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

	@Autowired
	private CustomWishlistRepository wishlistRepository;

	@Autowired
	private WishlistUIMapper wishlistUIMapper;

	public void getAllWishlistsFromCache() {
		wishlistRepository.cacheAllWishlists();
	}

	public List<WishlistViewBean> getAllWishlistsByUserId(String userId) {
		List<WishlistViewBean> wishlists = new ArrayList<>();
		
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Null or empty User ID");
			return null;
		}

		Optional<List<Wishlist>> wishlistsByUserId = wishlistRepository.findAllWishlistsByUserId(userId);
		if (wishlistsByUserId.isPresent() && wishlistsByUserId != null) {
			wishlists = wishlistUIMapper.createUIViewBeanList(wishlistsByUserId.get());
		}
		return wishlists;
	}

	public void createWishlist(String client, String locale, String userId, Wishlist wishlist) {

		if(Strings.isNullOrEmpty(client) || Strings.isNullOrEmpty(locale) || Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null parameter");
		}
		
		if (wishlist == null) {
			logger.error("Empy Wishlist Object");
		}

		wishlistRepository.createWishlistByUserId(client, locale, userId, wishlist);
	}

	public Optional<WriteResult> updateWishlistByUserIdAndWishlistId(String userId, String wishlistId, Wishlist wishlist) {
		if (Strings.isNullOrEmpty(wishlistId) || Strings.isNullOrEmpty(userId)) {
			logger.error("Empty or null parameter");
			return Optional.empty();
		}

		if (wishlist == null) {
			logger.error("Empty Wishlist Object");
			return Optional.empty();
		}

		return wishlistRepository.updateWishlistByUserIdAndWishlistId(userId, wishlistId, wishlist);
	}

	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId) {

		if (Strings.isNullOrEmpty(wishlistId) || Strings.isNullOrEmpty(userId)) {
			logger.error("Wishlist is is null or empty");
		}

		wishlistRepository.deleteWishlistByUserIdAndWishlistId(userId, wishlistId);
	}
	
	public Wishlist getWishlistByUserIdAndWishlistId(String userId, String wishlistId) {
		Wishlist wishlist = new Wishlist();

		if (Strings.isNullOrEmpty(wishlistId)) {
			logger.error("Empty or null parameter");
			return null;
		}
		
		wishlist = wishlistRepository.findWishlistByUserIdAndWishlistId(wishlistId);
		
		if (wishlist != null) {
			if(wishlist.getUserId().equals(userId)){
				return wishlist;
			}else if(!wishlist.getUserId().equals(userId) && wishlist.getPrivacy().toString().equals("PUBLIC")){
				return wishlist;
			}
			else if(!wishlist.getUserId().equals(userId) && wishlist.getPrivacy().toString().equals("PRIVATE")){
				return null;
			}
		}

		return null;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndSource(String userId, String source){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		if(source == null){
			logger.error("Source is not present");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSource(userId, source);
		return wishlists;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndPrivacy(String userId, String privacy){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		if(privacy == null){
			logger.error("Privacy is not present");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndPrivacy(userId, privacy);
		return wishlists;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndType(String userId, String type){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		if(type == null){
			logger.error("Privacy is not present");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndType(userId, type);
		return wishlists;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source, String privacy, String type){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		if(type == null){
			logger.error("Privacy is not present");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacyAndType(userId, source, privacy, type);
		return wishlists;
	}

	public List<Wishlist> getAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source, String privacy){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacy(userId, source, privacy);
		return wishlists;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndSourceAndType(String userId, String source, String type){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSourceAndType(userId, source, type);
		return wishlists;
	}
	
	public List<Wishlist> getAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy, String type){
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null user ID");
			return null;
		}
		List<Wishlist> wishlists = wishlistRepository.findAllWishlistsByUserIdAndPrivacyAndType(userId, privacy, type);
		return wishlists;
	}
	
	public List<Wishlist> getAllPublicWishlists(){
		List<Wishlist> wishlists = wishlistRepository.findAllPublicWishlists();
		return wishlists;
	}

}
