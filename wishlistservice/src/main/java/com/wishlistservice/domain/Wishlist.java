package com.wishlistservice.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wishlistservice.common.Privacy;
import com.wishlistservice.common.Source;
import com.wishlistservice.common.Type;

@Document
public class Wishlist {

	@Id
	private String wishlistId;

	private String name;

	private String description;

	private String client;

	private String locale;

	private Date createdAt;

	private Source source;

	private Type type;

	private Privacy privacy;

	private Date modifiedOn;

	private String userId;

	public Wishlist() {
	}

	public Wishlist(String name, String description, String client, String locale, Date createdAt, Source source,
			Type type, Privacy privacy, Date modifiedOn, String userId) {
		this.name = name;
		this.description = description;
		this.client = client;
		this.locale = locale;
		this.createdAt = createdAt;
		this.source = source;
		this.type = type;
		this.privacy = privacy;
		this.modifiedOn = modifiedOn;
		this.userId = userId;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public Source getSource() {
		return source;
	}

	public Type getType() {
		return type;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "Wishlist [wishlistId=" + wishlistId + ", name=" + name + ", description=" + description + "]";
	}

}
