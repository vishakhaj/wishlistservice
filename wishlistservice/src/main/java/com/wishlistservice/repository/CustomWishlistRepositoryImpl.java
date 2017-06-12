package com.wishlistservice.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mongodb.WriteResult;
import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.domain.Wishlist;

@Repository
public class CustomWishlistRepositoryImpl implements CustomWishlistRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(CustomWishlistRepositoryImpl.class);

	private Cache<Client, Map<Locale, List<Wishlist>>> cacheByClientAndLocale;

	private Cache<String, List<Wishlist>> cacheByUserId;

	public CustomWishlistRepositoryImpl() {
		cacheByClientAndLocale = CacheBuilder.newBuilder().build();
		cacheByUserId = CacheBuilder.newBuilder().build();
	}

	/**
	 * Caches all wish-lists
	 */
	public void cacheAllWishlists() {
		try {
			List<Wishlist> wishlists = mongoTemplate.findAll(Wishlist.class);
			System.out.println("All wishlists: " + wishlists);

			cacheWishlistsByClientAndLocale(wishlists);
			cacheWishlistsByUserId(wishlists);

		} catch (RuntimeException e) {
			logger.warn(e.toString(), e);
		}
	}

	private Map<Client, Map<Locale, List<Wishlist>>> cacheWishlistsByClientAndLocale(List<Wishlist> wishlists) {
		Map<Client, Map<Locale, List<Wishlist>>> mapByClient = new HashMap<>();

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
				return mapByClient;
			}
		}
		cacheByClientAndLocale.invalidateAll();
		cacheByClientAndLocale.putAll(mapByClient);
		return null;
	}

	private Map<String, List<Wishlist>> cacheWishlistsByUserId(List<Wishlist> wishlists) {
		Map<String, List<Wishlist>> mapByUserId = new HashMap<>();
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

	/**
	 * Fetches all wish-lists of a specific User by passing parameter as User
	 * ID.
	 */
	public Optional<List<Wishlist>> findAllWishlistsByUserId(String userId) {
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (!wishlists.isEmpty()) {
			return Optional.of(wishlists);
		}
		return Optional.empty();
	}

	@Override
	/**
	 * Creates a wish-list by a specified client and locale and user ID.
	 */
	public void createWishlistByUserId(String client, String locale, String userId, Wishlist wishlist) {
		Date createdAt = new Date();
		Date modifiedOn = new Date();
		mongoTemplate.save(new Wishlist(wishlist.getName(), wishlist.getDescription(), client, locale, createdAt,
				wishlist.getSource(), wishlist.getType(), wishlist.getPrivacy(), modifiedOn, userId));
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
				if (w.getWishlistId().equals(wishlistId)) {
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
		return Optional.empty();
	}

	@Override
	/**
	 * Deletes a wish-list with a specified ID
	 */
	public void deleteWishlistByUserIdAndWishlistId(String userId, String wishlistId) {
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getWishlistId().equals(wishlistId)) {
					Query query = new Query();
					Criteria criteria = Criteria.where("_id").is(wishlistId);
					query.addCriteria(criteria);
					mongoTemplate.findAndRemove(query, Wishlist.class);
				}
			}
		}
	}

	@Override
	/**
	 * Fetches a wish-list by a specified wish-list ID
	 */
	public Wishlist findWishlistByUserIdAndWishlistId(String wishlistId) {
		for (Entry<String, List<Wishlist>> mapByUserId : cacheByUserId.asMap().entrySet()) {
			List<Wishlist> wishlists = mapByUserId.getValue();
			if (wishlists != null) {
				for (Wishlist wishlist : wishlists) {
					if (wishlist.getWishlistId().toString().equals(wishlistId))
						return wishlist;
				}
			}
		}
		// List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		// if (wishlists != null) {
		// for (Wishlist wishlist : wishlists) {
		// if (wishlist.getWishlistId().equals(wishlistId))
		// return wishlist;
		// }
		// }
		return null;
	}

	/**
	 * Fetches all wish-lists where PRIVACY=PUBLIC
	 */
	public List<Wishlist> findAllPublicWishlists() {
		List<Wishlist> resultList = new ArrayList<>();
		for (Entry<String, List<Wishlist>> mapByUserId : cacheByUserId.asMap().entrySet()) {
			List<Wishlist> wishlists = mapByUserId.getValue();
			if (wishlists != null) {
				for(Wishlist wishlist : wishlists){
					if(Objects.equal(wishlist.getPrivacy().toString(), "PUBLIC")){
						resultList.add(wishlist);
					}
				}
				return resultList;
			}
		}
		return null;
	}

	@Override
	/**
	 * Fetches all wish-lists of a specific USER and a specified SOURCE.
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndSource(String userId, String source) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				System.out.println("source: " + wishlist.getSource());
				if (wishlist.getSource().toString().toLowerCase().equals(source)) {
					resultList.add(wishlist);
				}
			}
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-lists of a specific USER and specified PRIVACY.
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacy(String userId, String privacy) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getPrivacy().toString().toLowerCase().equals(privacy)) {
					resultList.add(wishlist);
				}
			}
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-lists of a specific USER and specified TYPE
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndType(String userId, String type) {
		List<Wishlist> resultList = new ArrayList<>();
		List<Wishlist> wishlists = cacheByUserId.getIfPresent(userId);
		if (wishlists != null) {
			for (Wishlist wishlist : wishlists) {
				if (wishlist.getType().toString().toLowerCase().equals(type)) {
					resultList.add(wishlist);
				}
			}
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-list of a specific USER and SOURCE, PRIVACY, TYPE
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacyAndType(String userId, String source,
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
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-list of a specific USER and SOURCE, PRIVACY.
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndPrivacy(String userId, String source, String privacy) {
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
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-list of a specific USER and SOURCE, TYPE
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndSourceAndType(String userId, String source, String type) {
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
		}
		return resultList;
	}

	@Override
	/**
	 * Fetches all wish-list of a specific USER and PRIVACY, TYPE
	 */
	public List<Wishlist> findAllWishlistsByUserIdAndPrivacyAndType(String userId, String privacy, String type) {
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
		}
		return resultList;
	}

}
