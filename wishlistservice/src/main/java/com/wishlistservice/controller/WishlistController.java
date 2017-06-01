package com.wishlistservice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import com.wishlistservice.viewbean.WishlistViewBean;

@RestController
@RequestMapping(value = "/{client}/{locale}/api/wishlists")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllWishlists() {
		ModelAndView mav = new ModelAndView("index");
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByClientAndLocale();
		if (listOfWishlists.isEmpty()) {
			return mav;
		}
		mav.addObject("listOfWishlists", listOfWishlists);
		return mav;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView createWishlist(@Valid @RequestBody Wishlist wishlist, HttpServletResponse response)
			throws IOException {

		ModelAndView mav = new ModelAndView("index");
		wishlistService.createWishlist(wishlist);
		System.out.println("Wishlist saved..");
		response.sendRedirect("/{client}/{locale}/api/wishlists");
		return mav;
	}

	@RequestMapping(value = "wishlist/{id}", method = RequestMethod.GET)
	public ModelAndView deleteWishlist(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView("index");
		wishlistService.deleteWishlist(id);
		response.sendRedirect("/{client}/{locale}/api/wishlists");
		return mav;
	}
}
