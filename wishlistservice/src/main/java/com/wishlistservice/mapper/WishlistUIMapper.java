package com.wishlistservice.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistUIMapper {

	public List<WishlistViewBean> createUIViewBeanList(List<Wishlist> wishlists) {
		
		if(wishlists.isEmpty()){
			return Collections.emptyList();
		}
		
		return wishlists.stream()
				.map(wishlist -> new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(), 
						wishlist.getDescription(), wishlist.getCreatedAt(), wishlist.getClient(), wishlist.getSource(),
						wishlist.getType(), wishlist.getPrivacy(), wishlist.getModifiedOn(), wishlist.getUserId()))
				.collect(Collectors.toList());
	}
	
	public WishlistViewBean createUIViewBean(Wishlist wishlist){
		WishlistViewBean viewBean = new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(), wishlist.getDescription(),
				wishlist.getCreatedAt(), wishlist.getClient(), wishlist.getSource(), 
				wishlist.getType(), wishlist.getPrivacy(), wishlist.getModifiedOn(), wishlist.getUserId());
		return viewBean;
	}
	
}
	
	

