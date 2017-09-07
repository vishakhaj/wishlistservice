package com.wishlistservice.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.viewbean.WishlistViewBean;

@Component
public class WishlistUIMapper {

	public List<WishlistViewBean> createUIViewBeanList(List<Wishlist> wishlists) {
		
		List<WishlistViewBean> list = new ArrayList<>();
		
		for(Wishlist w : wishlists){
			LocalDateTime zonedTimeForCreatedAt = LocalDateTime.ofInstant(w.getCreatedAt().toInstant(), ZoneId.of("Europe/Berlin"));
			LocalDateTime zonedTimeForModifiedOn = LocalDateTime.ofInstant(w.getModifiedOn().toInstant(), ZoneId.of("Europe/Berlin"));
			
			WishlistViewBean wishlistViewBean = new WishlistViewBean(w.getWishlistId(), w.getName(), 
					w.getDescription(), zonedTimeForCreatedAt, w.getClient(), w.getLocale(),
					w.getSource(), w.getType(), w.getPrivacy(), zonedTimeForModifiedOn, w.getUserId(), w.getItems());
			
			list.add(wishlistViewBean);
		}
		
		return list;
//		return wishlists.stream()
//				.map(wishlist -> new WishlistViewBean(wishlist.getWishlistId(), wishlist.getName(), 
//						wishlist.getDescription(), zonedDateTime, wishlist.getClient(), wishlist.getLocale(),
//						wishlist.getSource(), wishlist.getType(), wishlist.getPrivacy(), wishlist.getModifiedOn(), wishlist.getUserId(), wishlist.getItems()))
//				.collect(Collectors.toList());
	}
	
	public WishlistViewBean createUIViewBean(Wishlist wishlist){
		
		LocalDateTime zonedTimeForCreatedAt = LocalDateTime.ofInstant(wishlist.getCreatedAt().toInstant(), ZoneId.of("Europe/Berlin")); 
		LocalDateTime zonedTimeForModifiedOn = LocalDateTime.ofInstant(wishlist.getModifiedOn().toInstant(), ZoneId.of("Europe/Berlin"));
		WishlistViewBean viewBean = new WishlistViewBean();
		
		viewBean.setWishlistId(wishlist.getWishlistId());
		viewBean.setName(wishlist.getName());
		viewBean.setDescription(wishlist.getDescription());
		viewBean.setCreatedAt(zonedTimeForCreatedAt);
		viewBean.setClient(wishlist.getClient());
		viewBean.setLocale(wishlist.getLocale());
		viewBean.setSource(wishlist.getSource());
		viewBean.setType(wishlist.getType());
		viewBean.setPrivacy(wishlist.getPrivacy());
		viewBean.setModifiedOn(zonedTimeForModifiedOn);
		viewBean.setUserId(wishlist.getUserId());
		viewBean.setItems(wishlist.getItems());
		return viewBean;
	}
	
}
	
	

