package com.wishlistservice.repository;

import java.util.List;
import java.util.Locale;

import com.wishlistservice.common.Client;
import com.wishlistservice.domain.Wishlist;

public interface CustomWishlistRepository {

	public void cacheAllWishlists();
	
	public List<Wishlist> findAllWishlists(Client client, Locale locale);
	
	public void createWishlist(Wishlist wishlist);
	
	public void deleteWishlist(String id);

}
