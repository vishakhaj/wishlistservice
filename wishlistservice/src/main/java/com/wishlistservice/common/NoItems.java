package com.wishlistservice.common;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.wishlistservice.domain.Wishlist;

@Projection(name = "noItems", types = { Wishlist.class }) 
public interface NoItems {

	String getWishlistId();
	
	String getName();
	
	String getDescription();
	
	Date getCreatedAt();
	
	Source getSource();
	
	Type getType();
	
	Privacy getPrivacy();
	
	Date getModifiedOn();
	
	String getUserId();
	
}
