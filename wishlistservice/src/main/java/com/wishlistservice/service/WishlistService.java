package com.wishlistservice.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.WishlistRepository;
import com.wishlistservice.viewbean.WishlistViewBean;

@Service
public class WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private WishlistUIMapper mapper;

	public List<WishlistViewBean> getAllWishlists() {
		wishlistRepository.getAllWishlists();
		List<Wishlist> wishlists = wishlistRepository.findAllWishlists(Clients.clientByShortName("MediaDE"), Locale.GERMAN);
		return mapper.createWishlists(wishlists);
	}

	public void createWishlist(Wishlist wishlist) {
		wishlistRepository.createWishlist(wishlist);
	}

	public void deleteWishlist(String id) {
		wishlistRepository.deleteWishlist(id);
	}

}
