package com.wishlistservice.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.wishlistservice.repository.CustomWishlistRepository;


@Component
public class WishlistInfoContributor implements InfoContributor{

	@Autowired
	private CustomWishlistRepository wishlistRepository;
	
	@Override
	public void contribute(Builder builder) {
		// TODO Auto-generated method stub
		Map<String, Integer> wishlistDetails = new HashMap<>();
        wishlistDetails.put("public", wishlistRepository.countWishlists("PUBLIC"));
        wishlistDetails.put("private", wishlistRepository.countWishlists("PRIVATE"));
        builder.withDetail("wishlists", wishlistDetails);
	}

}
