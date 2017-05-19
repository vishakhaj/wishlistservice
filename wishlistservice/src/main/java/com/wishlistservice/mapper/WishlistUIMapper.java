package com.wishlistservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistUIMapper {

	public List<WishlistViewBean> createWishlists(List<Wishlist> wishlists) {

		return wishlists.stream()
				.map(wishlist -> new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(),wishlist.getDescription()))
				.collect(Collectors.toList());
	}
}
