package com.wishlistservice.hateoas.resource;

import org.springframework.hateoas.ResourceSupport;

import com.wishlistservice.domain.Item;

public class ItemResource extends ResourceSupport {

	private Item item;

	public ItemResource() {
	}

	public ItemResource(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
