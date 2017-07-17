package com.wishlistservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistUIMapper {

	public List<WishlistViewBean> createUIViewBeanList(List<Wishlist> wishlists) {
		
		return wishlists.stream()
				.map(wishlist -> new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(), 
						wishlist.getDescription(), wishlist.getCreatedAt(), wishlist.getClient(), wishlist.getSource(),
						wishlist.getType(), wishlist.getPrivacy(), wishlist.getModifiedOn(), wishlist.getUserId(), wishlist.getItems()))
				.collect(Collectors.toList());
	}
	
	public WishlistViewBean createUIViewBean(Wishlist wishlist){
		WishlistViewBean viewBean = new WishlistViewBean();
		
		viewBean.setWishlistId(wishlist.getWishlistId());
		viewBean.setName(wishlist.getName());
		viewBean.setDescription(wishlist.getDescription());
		viewBean.setCreatedAt(wishlist.getCreatedAt());
		viewBean.setClient(wishlist.getClient());
		viewBean.setSource(wishlist.getSource());
		viewBean.setType(wishlist.getType());
		viewBean.setPrivacy(wishlist.getPrivacy());
		viewBean.setModifiedOn(wishlist.getModifiedOn());
		viewBean.setUserId(wishlist.getUserId());
		viewBean.setItems(wishlist.getItems());
		return viewBean;
	}
}
	
	

