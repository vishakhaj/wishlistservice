package com.wishlistservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.mapper.WishlistUIMapper;
import com.wishlistservice.repository.CustomWishlistRepository;
import com.wishlistservice.service.WishlistService;
import com.wishlistservice.viewbean.WishlistViewBean;

@RunWith(MockitoJUnitRunner.class)
public class WishlistServiceTest {

	@InjectMocks
	private WishlistService classUnderTest;
	
	@Mock
	private CustomWishlistRepository repository;
	
	@Mock
	private WishlistUIMapper mapper;
	
	@Mock
	private Client clients;

	private final Client MMDE_CLIENT = Clients.clientByShortName("MediaDE");
	private final Locale LOCALE = Locale.forLanguageTag("de");
	
	@Before
	public void setUp() {
		classUnderTest = new WishlistService();
	}

	@Test
	public void getAllWishlists_WishlistNotFound_ReturnNull() {
		Mockito.when(repository.findAllWishlistsByClientAndLocale(MMDE_CLIENT, LOCALE)).thenReturn(Optional.of(mockEmptyList()));
		Mockito.mock(WishlistViewBean.class);
		Mockito.when(mapper.createUIViewBean(Mockito.any())).thenReturn(null);
//		List<WishlistViewBean> allWishlists = classUnderTest.getAllWishlistsByClientAndLocale();
//		assertFalse(allWishlists.isEmpty());
	}
	
	@Test
	public void getAllWishlists_WishlistsFound_MapToViewBean(){
		classUnderTest= new WishlistService();
		Mockito.when(repository.findAllWishlistsByClientAndLocale(Mockito.any(), Mockito.any())).thenReturn(wishlists());
		Mockito.when(mapper.createUIViewBean(Mockito.any())).thenReturn(new ArrayList<WishlistViewBean>());
//		List<WishlistViewBean> allWishlists = classUnderTest.getAllWishlistsByClientAndLocale();
//		assertNotNull(allWishlists);
	}

	private Optional<List<Wishlist>> wishlists() {
		List<Wishlist> list = new ArrayList<>();
		Wishlist wishlist = new Wishlist("1", "wishlist a", "description a", "MediaDE", "de");
		list.add(wishlist);
		return Optional.of(list);
	}
	
	public List<Wishlist> mockEmptyList(){
		List<Wishlist> list = new ArrayList<>();
		list.add(new Wishlist());
		return list;
	}

}
