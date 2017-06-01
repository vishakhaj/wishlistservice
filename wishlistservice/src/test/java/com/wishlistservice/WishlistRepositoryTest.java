package com.wishlistservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoOperations;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.repository.CustomWishlistRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class WishlistRepositoryTest {

	@InjectMocks
	private CustomWishlistRepositoryImpl classUnderTest;

	@Mock
	private MongoOperations mongo;

	@Spy
	private Cache<Client, Map<Locale, List<Wishlist>>> cache = CacheBuilder.newBuilder().build();

	private final Client MMDE_CLIENT = Clients.clientByShortName("MediaDE");
	private final Locale LOCALE = Locale.forLanguageTag("de");

	@Before
	public void setUp() {
		classUnderTest = new CustomWishlistRepositoryImpl();
	}

	@Test
	public void findAllWishlists_CheckCacheEmptyForClient_ReturnEmptyList() {
		Optional<List<Wishlist>> findAllWishlistsByClientAndLocale = classUnderTest
				.findAllWishlistsByClientAndLocale(MMDE_CLIENT, LOCALE);
		assertEquals(0, findAllWishlistsByClientAndLocale.get().size());
	}

	@Test
	public void findAllWishlists_CheckCacheEmptyForLocale_ReturnEmptyList() {

		Optional<List<Wishlist>> findAllWishlistsByClientAndLocale = classUnderTest
				.findAllWishlistsByClientAndLocale(MMDE_CLIENT, LOCALE);
		Mockito.when(cache.getIfPresent(MMDE_CLIENT)).thenReturn(mapListByLocale());
		Mockito.verify(cache, Mockito.never()).invalidateAll();
		Mockito.verify(cache, Mockito.never()).invalidateAll(Mockito.any());
		Mockito.verify(cache, Mockito.never()).invalidate(Mockito.any());
		assertTrue(findAllWishlistsByClientAndLocale.isPresent());

	}

	private Map<Locale, List<Wishlist>> mapListByLocale() {
		Map<Locale, List<Wishlist>> resultMap = new HashMap<>();
		resultMap.put(LOCALE, new ArrayList<>());
		return resultMap;
	}

}
