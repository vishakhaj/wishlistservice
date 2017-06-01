package com.wishlistservice.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;

@Repository
public class CustomWishlistRepositoryImpl implements CustomWishlistRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomWishlistRepositoryImpl.class);

	private Cache<Client, Map<Locale, List<Wishlist>>> cache;

	public CustomWishlistRepositoryImpl() {
		cache = CacheBuilder.newBuilder().build();
	}

	public void cacheAllWishlists() {
		Map<Client, Map<Locale, List<Wishlist>>> fetchAllWishlists = fetchAllWishlists();
		cache.invalidateAll();
		cache.putAll(fetchAllWishlists);
	}

	public Map<Client, Map<Locale, List<Wishlist>>> fetchAllWishlists() {
		Map<Client, Map<Locale, List<Wishlist>>> mapByClient = new HashMap<>();
		try {
			List<Wishlist> wishlists = mongoTemplate.findAll(Wishlist.class);
			System.out.println("All wishlists: " + wishlists);

			for (Wishlist wishlist : wishlists) {
				if (wishlist != null) {
					Client client = Clients.clientByShortName(wishlist.getClient());

					Map<Locale, List<Wishlist>> mapByLocale = mapByClient.get(client);
					if (mapByLocale == null) {
						mapByClient.put(client, mapByLocale = new HashMap<>());
					}
					Locale locale = Locale.forLanguageTag(wishlist.getLocale());
					List<Wishlist> listOfWishlists = mapByLocale.get(locale);
					if (listOfWishlists == null) {
						mapByLocale.put(locale, listOfWishlists = new ArrayList<>());
					}
					listOfWishlists.add(wishlist);
				}
			}
		} catch (RuntimeException e) {
			logger.warn(e.toString(), e);
		}
		return mapByClient;
	}
	
	//Find all wishlists by Client and Locale
	@Override
	public Optional<List<Wishlist>> findAllWishlistsByClientAndLocale(Client client, Locale locale) {
		List<Wishlist> listOfWishlistsByLocale = new ArrayList<>();
		
		Map<Locale, List<Wishlist>> mapByClient = cache.getIfPresent(client);
		if(mapByClient== null){
			return Optional.of(new ArrayList<Wishlist>());
		}
		listOfWishlistsByLocale = mapByClient.get(locale); 
		if(listOfWishlistsByLocale == null){
			logger.debug("No locale found in cache");
			return Optional.empty();
		}
		return Optional.of(listOfWishlistsByLocale);		
	}

	//Create a wishlist
	@Override
	public void createWishlist(Wishlist wishlist) {
		mongoTemplate.save(wishlist);
	}
	
	//Delete a wishlist
	@Override
	public void deleteWishlist(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		mongoTemplate.findAndRemove(query, Wishlist.class);
	}

}
