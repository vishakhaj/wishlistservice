package com.wishlistservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.wishlistservice.common.NoItems;
import com.wishlistservice.domain.Wishlist;

@RepositoryRestResource(excerptProjection = NoItems.class)
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

}
