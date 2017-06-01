package com.wishlistservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.viewbean.WishlistViewBean;

@RunWith(MockitoJUnitRunner.class)
public class WishlistUIMapperTest {

	@InjectMocks
	private WishlistUIMapper classUnderTest = new WishlistUIMapper();
	
	
	@Test
	public void createUIViewBean_WishlistsNotFound_EmtpyListReturned() {
		List<WishlistViewBean> viewBean = classUnderTest.createUIViewBean(emptyWishlist());
		assertEquals(0, viewBean.size());
		
	}
	
	private List<Wishlist> emptyWishlist() {
		return new ArrayList<Wishlist>();
	}

	@Test
	public void createUIViewBean_WishlistsFound_ViewBeanReturned(){
		classUnderTest.createUIViewBean(wishlists());
		WishlistViewBean viewBean = new WishlistViewBean();
		
		for(Wishlist wishlist : wishlists()){
			viewBean.setName(wishlist.getName());
			viewBean.setDescription(wishlist.getDescription());
		}
		
		assertNotNull(viewBean);
		assertEquals("wishlist a", viewBean.getName() );
		assertEquals("description a", viewBean.getDescription());
	}

	private List<Wishlist> wishlists() {
		List<Wishlist> list = new ArrayList<>();
		Wishlist wishlist1 = new Wishlist("1", "wishlist a", "description a", "MediaDE", "de");
		list.add(wishlist1);
		return list;
	}
}

