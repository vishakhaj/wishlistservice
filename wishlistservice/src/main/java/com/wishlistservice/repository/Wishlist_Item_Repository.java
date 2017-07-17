package com.wishlistservice.repository;

import java.util.Optional;

import com.mongodb.WriteResult;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;

public interface Wishlist_Item_Repository {

	public Optional<WriteResult> addItemToWishlist(Wishlist wishlist, Item item);
	
	public Optional<WriteResult> deleteItemFromWishlist(Wishlist wishlist, Item item);
}
