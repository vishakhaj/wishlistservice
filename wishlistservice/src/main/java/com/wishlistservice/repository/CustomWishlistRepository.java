package com.wishlistservice.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mongodb.WriteResult;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;

public interface CustomWishlistRepository {

	public void cacheAllWishlists();

	public void createWishlistByUserId(String client, String locale, String userId, Wishlist wishlist);

	public Optional<WriteResult> updateWishlistByUserIdAndWishlistId(String userId, String wishlistId, Wishlist wishlist);

	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId);

	public void deleteAllWishlistsByUserId(String userId);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserId(String userId) throws ParseException;

	public Optional<Wishlist> findWishlistByUserIdAndWishlistId(String wishlistId);
	
	public Optional<List<Wishlist>> findAllPublicWishlists(String privacy);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSource(String userId, String source);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndPrivacy(String userId, String privacy);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndType(String userId, String type);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source, String privacy, String type);

	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source, String privacy);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndType(String userId, String source, String type);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy, String type);
	
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSortOrder(String userId, String sortOrder);

	public Set<Item> findAllUniqueWishlistItemsByUserId(String userId);
	
	public Optional<List<Wishlist>> findAllWishlists();
	
	public int countWishlists(String privacy);
}
