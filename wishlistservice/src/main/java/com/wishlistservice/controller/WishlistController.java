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
		ModelAndView mav = new ModelAndView("index");
		wishlistService.getAllWishlistsFromCache();
		mav.addObject("listOfWishlists", wishlistService.getAllWishlists());
		return mav;
	}

	@RequestMapping(value="", method = RequestMethod.POST)
	public void createWishlist(@Valid @RequestBody Wishlist wishlist) {
		 wishlistService.createWishlist(wishlist);
		 System.out.println("Wishlist saved..");
		 
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void deleteWishlist(@PathVariable("id") String id) {
		wishlistService.deleteWishlist(id);
	}
}
