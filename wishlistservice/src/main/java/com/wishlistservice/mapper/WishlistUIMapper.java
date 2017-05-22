package com.wishlistservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistUIMapper {

	private static final Logger logger = LoggerFactory.getLogger(WishlistUIMapper.class);
	
	public List<WishlistViewBean> createUIViewBean(List<Wishlist> wishlists) {
		
		if(wishlists == null){
			logger.info("Wishlist is null");
			return null;
		}
		
		return wishlists.stream()
				.map(wishlist -> new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(), wishlist.getDescription()))
				.collect(Collectors.toList());
	}
}
	
	

