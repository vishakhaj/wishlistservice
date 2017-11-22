 
package com.wishlistservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wishlistservice.common.ApiError;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.hateoas.controller.assembler.WishlistResourceAssembler;
import com.wishlistservice.hateoas.resource.WishlistResource;
import com.wishlistservice.service.ItemService;
import com.wishlistservice.service.WishlistService;
import com.wishlistservice.service.Wishlist_Item_Service;
import com.wishlistservice.viewbean.WishlistViewBean;

@RestController
@RequestMapping(value = "/api")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private Wishlist_Item_Service wishlist_item_Service;

	@Autowired
	private ItemService itemService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private WishlistResourceAssembler wishlistResourceAssembler;

	// Write Queries

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists", method = RequestMethod.POST)
	public ResponseEntity<WishlistResource> createWishlistByUserId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@Valid @RequestBody Wishlist wishlist) throws IOException, ParseException, URISyntaxException {

		wishlistService.createWishlist(client, locale, userId, wishlist);
		System.out.println("Wishlist saved..");

		httpSession.setAttribute("userIdSession", userId);
		httpSession.setAttribute("clientSession", client);
		httpSession.setAttribute("localeSession", locale);

		WishlistResource resource = wishlistResourceAssembler.returnUriForAddWishlist();
		return new ResponseEntity<>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.PUT)
	public ResponseEntity<WishlistResource> updateWishlistByUserIdAndWishlistId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, @RequestBody Wishlist wishlist)
			throws IOException, URISyntaxException, ParseException {

		wishlistService.updateWishlistByUserIdAndWishlistId(userId, wishlistId, wishlist);

		wishlistService.getAllWishlistsFromCache();
		WishlistViewBean w = wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId);
		WishlistResource wishlistResource = new WishlistResource();
		wishlistResource.setItemAddedToWishlist(true);
		WishlistResource resource = wishlistResourceAssembler
				.returnUriForUpdateWishlist(wishlistResource.isItemAddedToWishlist(), w);

		return new ResponseEntity<WishlistResource>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteWishlistByUserIdAndWishlistId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId) throws IOException, ParseException, URISyntaxException {

		wishlistService.deleteWishlistByUserIdAndWishlistId(userId, wishlistId);
		wishlistService.getAllWishlistsFromCache();
		
		WishlistResource resource = wishlistResourceAssembler.returnUriForDeleteWishlist();

		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllWishlistsByUserId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId) throws IOException, URISyntaxException, ParseException {

		wishlistService.deleteAllWishlistsByUserId(userId);
		wishlistService.getAllWishlistsFromCache();

		ApiError apiError = new ApiError(HttpStatus.OK, "All Wishlists deleted");
		return new ResponseEntity<>(apiError, apiError.getStatus());
		
	}

	// Read Queries

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists", method = RequestMethod.GET)
	public ResponseEntity<?> getAllWishlistsByUserId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId)
			throws IOException, ParseException, URISyntaxException {

		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserId(userId);
		List<WishlistResource> wishlistResources = wishlistResourceAssembler.toResources(wishlists);
		
		if (wishlists == null || wishlists.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "You have no wishlists");
			return new ResponseEntity<>(apiError, apiError.getStatus());
		}

		return new ResponseEntity<List<WishlistResource>>(wishlistResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists/wishlist/{wishlistId}", method = RequestMethod.GET)
	public ResponseEntity<?> getWishlistByUserIdAndWishlistId(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId) throws IOException, URISyntaxException, ParseException {

		httpSession.setAttribute("userIdSession", userId);
		httpSession.setAttribute("clientSession", client);
		httpSession.setAttribute("localeSession", locale);

		wishlistService.getAllWishlistsFromCache();
		WishlistViewBean wishlist = wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId);
		System.out.println("wishlist: " + wishlist);
		
		if(wishlist.getWishlistId() == null){
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "You cannot access this wishlist.");
			return new ResponseEntity<>(apiError, apiError.getStatus());
		}
		
		WishlistResource wishlistResource = wishlistResourceAssembler.toResource(wishlist);
		return new ResponseEntity<WishlistResource>(wishlistResource, HttpStatus.OK);
	}

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

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "source", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSource(@PathVariable("userId") String userId,
			@RequestParam("source") String source, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSource(userId, source);
		if (listOfWishlists == null) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		} else if (listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Wishlist is null or empty");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "privacy", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacy(@PathVariable("userId") String userId,
			@RequestParam("privacy") String privacy, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacy(userId, privacy);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = "type", method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndType(@PathVariable("userId") String userId,
			@RequestParam("type") String type, HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndType(userId, type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

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
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "source",
			"privacy" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndPrivacy(@PathVariable("userId") String userId,
			@RequestParam("source") String source, @RequestParam("privacy") String privacy,
			HttpServletResponse response) throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy(userId,
				source, privacy);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "source", "type" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndSourceAndType(@PathVariable("userId") String userId,
			@RequestParam("source") String source, @RequestParam("type") String type, HttpServletResponse response)
			throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndType(userId, source,
				type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/users/user/{userId}/wishlists", params = { "privacy",
			"type" }, method = RequestMethod.GET)
	public List<WishlistViewBean> getAllWishlistsByUserIdAndPrivacyAndType(@PathVariable("userId") String userId,
			@RequestParam("privacy") String privacy, @RequestParam("type") String type, HttpServletResponse response)
			throws IOException {
		wishlistService.getAllWishlistsFromCache();
		List<WishlistViewBean> listOfWishlists = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType(userId,
				privacy, type);
		if (listOfWishlists == null || listOfWishlists.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value(),
					"Required value for the parameter is empty or not correct.");
		}
		response.setStatus(HttpStatus.OK.value());
		return listOfWishlists;
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists/wishlist/{wishlistId}/items/item/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateItemToWishlist(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, @PathVariable("itemId") String itemId)
			throws URISyntaxException, IOException, ParseException {

		itemService.getCachedItems();
		List<Item> items = itemService.getAllItems();
		wishlist_item_Service.addItemToWishlist(userId, wishlistId, itemId);
		WishlistViewBean wishlist = wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId);
		WishlistResource resource = wishlistResourceAssembler.returnUriForItemAddedAndDeletedFromWishlist(wishlistId,
				userId);

		for (Item item : wishlist.getItems()) {
			if (Objects.equals(item.getItemId(), itemId)) {
				ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Item already exists", resource);
				return new ResponseEntity<>(apiError, apiError.getStatus());
			}
		}

		for (Item item : items) {
			if (item.getItemId().equals(itemId)) {
				return new ResponseEntity<WishlistResource>(resource, HttpStatus.CREATED);
			}
		}
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Item not found", resource);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@RequestMapping(value = "/{client}/{locale}/users/user/{userId}/wishlists/wishlist/{wishlistId}/items/item/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItemFromWishlist(@PathVariable("client") String client,
			@PathVariable("locale") String locale, @PathVariable("userId") String userId,
			@PathVariable("wishlistId") String wishlistId, @PathVariable("itemId") String itemId)
			throws URISyntaxException, IOException, ParseException {

		WishlistViewBean wishlist = wishlistService.getWishlistByUserIdAndWishlistId(userId, wishlistId);
		WishlistResource resource = wishlistResourceAssembler.returnUriForItemAddedAndDeletedFromWishlist(wishlistId,
				userId);

		for (Item item : wishlist.getItems()) {
			if (item.getItemId().equals(itemId)) {
				wishlist_item_Service.deleteItemFromWishlist(userId, wishlistId, itemId);
				return new ResponseEntity<>(resource, HttpStatus.OK);
			}
		}

		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Item does not exist", resource);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
