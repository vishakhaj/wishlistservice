//package com.wishlistservice;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.wishlistservice.common.Privacy;
//import com.wishlistservice.common.Source;
//import com.wishlistservice.common.Type;
//import com.wishlistservice.domain.Wishlist;
//import com.wishlistservice.mapper.WishlistUIMapper;
//import com.wishlistservice.repository.CustomWishlistRepository;
//import com.wishlistservice.service.WishlistService;
//import com.wishlistservice.viewbean.WishlistViewBean;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WishlistServiceTest {
//
//	@InjectMocks
//	private WishlistService wishlistService;
//
//	@Mock
//	private CustomWishlistRepository wishlistRepository;
//
//	@Mock
//	private WishlistUIMapper wishlistUIMapper;
//
//	@Test
//	public void getAllWishlistsByUserId_UserIdNull_EmptyList() {
//		List<WishlistViewBean> wishlistByUserId = wishlistService.getAllWishlistsByUserId(null);
//		assertNotNull(wishlistByUserId);
//	}
//
//	@Test
//	public void getAllWishlistsByUserId_UserIdNotNull_EmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserId(Mockito.any())).thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserId = wishlistService.getAllWishlistsByUserId("1");
//		assertNotNull(wishlistsByUserId);
//		assertTrue(wishlistsByUserId.isEmpty());
//	}
//
//	@Test
//	public void getAllWishlistsByUserId_UserIdNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserId(Mockito.any())).thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserId = wishlistService.getAllWishlistsByUserId("1");
//		assertNotNull(wishlistsByUserId);
//		for (WishlistViewBean wishlist : wishlistsByUserId) {
//			assertSame("name 1", wishlist.getName());
//		}
//		// assertFalse(wishlistsByUserId.isEmpty());
//	}
//
//	@Test
//	public void getWishlistByUserIdAndWishlistId_UserIdAndWishlistIdNull_ReturnEmptyList() {
//		WishlistViewBean wishlistByUserIdAndWishlistId = wishlistService.getWishlistByUserIdAndWishlistId(null, null);
//		assertNotNull(wishlistByUserIdAndWishlistId);
//	}
//
//	@Test
//	public void getWishlistByUserIdAndWishlistId_UserIdAndWishlistIdNotNull_EmptyList() {
//		Mockito.when(wishlistRepository.findWishlistByUserIdAndWishlistId(Mockito.any())).thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBean(Mockito.any())).thenReturn(new WishlistViewBean());
//		WishlistViewBean wishlistByUserIdAndWishlistId = wishlistService.getWishlistByUserIdAndWishlistId("1", "1");
//		assertNotNull(wishlistByUserIdAndWishlistId);
//	}
//
//	@Test
//	public void getWishlistByUserIdAndWishlistId_UserIdAndWishlistIdNotNull_MappedViewBean() {
//		Mockito.when(wishlistRepository.findWishlistByUserIdAndWishlistId(Mockito.any()))
//				.thenReturn(Optional.of(wishlist()));
//		Mockito.when(wishlistUIMapper.createUIViewBean(Mockito.any())).thenReturn(viewBean());
//		WishlistViewBean wishlistByUserIdAndWishlistId = wishlistService.getWishlistByUserIdAndWishlistId("1", "1");
//		assertSame("name 1", wishlistByUserIdAndWishlistId.getName());
//		// assertEquals("1", wishlistByUserIdAndWishlistId.getUserId());
//		assertNotSame("2", wishlistByUserIdAndWishlistId.getUserId());
//		assertSame("PRIVATE", wishlistByUserIdAndWishlistId.getPrivacy().toString());
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSource_UserIdAndSourceNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSource(null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSource_UserIdAndSourceNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSource(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserIdAndSource = wishlistService.getAllWishlistsByUserIdAndSource("1",
//				"ONLINE");
//		assertNotNull(wishlistsByUserIdAndSource);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSource_UserIdNotNullAndSourceIsNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSource("1", null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSource_UserIdAndSourceNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSource(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserIdAndSource = wishlistService.getAllWishlistsByUserIdAndSource("1",
//				"ONLINE");
//		for (WishlistViewBean wishlist : wishlistsByUserIdAndSource) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacy_UserIdAndPrivacyNull_ReturnEmptyList() {
//		List<WishlistViewBean> allWishlistsByUserIdAndPrivacy = wishlistService.getAllWishlistsByUserIdAndPrivacy(null,
//				null);
//		assertNotNull(allWishlistsByUserIdAndPrivacy);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacy_UserIdNotNullAndPrivacyNull_ReturnEmptyList() {
//		List<WishlistViewBean> allWishlistsByUserIdAndPrivacy = wishlistService.getAllWishlistsByUserIdAndPrivacy("1",
//				null);
//		assertNotNull(allWishlistsByUserIdAndPrivacy);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacy_UserIdAndPrivacyNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndPrivacy(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> allWishlistsByUserIdAndPrivacy = wishlistService.getAllWishlistsByUserIdAndPrivacy("1",
//				"PRIVATE");
//		assertNotNull(allWishlistsByUserIdAndPrivacy);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacy_UserIdAndPrivacyNotNull_MappedViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndPrivacy(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> allWishlistsByUserIdAndPrivacy = wishlistService.getAllWishlistsByUserIdAndPrivacy("1",
//				"PUBLIC");
//		for (WishlistViewBean wishlist : allWishlistsByUserIdAndPrivacy) {
//			assertSame("name 1", wishlist.getName());
//		}
//
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndType_UserIdAndTypeNull_ReturnEmptyList() {
//		List<WishlistViewBean> allWishlistsByUserIdAndType = wishlistService.getAllWishlistsByUserIdAndType(null,
//				null);
//		assertNotNull(allWishlistsByUserIdAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndType_UserIdNotNullAndTypeNull_ReturnEmptyList() {
//		List<WishlistViewBean> allWishlistsByUserIdAndType = wishlistService.getAllWishlistsByUserIdAndType("1",
//				null);
//		assertNotNull(allWishlistsByUserIdAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndType_UserIdAndTypeNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndType(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> allWishlistsByUserIdAndType = wishlistService.getAllWishlistsByUserIdAndType("1",
//				"DEFAULT");
//		assertNotNull(allWishlistsByUserIdAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndType_UserIdAndTypeNotNull_MappedViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndType(Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> allWishlistsByUserIdAndType = wishlistService.getAllWishlistsByUserIdAndType("1",
//				"DEFAULT");
//		for (WishlistViewBean wishlist : allWishlistsByUserIdAndType) {
//			assertSame("name 1", wishlist.getName());
//		}
//
//	}
//	
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacyAndType_UserIdAndSourceAndPrivacyAndTypeNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacyAndType(null, null, null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacyAndType_UserIdAndSourceAndPrivacyAndTypeNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacyAndType(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndPrivacyAndType = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacyAndType("1",
//				"ONLINE", "PUBLIC", "DEFAULT");
//		assertNotNull(wishlistsByUserIdAndSourceAndPrivacyAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacyAndType_UserIdNotNullAndSourceAndPrivacyAndTypeIsNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacyAndType("1", null, null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacyAndType_UserIdAndSourceAndPrivacyAndTypeNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacyAndType(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndPrivacyAndType = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacyAndType("1",
//				"ONLINE", "PUBLIC", "DEFAULT");
//		for (WishlistViewBean wishlist : wishlistsByUserIdAndSourceAndPrivacyAndType) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//	
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacy_UserIdAndSourceAndPrivacyNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy(null, null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacy_UserIdAndSourceAndPrivacyNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacy(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndPrivacy = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy("1",
//				"ONLINE", "PUBLIC");
//		assertNotNull(wishlistsByUserIdAndSourceAndPrivacy);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacy_UserIdNotNullAndSourceAndPrivacyIsNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy("1", null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndPrivacy_UserIdAndSourceAndPrivacyNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndPrivacy(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndPrivacy = wishlistService.getAllWishlistsByUserIdAndSourceAndPrivacy("1",
//				"ONLINE", "PUBLIC");
//		for (WishlistViewBean wishlist : wishlistsByUserIdAndSourceAndPrivacy) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//	
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndType_UserIdAndSourceAndTypeNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndType(null, null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndType_UserIdAndSourceAndTypeNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndType(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndType = wishlistService.getAllWishlistsByUserIdAndSourceAndType("1",
//				"ONLINE", "DEFAULT");
//		assertNotNull(wishlistsByUserIdAndSourceAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndType_UserIdNotNullAndSourceAndTypeIsNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndSourceAndType("1", null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndSourceAndType_UserIdAndSourceAndTypeNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndSourceAndType(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserIdAndSourceAndType = wishlistService.getAllWishlistsByUserIdAndSourceAndType("1",
//				"ONLINE", "DEFAULT");
//		for (WishlistViewBean wishlist : wishlistsByUserIdAndSourceAndType) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//	
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacyAndType_UserIdAndPrivacyAndTypeNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType(null, null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacyAndType_UserIdAAndPrivacyAndTypeNotNull_ReturnEmptyList() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndPrivacyAndType(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any()))
//				.thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> wishlistsByUserIdAndPrivacyAndType = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType("1",
//				"ONLINE", "DEFAULT");
//		assertNotNull(wishlistsByUserIdAndPrivacyAndType);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacyAndType_UserIdNotNullAndPrivacyAndTypeIsNull_ReturnEmptyList() {
//		List<WishlistViewBean> wishlists = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType("1", null, null);
//		assertNotNull(wishlists);
//	}
//
//	@Test
//	public void getAllWishlistsByUserIdAndPrivacyAndType_UserIdAndPrivacyAndTypeNotNull_MappedToViewBean() {
//		Mockito.when(wishlistRepository.findAllWishlistsByUserIdAndPrivacyAndType(Mockito.any(), Mockito.any(), Mockito.any()))
//				.thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> wishlistsByUserIdAndPrivacyAndType = wishlistService.getAllWishlistsByUserIdAndPrivacyAndType("1",
//				"ONLINE", "DEFAULT");
//		for (WishlistViewBean wishlist : wishlistsByUserIdAndPrivacyAndType) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//
//	@Test
//	public void getAllPublicWishlists_NoPublicWishlistsPresent_ReturnEmptyList(){
//		Mockito.when(wishlistRepository.findAllPublicWishlists()).thenReturn(Optional.empty());
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> allPublicWishlists = wishlistService.getAllPublicWishlists();
//		assertNotNull(allPublicWishlists);
//	}
//	
//	@Test
//	public void getAllPublicWishlists_PublicWishlistsPresent_MappedToViewBean(){
//		Mockito.when(wishlistRepository.findAllPublicWishlists()).thenReturn(Optional.of(wishlists()));
//		Mockito.when(wishlistUIMapper.createUIViewBeanList(Mockito.any())).thenReturn(wishlistViewBean());
//		List<WishlistViewBean> allPublicWishlists = wishlistService.getAllPublicWishlists();
//		for (WishlistViewBean wishlist : allPublicWishlists) {
//			assertSame("name 1", wishlist.getName());
//		}
//	}
//	
//	private List<WishlistViewBean> wishlistViewBean() {
//		List<WishlistViewBean> listOfWishlists = new ArrayList<>();
//		WishlistViewBean wishlistViewBean = new WishlistViewBean();
//		wishlistViewBean.setName("name 1");
//		wishlistViewBean.setDescription("desc 1");
//		wishlistViewBean.setClient("MediaDE");
//		listOfWishlists.add(wishlistViewBean);
//		return listOfWishlists;
//	}
//
//	private List<Wishlist> wishlists() {
//		List<Wishlist> listOfWishlists = new ArrayList<>();
//		Wishlist wishlist = new Wishlist("name 1", "desc 1", "MediaDE", "de", new Date(), Source.ONLINE, Type.DEFAULT,
//				Privacy.PRIVATE, new Date(), "1");
//		listOfWishlists.add(wishlist);
//		return listOfWishlists;
//	}
//
//	private Wishlist wishlist() {
//		Wishlist wishlist = new Wishlist("name 1", "desc 1", "MediaDE", "de", new Date(), Source.ONLINE, Type.DEFAULT,
//				Privacy.PRIVATE, new Date(), "1");
//		return wishlist;
//	}
//
//	private WishlistViewBean viewBean() {
//		WishlistViewBean wishlistViewBean = new WishlistViewBean();
//		wishlistViewBean.setName("name 1");
//		wishlistViewBean.setDescription("desc 1");
//		wishlistViewBean.setClient("MediaDE");
//		wishlistViewBean.setUserId("1");
//		wishlistViewBean.setPrivacy(Privacy.PRIVATE);
//		return wishlistViewBean;
//	}
//}
