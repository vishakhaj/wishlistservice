package com.wishlistservice.repositoryimpl;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.common.Privacy;
import com.wishlistservice.common.SortOrder;
import com.wishlistservice.common.SortWishlistByDate;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.repository.CustomWishlistRepository;

@Repository
public class CustomWishlistRepositoryImpl implements CustomWishlistRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(CustomWishlistRepositoryImpl.class);

	private Cache<Client, Map<Locale, List<Wishlist>>> cacheByClientAndLocale;

	private Cache<String, List<Wishlist>> cacheByUserId;

	public CustomWishlistRepositoryImpl() {
		cacheByClientAndLocale = CacheBuilder.newBuilder().maximumSize(1000).build();
		cacheByUserId = CacheBuilder.newBuilder().maximumSize(1000).build();
	}

	/**
	 * Caches all wish-lists
	 */
	public void cacheAllWishlists() {
		try {
			// mongoTemplate.indexOps(Wishlist.class).ensureIndex(new
			// Index().on("createdAt", Direction.DESC));
			Query query = new Query();
			query.with(new Sort(Sort.Direction.DESC, "createdAt"));
			List<Wishlist> wishlists = mongoTemplate.find(query, Wishlist.class);
			System.out.println("All wishlists: " + wishlists);

			cacheWishlistsByClientAndLocale(wishlists);
			cacheWishlistsByUserId(wishlists);

		} catch (RuntimeException e) {
			logger.warn(e.toString(), e);
		}
	}

	/**
	 * 
	 * @param wishlists
	 *            - List<Wishlist>
	 * @return cache of wish-lists by CLIENT and LOCALE.
	 */
	private Map<Client, Map<Locale, List<Wishlist>>> cacheWishlistsByClientAndLocale(List<Wishlist> wishlists) {
		Map<Client, Map<Locale, List<Wishlist>>> mapByClient = new HashMap<>();

		if (wishlists.isEmpty()) {
			cacheByClientAndLocale.invalidateAll();
			cacheByClientAndLocale.putAll(mapByClient);
			return new HashMap<>();
		}
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
		cacheByClientAndLocale.invalidateAll();
		cacheByClientAndLocale.putAll(mapByClient);
		return mapByClient;
	}

	/**
	 * @param wishlists
	 *            - List<Wishlist>
	 * @return cache of wish-lists by USER ID.
	 */
	private Map<String, List<Wishlist>> cacheWishlistsByUserId(List<Wishlist> wishlists) {
		System.out.println("wishlists: " + wishlists);
		Map<String, List<Wishlist>> mapByUserId = new HashMap<>();
		if (wishlists.isEmpty()) {
			cacheByUserId.invalidateAll();
			cacheByUserId.putAll(mapByUserId);
			return new HashMap<>();
		}
		
		for (Wishlist wishlist : wishlists) {
			if (wishlist != null) {
				System.out.println("user id: " + wishlist.getUserId());
				List<Wishlist> listOfWishlists = mapByUserId.get(wishlist.getUserId());
				if (listOfWishlists == null) {
					mapByUserId.put(wishlist.getUserId(), listOfWishlists = new ArrayList<>());
				}
				listOfWishlists.add(wishlist);
				System.out.println("list of wishlists: " + listOfWishlists);

			}
		}
		cacheByUserId.invalidateAll();
		cacheByUserId.putAll(mapByUserId);
		return mapByUserId;
	}

	@Override
	/**
	 * Creates a wish-list by a specified client and locale and user ID.
	 */
	public void createWishlistByUserId(String client, String locale, String userId, Wishlist wishlist) {
		
//		ZoneId zoneId = ZoneId.of("Europe/Berlin");
//		Instant instant = Instant.now();
//		
////		System.out.println("instant" + instant);
//		
//		ZonedDateTime zdt = instant.atZone(zoneId);
//		System.out.println("Zone date time: " + zdt);
//		
////		System.out.println("zoned time zone: " + zdt);
//		
////		LocalDateTime ldt = LocalDateTime.now();
////		System.out.println("local date time: " + ldt);
////		ZonedDateTime zdt = ldt.atZone(zoneId);
////		// ZonedDateTime zdt = ZonedDateTime.of(ldt, zoneId);
//		
//		Date date = Date.from(zdt.toInstant());
//		System.out.println("date: " + date);
//		
		mongoTemplate.save(new Wishlist(wishlist.getName(), wishlist.getDescription(), client, locale, new Date(),
				wishlist.getSource(), wishlist.getType(), wishlist.getPrivacy(), new Date(), userId, SortOrder.DEFAULT,
				new ArrayList<Item>()));
	}

	@Override
	/**
	 * Updates the already existing wish-list record of wish-list ID of a
	 * specific user ID
	 */
	public Optional<WriteResult> updateWishlistByUserIdAndWishlistId(String userId, String wishlistId,
			Wishlist wishlist) {

		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist w : wishlists) {
				if (w.getUserId().equals(userId) && w.getWishlistId().equals(wishlistId)) {
					Query query = new Query(Criteria.where("_id").is(w.getWishlistId()));
					Update update = new Update();
					update.set("name", wishlist.getName());
					update.set("description", wishlist.getDescription());
					update.set("source", wishlist.getSource());
					update.set("privacy", wishlist.getPrivacy());
					update.set("modifiedOn", new Date());
					WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Wishlist.class);
					return Optional.of(updateFirst);
				}
			}
		}
		System.out.println("Wishlist is not owned by this user ID" + userId);
		return Optional.empty();
	}

	@Override
	/**
	 * Deletes a wish-list with a specified user ID and wish-list ID.
	 */
	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId) {
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getUserId().equals(userId) && wishlist.getWishlistId().equals(wishlistId)) {
					Query query = new Query();
					Criteria criteria = Criteria.where("_id").is(wishlistId);
					query.addCriteria(criteria);
					Wishlist wishlistDeleted = mongoTemplate.findAndRemove(query, Wishlist.class);
					
					System.out.println("wishlist deleted: " + wishlistDeleted);
				}
			}
		}
		
