package com.wishlistservice.viewbean;

import java.util.Date;

import com.wishlistservice.common.Privacy;
import com.wishlistservice.common.Source;
import com.wishlistservice.common.Type;

public class WishlistViewBean {

	private String wishlistId;

	private String name;

	private String description;

	private Date createdAt;

	private String client;

	private Source source;

	private Type type;

	private Privacy privacy;

	private Date modifiedOn;

	private String userId;

	public WishlistViewBean() {
	}

	public WishlistViewBean(String wishlistId, String name, String description, Date createdAt, String client,
			Source source, Type type, Privacy privacy, Date modifiedOn, String userId) {
		this.wishlistId = wishlistId;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.client = client;
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

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getClient() {
		return client;
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
		return "WishlistViewBean [wishlistId=" + wishlistId + ", name=" + name + ", description=" + description + "]";
	}

}
