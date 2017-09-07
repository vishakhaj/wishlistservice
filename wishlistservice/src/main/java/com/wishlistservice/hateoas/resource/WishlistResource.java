package com.wishlistservice.hateoas.resource;

import org.springframework.hateoas.ResourceSupport;

import com.wishlistservice.viewbean.WishlistViewBean;

public class WishlistResource extends ResourceSupport {

	private WishlistViewBean wishlist;

	private boolean itemAddedToWishlist;
	
	public WishlistResource() {
	}
	
	public WishlistResource(WishlistViewBean wishlist) {
		this.wishlist = wishlist;
	}

	public WishlistViewBean getWishlist() {
		return wishlist;
	}

	public void setWishlist(WishlistViewBean wishlist) {
		this.wishlist = wishlist;
	}

	public boolean isItemAddedToWishlist() {
		return itemAddedToWishlist;
	}

	public void setItemAddedToWishlist(boolean itemAddedToWishlist) {
		this.itemAddedToWishlist = itemAddedToWishlist;
	}

}
