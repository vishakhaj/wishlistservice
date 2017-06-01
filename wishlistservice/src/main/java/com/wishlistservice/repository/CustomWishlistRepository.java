package com.wishlistservice.repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.wishlistservice.common.Client;
import com.wishlistservice.domain.Wishlist;

public interface CustomWishlistRepository {

	public void cacheAllWishlists();
	
	public Optional<List<Wishlist>> findAllWishlistsByClientAndLocale(Client client, Locale locale);
	
	public void createWishlist(Wishlist wishlist);
	
	public void deleteWishlist(String id);
}
