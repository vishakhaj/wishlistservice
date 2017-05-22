package com.wishlistservice.service;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.WishlistRepository;
import com.wishlistservice.utils.Context;
import com.wishlistservice.viewbean.WishlistViewBean;

@Service
public class WishlistService {

	private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private WishlistUIMapper mapper;
	
	public void getAllWishlistsFromCache(){
		wishlistRepository.cacheAllWishlists();
	}
	
	public List<WishlistViewBean> getAllWishlists() {
		List<Wishlist> wishlists = wishlistRepository.findAllWishlists(Context.client, Context.locale);

		if (wishlists == null) {
			logger.debug("Empty list");
		}

		return mapper.createUIViewBean(wishlists);
	}

	public void createWishlist(Wishlist wishlist) {

		if (wishlist == null) {
			logger.debug("Empy Wishlist Object");
		}
		
		wishlistRepository.createWishlist(wishlist);
	}

	public void deleteWishlist(String id) {
		
		if(Strings.isNullOrEmpty(id)){
			logger.debug("Wishlist is is null or empty");
		}
		
		wishlistRepository.deleteWishlist(id);
	}

}
