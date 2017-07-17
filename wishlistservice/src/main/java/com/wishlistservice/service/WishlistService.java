package com.wishlistservice.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.mongodb.WriteResult;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.CustomWishlistRepository;
import com.wishlistservice.viewbean.WishlistViewBean;

@Service
@RestController
public class WishlistService {

	private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

	private static final WishlistViewBean NOT_FOUND_WISHLIST = new WishlistViewBean(true);

	private static final List<WishlistViewBean> NOT_FOUND_LISTOFWISHLISTS = new ArrayList<>();

	@Autowired
	private CustomWishlistRepository wishlistRepository;

	@Autowired
	private WishlistUIMapper wishlistUIMapper;

	public void getAllWishlistsFromCache() {
		wishlistRepository.cacheAllWishlists();
	}

	public void createWishlist(String client, String locale, String userId, Wishlist wishlist) {
		if (Strings.isNullOrEmpty(client) || Strings.isNullOrEmpty(locale) || Strings.isNullOrEmpty(userId)) {
			logger.error("Empty or null parameter");
		}

		if (wishlist != null) {
			wishlistRepository.createWishlistByUserId(client, locale, userId, wishlist);
		}
	}

	public Optional<WriteResult> updateWishlistByUserIdAndWishlistId(String userId, String wishlistId,
			Wishlist wishlist) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(wishlistId)) {
			logger.error("Empty or null parameter");
			return Optional.empty();
		}

		if (wishlist == null) {
			logger.error("Empty Wishlist Object");
			return Optional.empty();
		}

		return wishlistRepository.updateWishlistByUserIdAndWishlistId(userId, wishlistId, wishlist);
	}

	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(wishlistId)) {
			logger.error("Empty or null parameter");
		}

		wishlistRepository.deleteWishlistByUserIdAndWishlistId(userId, wishlistId);
	}

	public void deleteAllWishlistsByUserId(String userId) {
		wishlistRepository.deleteAllWishlistsByUserId(userId);
	}

	public List<WishlistViewBean> getAllWishlistsByUserId(String userId) throws ParseException {
		if (Strings.isNullOrEmpty(userId)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlistsByUserId = wishlistRepository.findAllWishlistsByUserId(userId);

		if (wishlistsByUserId.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlistsByUserId.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public WishlistViewBean getWishlistByUserIdAndWishlistId(String userId, String wishlistId) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(wishlistId)) {
			return NOT_FOUND_WISHLIST;
		}
		Optional<Wishlist> wishlist = wishlistRepository.findWishlistByUserIdAndWishlistId(wishlistId);

		if (wishlist.isPresent()) {
			if (wishlist.get().getUserId().equals(userId)) {
				return wishlistUIMapper.createUIViewBean(wishlist.get());
			} else if (!wishlist.get().getUserId().equals(userId)
					&& wishlist.get().getPrivacy().toString().equals("PUBLIC")) {
				return wishlistUIMapper.createUIViewBean(wishlist.get());
			}
		}
		return NOT_FOUND_WISHLIST;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndSource(String userId, String source) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(source)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}

		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSource(userId, source);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacy(String userId, String privacy) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(privacy)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndPrivacy(userId, privacy);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndType(String userId, String type) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(type)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndType(userId, type);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source,
			String privacy, String type) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(source) || Strings.isNullOrEmpty(privacy)
				|| Strings.isNullOrEmpty(type)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository
				.findAllWishlistsByUserIdAndSourceAndPrivacyAndType(userId, source, privacy, type);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source,
			String privacy) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(source) || Strings.isNullOrEmpty(privacy)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacy(userId,
				source, privacy);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndType(String userId, String source, String type) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(source) || Strings.isNullOrEmpty(type)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSourceAndType(userId, source,
				type);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy, String type) {
		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(privacy) || Strings.isNullOrEmpty(type)) {
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndPrivacyAndType(userId,
				privacy, type);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}

	public List<WishlistViewBean> getAllPublicWishlists(String privacy) {
		if(Strings.isNullOrEmpty(privacy)){
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllPublicWishlists(privacy);
		if (wishlists.isPresent()) {
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}
	
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSortOrder(String userId, String sortOrder){
		if(Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(sortOrder)){
			return NOT_FOUND_LISTOFWISHLISTS;
		}
		Optional<List<Wishlist>> wishlists = wishlistRepository.findAllWishlistsByUserIdAndSortOrder(userId, sortOrder);
		if(wishlists.isPresent()){
			return wishlistUIMapper.createUIViewBeanList(wishlists.get());
		}
		return NOT_FOUND_LISTOFWISHLISTS;
	}
	
	@RequestMapping(value="rest/unique/items/{userId}", method=RequestMethod.GET)
	public Set<Item> getAllUniqueWishlistItemsByUserId(@PathVariable("userId") String userId){
		getAllWishlistsFromCache();
		if(Strings.isNullOrEmpty(userId)){
			logger.error("Empty or null parameter");
			return null;
		}
		
		return wishlistRepository.findAllUniqueWishlistItemsByUserId(userId);
	}

}
