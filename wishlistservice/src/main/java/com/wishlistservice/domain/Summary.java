package com.wishlistservice.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="wishlist", types=Wishlist.class)
public interface Summary {
	String getName();
}
