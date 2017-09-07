package com.wishlistservice.hateoas.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.wishlistservice.controller.ItemController;
import com.wishlistservice.controller.WishlistController;
import com.wishlistservice.domain.Item;
import com.wishlistservice.hateoas.resource.ItemResource;

@Component
public class ItemResourceAssembler extends ResourceAssemblerSupport<Item, ItemResource> {

	@Autowired
	private HttpSession httpSession;
	
	public ItemResourceAssembler() {
		super(ItemController.class, ItemResource.class);
	}

	@Override
	public ItemResource toResource(Item item) {
		ItemResource itemResource = new ItemResource(item);

		try {
			// view item by ID
			itemResource.add(linkTo(methodOn(ItemController.class).getItemById(itemResource.getItem().getItemId()))
					.withSelfRel());
			// add item to wishlist by ID
			itemResource.add(linkTo(methodOn(WishlistController.class).updateItemToWishlist(itemResource.getItem().getClient(),
					itemResource.getItem().getLocale(), httpSession.getAttribute("userIdSession").toString(),
					httpSession.getAttribute("wishlistIdSession").toString(), itemResource.getItem().getItemId()))
							.withRel("add-item-to-this-wishlist"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return itemResource;
	}

}
