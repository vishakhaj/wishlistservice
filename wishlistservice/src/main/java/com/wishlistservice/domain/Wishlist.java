package com.wishlistservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Wishlist {

	@Id
	private String wishlistId;
	
	private String name;

	private String description;

	private String client;

	private String locale;

	public Wishlist() {
	}

	public Wishlist(String wishlistId, String name, String description, String client, String locale) {
		this.wishlistId = wishlistId;
		this.name = name;
		this.description = description;
		this.client = client;
		this.locale = locale;
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

	public String getClient() {
		return client;
	}

	public String getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "Wishlist [wishlistId=" + wishlistId + ", name=" + name + ", description=" + description + "]";
	}

}
