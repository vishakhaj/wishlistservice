package com.wishlistservice.hateoas.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.wishlistservice.controller.ItemController;
import com.wishlistservice.controller.WishlistController;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.hateoas.resource.WishlistResource;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistResourceAssembler extends ResourceAssemblerSupport<WishlistViewBean, WishlistResource> {

	@Autowired
	private HttpSession httpSession;

	public WishlistResourceAssembler() {
		super(WishlistController.class, WishlistResource.class);
	}

	@Override
	public WishlistResource toResource(WishlistViewBean wishlist) {
		WishlistResource wishlistResource = new WishlistResource(wishlist);

		httpSession.setAttribute("wishlistIdSession", wishlist.getWishlistId());
		try {
			// view wishlist by ID
			wishlistResource.add(linkTo(methodOn(WishlistController.class).getWishlistByUserIdAndWishlistId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId(), wishlistResource.getWishlist().getWishlistId()))
							.withSelfRel());
			// update wishlist
			wishlistResource.add(linkTo(methodOn(WishlistController.class).updateWishlistByUserIdAndWishlistId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId(), wishlistResource.getWishlist().getWishlistId(),
					new Wishlist())).withRel("update-this-wishlist"));
			// delete this wishlist
			wishlistResource.add(linkTo(methodOn(WishlistController.class).deleteWishlistByUserIdAndWishlistId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId(), wishlistResource.getWishlist().getWishlistId()))
							.withRel("delete-this-wishlist"));
			// view items
			wishlistResource.add(linkTo(methodOn(ItemController.class).getAllItems()).withRel("view-items"));

			// delete an item from wishlist
			for (Item item : wishlistResource.getWishlist().getItems()) {
				wishlistResource.add(linkTo(methodOn(WishlistController.class).deleteItemFromWishlist(
						wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
						wishlistResource.getWishlist().getUserId(), wishlistResource.getWishlist().getWishlistId(),
						item.getItemId())).withRel("delete-this-item"));
			}

			// delete all items from wishlist
			// wishlistResource.add(linkTo(methodOn(WishlistController.class).deleteAllItemsFromWishlist(
			// wishlistResource.getWishlist().getClient(),
			// wishlistResource.getWishlist().getLocale(),
			// wishlistResource.getWishlist().getUserId(),
			// wishlistResource.getWishlist().getWishlistId()))
			// .withRel("delete-all-items"));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return wishlistResource;
	}

	public List<WishlistResource> toResources(List<WishlistViewBean> wishlists)
			throws IOException, URISyntaxException, ParseException {

		List<WishlistResource> wishlistResources = wishlists.stream().map(WishlistResource::new)
				.collect(Collectors.toList());
		for (WishlistResource wishlistResource : wishlistResources) {

			// view a wishlist
			wishlistResource.add(linkTo(methodOn(WishlistController.class).getWishlistByUserIdAndWishlistId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId(), wishlistResource.getWishlist().getWishlistId()))
							.withSelfRel());
			// create a wishlist
			wishlistResource.add(linkTo(methodOn(WishlistController.class).createWishlistByUserId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId(), new Wishlist())).withRel("add-wishlist"));
			// delete all wishlists
			wishlistResource.add(linkTo(methodOn(WishlistController.class).deleteAllWishlistsByUserId(
					wishlistResource.getWishlist().getClient(), wishlistResource.getWishlist().getLocale(),
					wishlistResource.getWishlist().getUserId())).withRel("delete-all-wishlists"));
		}
		return wishlistResources;
	}

	// return url after creating and deleting wishlist
	public WishlistResource returnUriForAddWishlist() throws IOException, URISyntaxException, ParseException {

		WishlistResource wishlistResource = new WishlistResource();
		// return get all wishlists of a user
		wishlistResource.add(linkTo(methodOn(WishlistController.class).getAllWishlistsByUserId(
				httpSession.getAttribute("clientSession").toString(),
				httpSession.getAttribute("localeSession").toString(),
				httpSession.getAttribute("userIdSession").toString())).withSelfRel());
		
		
		return wishlistResource;
	}

	public WishlistResource returnUriForDeleteWishlist() throws IOException, URISyntaxException, ParseException {

		System.out.println("wishlist id session before: " + httpSession.getAttribute("wishlistIdSession").toString());
		httpSession.removeAttribute("wishlistIdSession");
		
		WishlistResource wishlistResource = new WishlistResource();
		// return get all wishlists of a user
		wishlistResource.add(linkTo(methodOn(WishlistController.class).getAllWishlistsByUserId(
				httpSession.getAttribute("clientSession").toString(),
				httpSession.getAttribute("localeSession").toString(),
				httpSession.getAttribute("userIdSession").toString())).withSelfRel());
		
		
		return wishlistResource;
	}

	
	// return url after updating an existing wishlist
	public WishlistResource returnUriForUpdateWishlist(boolean isUpdated, WishlistViewBean viewBean)
			throws IOException, URISyntaxException, ParseException {

		WishlistResource wishlistResource = new WishlistResource();

		if (isUpdated) {
			// return get all wishlists of a user
			wishlistResource.add(linkTo(methodOn(WishlistController.class).getAllWishlistsByUserId(viewBean.getClient(),
					viewBean.getLocale(), viewBean.getUserId())).withSelfRel());

			// return get a specific wishlist of a user
			wishlistResource.add(
					linkTo(methodOn(WishlistController.class).getWishlistByUserIdAndWishlistId(viewBean.getClient(),
							viewBean.getLocale(), viewBean.getUserId(), viewBean.getWishlistId())).withSelfRel());
			System.out.println("wishlist id session: " + httpSession.getAttribute("wishlistIdSession").toString());
			return wishlistResource;
		}

		return null;
	}

	// return url after items are added or deleted from a wishlist of a user
	public WishlistResource returnUriForItemAddedAndDeletedFromWishlist(String wishlistId, String userId)
			throws IOException, URISyntaxException, ParseException {

		WishlistResource wishlistResource = new WishlistResource();
		System.out.println("clientSession: " + httpSession.getAttribute("clientSession").toString());
		System.out.println("localeSession: " + httpSession.getAttribute("localeSession").toString());

		// get all wishlists of a user
		wishlistResource.add(linkTo(methodOn(WishlistController.class).getAllWishlistsByUserId(
				httpSession.getAttribute("clientSession").toString(),
				httpSession.getAttribute("localeSession").toString(), userId)).withSelfRel());
		// get a specific wishlist of a user
		wishlistResource.add(linkTo(methodOn(WishlistController.class).getWishlistByUserIdAndWishlistId(
				httpSession.getAttribute("clientSession").toString(),
				httpSession.getAttribute("localeSession").toString(), userId, wishlistId)).withSelfRel());

		return wishlistResource;
	}
	
	
}
