package com.wishlistservice.viewbean;

public class WishlistViewBean {

	private String wishlistId;

	private String name;

	private String description;

	public WishlistViewBean(String wishlistId, String name, String description) {
		this.wishlistId = wishlistId;
		this.name = name;
		this.description = description;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "WishlistViewBean [wishlistId=" + wishlistId + ", name=" + name + ", description=" + description + "]";
	}

}
