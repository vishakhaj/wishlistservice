package com.wishlistservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

	private String itemId;

	private String itemName;

	private String brandName;

	private Double rating;

	private String client;

	private String locale;
	
	private String userId;
	
	private String wishlistId;

	public Item() {
	}

	public Item(String itemId, String itemName, String brandName, Double rating, String client, String locale) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.brandName = brandName;
		this.rating = rating;
		this.client = client;
		this.locale = locale;
	}
	
	public Item(String userId, String wishlistId, String itemId){
		this.userId = userId;
		this.wishlistId = wishlistId;
		this.itemId = itemId;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getBrandName() {
		return brandName;
	}

	public Double getRating() {
		return rating;
	}

	public String getClient() {
		return client;
	}

	public String getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", brandName=" + brandName + ", rating=" + rating
				+ ", client=" + client + ", locale=" + locale + "]";
	}

}
