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

@RestController
@RequestMapping(value = "/{client}/{locale}/api")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	/**
	 * @return All Wish-lists of a specific user ID.
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", method = RequestMethod.GET)
	public ModelAndView getAllWishlistsByUserId(@PathVariable("userId") String userId) {
		ModelAndView mav = new ModelAndView("index");
		wishlistService.getAllWishlistsFromCache();
		mav.addObject("listOfWishlists", wishlistService.getAllWishlistsByUserId(userId));
		return mav;
	}

	/**
	 * @param client
	 *            - Takes the specified client from the REST url.
	 * @param locale
	 *            - Takes the specified locale from the REST url.
	 * @param userId
	 *            - Takes the user ID
	 * @param wishlist
	 *            - Takes the wish-list information specified in json Creates a
	 *            wish-list of a specific user.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", method = RequestMethod.POST)
	public void createWishlistByUserId(@PathVariable String client, @PathVariable String locale,
			@PathVariable("userId") String userId, @Valid @RequestBody Wishlist wishlist, HttpServletResponse response) {
		wishlistService.createWishlist(client, locale, userId, wishlist);
		System.out.println("Wishlist saved..");
	}

	/**
	 * @param wishlistId
	 *            - Takes the unique wish-list ID
	 * @return Wish-list of a specific wish-list ID
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.GET)
	public ModelAndView getWishlistByWishlistId(@PathVariable("userId") String userId, @PathVariable("wishlistId") String wishlistId) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("wishlistById", wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId));
		return mav;
	}

	/**
	 * @param wishlistId
	 *            - Takes the unique wish-list ID.
	 * @param wishlist
	 *            - Takes the wish-list information to updated Updates a
	 *            wish-list
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.POST)
	public void updateWishlistByWishlistId(@PathVariable("userId") String userId, @PathVariable("wishlistId") String wishlistId, @RequestBody Wishlist wishlist) {
		wishlistService.updateWishlistByUserIdAndWishlistId(userId, wishlistId, wishlist);
	}
	
	/**
	 * 
	 * @param wishlistId
	 * Deletes a wish-list of a specific wishlist ID
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.DELETE)
	public void deleteWishlistByWishlistId(@PathVariable("userId") String userId, @PathVariable("wishlistId") String wishlistId){
		wishlistService.deleteWishlistByUserIdAndWishlistId(userId, wishlistId);
	}
	
	@RequestMapping(value="/wishlists", method = RequestMethod.GET)
	public List<Wishlist> getAllPublicWishlists(){
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> wishlists = wishlistService.getAllPublicWishlists();
		return wishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/source/{source}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndSource(@PathVariable("userId") String userId,
			@PathVariable("source") String source) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSource(userId, source);
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/visibility/{privacy}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacy(@PathVariable("userId") String userId,
			@PathVariable("privacy") String privacy) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacy(userId, privacy);
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/type/{type}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndType(@PathVariable("userId") String userId,
			@PathVariable("type") String type) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndType(userId, type);
		return listOfWishlists;
	}
	
	@RequestMapping(value = "/users/user/{userId}/{source}/{privacy}/{type}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacyAndType(@PathVariable("userId") String userId,
			@PathVariable("source") String source, @PathVariable("privacy") String privacy,
			@PathVariable("type") String type) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacyAndType(userId, source, privacy, type);
		return listOfWishlists;
	}
	
	@RequestMapping(value = "/users/user/{userId}/source/{source}/privacy/{privacy}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacy(@PathVariable("userId") String userId,
			@PathVariable("source") String source, @PathVariable("privacy") String privacy) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy(userId, source, privacy);
		return listOfWishlists;
	}
	
	@RequestMapping(value = "/users/user/{userId}/source/{source}/type/{type}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndType(@PathVariable("userId") String userId,
			@PathVariable("source") String source, 
			@PathVariable("type") String type) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndType(userId, source, type);
		return listOfWishlists;
	}
	
	@RequestMapping(value = "/users/user/{userId}/privacy/{privacy}/type/{type}/wishlists", method = RequestMethod.GET)
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacyAndType(@PathVariable("userId") String userId,
			@PathVariable("privacy") String privacy,
			@PathVariable("type") String type) {
		wishlistService.getAllWishlistsFromCache();
		List<Wishlist> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType(userId, privacy, type);
		return listOfWishlists;
	}

}
