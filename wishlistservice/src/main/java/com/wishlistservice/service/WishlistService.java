package com.wishlistservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.CustomWishlistRepository;
import com.wishlistservice.viewbean.WishlistViewBean;

@Service
public class WishlistService {

	private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

	private static final String client = System.getenv("CLIENT");

	private static final String locale = System.getenv("LOCALE");

	@Autowired
	private CustomWishlistRepository wishlistRepository;

	@Autowired
	private WishlistUIMapper mapper;

	public void getAllWishlistsFromCache() {
		wishlistRepository.cacheAllWishlists();
	}

	public List<WishlistViewBean> getAllWishlistsByClientAndLocale() {
		Optional<List<Wishlist>> allWishlists = wishlistRepository
				.findAllWishlistsByClientAndLocale(Clients.clientByShortName(client), Locale.forLanguageTag(locale));

		if (allWishlists == null || allWishlists.get().isEmpty()) {
			List<WishlistViewBean> viewBeanList = new ArrayList<>();
			logger.debug("Empty list");
			viewBeanList.add(new WishlistViewBean());
			return viewBeanList;

		}

		return mapper.createUIViewBean(allWishlists.get());
	}

	public void createWishlist(Wishlist wishlist) {

		if (wishlist == null) {
			logger.debug("Empy Wishlist Object");
		}

		wishlistRepository.createWishlist(wishlist);
	}

	public void deleteWishlist(String id) {

		if (Strings.isNullOrEmpty(id)) {
			logger.debug("Wishlist is is null or empty");
		}

		wishlistRepository.deleteWishlist(id);
	}

}
