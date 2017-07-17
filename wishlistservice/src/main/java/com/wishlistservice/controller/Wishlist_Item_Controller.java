package com.wishlistservice.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wishlistservice.service.Wishlist_Item_Service;

@RestController
@RequestMapping("{client}/{locale}/api")
public class Wishlist_Item_Controller {
	
	@Autowired
	private Wishlist_Item_Service wishlist_itemService;

	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}/items/item/{itemId}", method = RequestMethod.PUT)
	public void updateWishlistByUserIdAndWishlistId(@PathVariable("userId") String userId, @PathVariable("wishlistId") String wishlistId,
			@PathVariable("itemId") String itemId,
			HttpServletResponse response) throws URISyntaxException {
		wishlist_itemService.addItemToWishlist(userId, wishlistId, itemId);
		response.setStatus(HttpStatus.CREATED.value());
	}
	
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}/items/item/{itemId}", method = RequestMethod.DELETE)
	public void deleteWishlistByUserIdAndWishlistId(@PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, 
			@PathVariable("itemId") String itemId,
			HttpServletResponse response) throws URISyntaxException {
		wishlist_itemService.deleteItemFromWishlist(userId, wishlistId, itemId);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
