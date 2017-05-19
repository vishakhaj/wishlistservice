package com.wishlistservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wishlistservice.domain.Wishlist;

public interface WishlistRepository extends MongoRepository<Wishlist, String>, CustomWishlistRepository {

}
