package com.wishlistservice.repository;

import java.util.List;
import java.util.Optional;

import com.mongodb.WriteResult;
import com.wishlistservice.domain.Wishlist;

public interface CustomWishlistRepository {

	public void cacheAllWishlists();

	public Optional<List<Wishlist>> findAllWishlistsByUserId(String userId);

	public void createWishlistByUserId(String client, String locale, String userId, Wishlist wishlist);

	public Optional<WriteResult> updateWishlistByUserIdAndWishlistId(String userId, String wishlistId, Wishlist wishlist);

	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId);
	
	public Wishlist findWishlistByUserIdAndWishlistId(String wishlistId);
	
	public List<Wishlist> findAllPublicWishlists();
	
	public List<Wishlist> findAllWishlistsByUserIdAndSource(String userId, String source);
	
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacy(String userId, String privacy);
	
	public List<Wishlist> findAllWishlistsByUserIdAndType(String userId, String type);
	
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source, String privacy, String type);

	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source, String privacy);
	
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndType(String userId, String source, String type);
	
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy, String type);

}
