package com.wishlistservice.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.service.WishlistService;
import com.wishlistservice.viewbean.WishlistViewBean;

@RestController
@RequestMapping(value = "/{client}/{locale}/api")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

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
			@PathVariable("userId") String userId, @Valid @RequestBody Wishlist wishlist,
			HttpServletResponse response) {
		wishlistService.createWishlist(client, locale, userId, wishlist);
		System.out.println("Wishlist saved..");
		response.setStatus(HttpStatus.CREATED.value());
	}

	/**
	 * 
	 * @param userId
	 * @param wishlistId
	 * @param wishlist
	 *            Updates a wish-list by wishlist ID belonging to a specific
	 *            user ID.
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.PUT)
	public void updateWishlistByUserIdAndWishlistId(@PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, @RequestBody Wishlist wishlist,
			HttpServletResponse response) {
		wishlistService.updateWishlistByUserIdAndWishlistId(userId, wishlistId, wishlist);
		response.setStatus(HttpStatus.OK.value());
	}

	/**
	 * 
	 * @param wishlistId
	 *            Deletes a wish-list by wish-list ID of a specific user ID.
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.DELETE)
	public void deleteWishlistByUserIdAndWishlistId(@PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, HttpServletResponse response) {
		wishlistService.deleteWishlistByUserIdAndWishlistId(userId, wishlistId);
		response.setStatus(HttpStatus.OK.value());
	}

	/**
	 * @param userId
	 *            Deletes all wish-lists belonging to a user ID.
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", method = RequestMethod.DELETE)
	public void deleteAllWishlistsByUserId(@PathVariable("userId") String userId, HttpServletResponse response) {
		wishlistService.deleteAllWishlistsByUserId(userId);
		response.setStatus(HttpStatus.OK.value());
	}

	/**
	 * @return All Wish-lists of a specific user ID.
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserId(@PathVariable("userId") String userId,
			HttpServletResponse response) throws IOException, ParseException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserId(userId);
		if (wishlists == null || wishlists.isEmpty()) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Wishlists for the specified user is Not Found");
		}
		response.setStatus(HttpStatus.OK.value());
		return wishlists;
	}

	/**
	 * @param wishlistId
	 *            - Takes the unique wish-list ID
	 * @return Wish-list of a specific wish-list ID
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.GET)
	public WishlistViewBean getWishlistByUserIdAndWishlistId(@PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, HttpServletResponse response) throws IOException {
		WishlistViewBean wishlist = wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId);
		if (wishlist == null) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Specific wishlist for the user is not found");
		}
		response.setStatus(HttpStatus.OK.value());
		return wishlist;
	}

	/**
	 * @return all wish-lists that are PUBLIC
	 * @throws IOException
	 */
	@RequestMapping(value = "/wishlists", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllPublicWishlists(@RequestParam("privacy") String privacy,
			HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> wishlists = wishlistService.getAllPublicWishlists(privacy);
		if (wishlists == null || wishlists.isEmpty()) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Wishlists for public not found");
		}
		response.setStatus(HttpStatus.OK.value());
		return wishlists;
	}

	/**
	 * @param userId
	 * @param source
	 * @return all wish-lists by SOURCE of a specific user ID
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "source", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSource(@PathVariable("userId") String userId,
			@RequestParam("source") String source, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSource(userId, source);
		if (listOfWishlists == null) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}else if(listOfWishlists.isEmpty()){
			response.sendError(HttpStatus.NOT_FOUND.value(), "Wishlist is null or empty");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param privacy
	 * @return all wish-lists by PRIVACY of a specific user ID.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "privacy", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacy(@PathVariable("userId") String userId,
			@RequestParam("privacy") String privacy, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacy(userId, privacy);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param type
	 * @return all wish-lists by TYPE of a specific user ID.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "type", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndType(@PathVariable("userId") String userId,
			@RequestParam("type") String type, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndType(userId, type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param source
	 * @param privacy
	 * @param type
	 * @return all wish-lists by SOURCE, TYPE and PRIVACY of a specific user ID.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "source", "type",
			"privacy" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndPrivacyAndType(
			@PathVariable("userId") String userId, @RequestParam("source") String source,
			@RequestParam("privacy") String privacy, @RequestParam("type") String type, HttpServletResponse response)
			throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService
				.getAllWishlistsByUserIdAndSourceAndPrivacyAndType(userId, source, privacy, type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param source
	 * @param privacy
	 * @return all wish-lists by SOURCE and PRIVACY of a specific user ID.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "source",
			"privacy" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndPrivacy(@PathVariable("userId") String userId,
			@RequestParam("source") String source, @RequestParam("privacy") String privacy,
			HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy(userId,
				source, privacy);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param source
	 * @param type
	 * @return all wish-lists by SOURCE AND TYPE of a specific user ID
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "source", "type" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndType(@PathVariable("userId") String userId,
			@RequestParam("source") String source, @RequestParam("type") String type, HttpServletResponse response)
			throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndType(userId, source,
				type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	/**
	 * @param userId
	 * @param privacy
	 * @param type
	 * @return all wish-lists by PRIVACY and TYPE of a specific user ID.
	 * @throws IOException
	 */
	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "privacy",
			"type" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacyAndType(@PathVariable("userId") String userId,
			@RequestParam("privacy") String privacy, @RequestParam("type") String type, HttpServletResponse response)
			throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType(userId,
				privacy, type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "sortOrder", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsbyUserIdAndSortOrder(@PathVariable("userId") String userId,
			@RequestParam("sortOrder") String sortOrder, HttpServletResponse response) throws IOException {

		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSortOrder(userId, sortOrder);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(), "Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

}
