//package com.wishlistservice;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.wishlistservice.common.Privacy;
//import com.wishlistservice.common.Source;
//import com.wishlistservice.common.Type;
//import com.wishlistservice.domain.Wishlist;
//import com.wishlistservice.mapper.WishlistUIMapper;
//import com.wishlistservice.viewbean.WishlistViewBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WishlistUIMapperTest {
//
//	@InjectMocks
//	private WishlistUIMapper classUnderTest = new WishlistUIMapper();
//	
//	
//	@Test
//	public void createUIViewBean_WishlistsFound_ReturnViewBean(){
//		classUnderTest.createUIViewBeanList(wishlists());
//		WishlistViewBean viewBean = new WishlistViewBean();
//		
//		for(Wishlist wishlist : wishlists()){
//			viewBean.setName(wishlist.getName());
//			viewBean.setDescription(wishlist.getDescription());
//		}
//		
//		assertNotNull(viewBean);
//		assertEquals("wishlist a", viewBean.getName() );
//		assertEquals("description a", viewBean.getDescription());
//	}
//	
//	@Test
//	public void createUIViewBean_WishlistFound_ReturnViewBean(){
//		WishlistViewBean wishlist = classUnderTest.createUIViewBean(wishlist());
//		assertNotNull(wishlist);
//		assertEquals("wishlist a", wishlist.getName());
//	}
//
//	private Wishlist wishlist() {
//		Wishlist wishlist = new Wishlist("wishlist a", "description a", "MediaDE", "de", new Date(), Source.ONLINE, Type.DEFAULT, Privacy.PUBLIC, new Date(), "1" );
//		return wishlist;
//	}
//
//	private List<Wishlist> wishlists() {
//		List<Wishlist> list = new ArrayList<>();
//		Wishlist wishlist = new Wishlist("wishlist a", "description a", "MediaDE", "de", new Date(), Source.ONLINE, Type.DEFAULT, Privacy.PUBLIC, new Date(), "1" );
//		list.add(wishlist);
//		return list;
//	}
//}
//
