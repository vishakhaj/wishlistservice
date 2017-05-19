package com.wishlistservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.service.WishlistService;

@RestController
@RequestMapping(value = "/{client}/{locale}/api/wishlists")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllWishlists() {
		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("listOfWishlists", wishlistService.getAllWishlists());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Wishlist createWishlist(@Valid @RequestBody Wishlist wishlist) {
		return wishlistService.createWishlist(wishlist);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteWishlist(@PathVariable("id") String id) {
		wishlistService.deleteWishlist(id);
	}
}
