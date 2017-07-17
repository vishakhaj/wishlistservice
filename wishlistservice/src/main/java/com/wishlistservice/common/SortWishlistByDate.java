package com.wishlistservice.common;

import java.util.Comparator;

import com.wishlistservice.domain.Wishlist;

public class SortWishlistByDate implements Comparator<Wishlist> {

	@Override
	public int compare(Wishlist o1, Wishlist o2) {
		return o1.getCreatedAt().compareTo(o2.getCreatedAt());
	}

}