//		String[] wishlistArray = { wishlistId };
//		Query findQuery = Query.query(Criteria.where("wishlistId").in(Arrays.asList(wishlistArray)));
//		DBObject pullUpdate = BasicDBObjectBuilder.start()
//				.add("wishlistId", BasicDBObjectBuilder.start().add("$in", wishlistArray).get()).get();
//
//		Update update = new Update().pull("wishlist", pullUpdate);
//		WriteResult wishlistDeleted = mongoTemplate.updateMulti(findQuery, update, Wishlist.class);
//		return Optional.of(wishlistDeleted);
	}

	@Override
	/**
	 * Deletes all wish-lists of a specific user ID.
	 */
	public void deleteAllWishlistsByUserId(String userId) {
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				mongoTemplate.remove(wishlist);
				System.out.println("Wishlist with wishlist ID" + wishlist.getWishlistId() + " deleted..");
			}
		}
	}

	/**
	 * Returns all wish-lists of a specific user by USER ID.
	 * 
	 * @throws ParseException
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserId(String userId) throws ParseException {
		cacheAllWishlists();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists == null || wishlists.isEmpty()) {
			return Optional.empty();
		}
		System.out.println("wishlists: " + wishlists);
		for(Wishlist wishlist : wishlists){
			List<Item> items = wishlist.getItems();
			System.out.println("items: " + items);
		}
		return Optional.of(wishlists);
	}

	@Override
	/**
	 * Returns a wish-list by a specified wish-list ID
	 */
	public Optional<Wishlist> findWishlistByUserIdAndWishlistId(String wishlistId) {
		for (Entry<String, List<Wishlist>> mapByUserId : cacheByUserId.asMap().entrySet()) {
			List<Wishlist> wishlists = mapByUserId.getValue();
			if (wishlists != null) {
				for (Wishlist wishlist : wishlists) {
					if (Objects.equal(wishlist.getWishlistId(), wishlistId)) {
						return Optional.of(wishlist);
					}
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns all wish-lists where privacy=PUBLIC
	 */
	public Optional<List<Wishlist>> findAllPublicWishlists(String privacy) {
		List<Wishlist> resultList = new ArrayList<>();
		for (Entry<String, List<Wishlist>> mapByUserId : cacheByUserId.asMap().entrySet()) {
			List<Wishlist> wishlists = mapByUserId.getValue();
			if (wishlists.isEmpty() || wishlists == null) {
				return Optional.empty();
			}
			if (wishlists != null) {
				for (Wishlist wishlist : wishlists) {
					if (Objects.equal(privacy, Privacy.PUBLIC.toString().toLowerCase())
							&& Objects.equal(wishlist.getPrivacy().toString().toLowerCase(), privacy)) {
						resultList.add(wishlist);
					}

				}
			}
		}
		return Optional.of(resultList);
	}

	@Override
	/**
	 * Returns all wish-lists of a specific USER and a specific SOURCE.
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSource(String userId, String source) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getSource().toString().toLowerCase().equals(source)) {
					resultList.add(wishlist);
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-lists of a specific USER and specific PRIVACY.
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndPrivacy(String userId, String privacy) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getPrivacy().toString().toLowerCase().equals(privacy)) {
					resultList.add(wishlist);
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-lists of a specific USER and specified TYPE
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndType(String userId, String type) {
		List<Wishlist> resultList = new ArrayList<>();

		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getType().toString().toLowerCase().equals(type)) {
					resultList.add(wishlist);
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-list of a specific USER and SOURCE, PRIVACY, TYPE
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source,
			String privacy, String type) {
		List<Wishlist> resultList = new ArrayList<>();

		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getSource().toString().toLowerCase().equals(source)) {
					if (wishlist.getPrivacy().toString().toLowerCase().equals(privacy)) {
						if (wishlist.getType().toString().toLowerCase().equals(type)) {
							resultList.add(wishlist);
						}
					}
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-list of a specific USER and SOURCE, PRIVACY.
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source,
			String privacy) {
		List<Wishlist> resultList = new ArrayList<>();

		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getSource().toString().toLowerCase().equals(source)) {
					if (wishlist.getPrivacy().toString().toLowerCase().equals(privacy)) {
						resultList.add(wishlist);
					}
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-list of a specific USER and SOURCE, TYPE
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSourceAndType(String userId, String source,
			String type) {
		List<Wishlist> resultList = new ArrayList<>();

		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getSource().toString().toLowerCase().equals(source)) {
					if (wishlist.getType().toString().toLowerCase().equals(type)) {
						resultList.add(wishlist);
					}
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Returns all wish-list of a specific USER and PRIVACY, TYPE
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy,
			String type) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getPrivacy().toString().toLowerCase().equals(privacy)) {
					if (wishlist.getType().toString().toLowerCase().equals(type)) {
						resultList.add(wishlist);
					}
				}
			}
			return Optional.of(resultList);
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<Wishlist>> findAllWishlistsByUserIdAndSortOrder(String userId, String sortOrder) {
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);

		if (wishlists != null) {
			if (Objects.equal(SortOrder.DESC.toString().toLowerCase(), sortOrder)) {
				Collections.sort(wishlists, new SortWishlistByDate().reversed());
				System.out.println("wishlists" + wishlists);
				return Optional.of(wishlists);
			} else if (Objects.equal(SortOrder.ASC.toString().toLowerCase(), sortOrder)) {
				Collections.sort(wishlists, new SortWishlistByDate());
				return Optional.of(wishlists);
			}
		}

		return Optional.empty();
	}

	@Override
	public Set<Item> findAllUniqueWishlistItemsByUserId(String userId) {
		Set<Item> uniqueItemList = new HashSet<>();
		
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if(wishlists!=null){
			for(Wishlist wishlist : wishlists){
				System.out.println("Wishlist: " + wishlist);
				for(Item item : wishlist.getItems()){
					System.out.println("item id: " + item.getItemId());
					if(!uniqueItemList.contains(item.getItemId())){
						uniqueItemList.add(item);
					}
				}
				
			}
			return uniqueItemList;
		}
		return null;
	}

}
