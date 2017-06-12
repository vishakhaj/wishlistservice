package com.wishlistservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wishlistservice.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

}
