package com.wishlistservice.repositoryimpl;

/**
 * Performs add and delete operations on items in a wish-list
 */
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.wishlistservice.domain.Item;
import com.wishlistservice.domain.Wishlist;
import com.wishlistservice.repository.Wishlist_Item_Repository;

@Repository
public class Wishlist_Item_RepositoryImpl implements Wishlist_Item_Repository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Optional<WriteResult> addItemToWishlist(Wishlist wishlist, Item item) {
		Query query = new Query(Criteria.where("_id").is(wishlist.getWishlistId()));
		Update update = new Update();
		update.addToSet("items", item);
		update.set("modifiedOn", new Date());
		WriteResult itemAdded = mongoTemplate.updateFirst(query, update, Wishlist.class);
		return Optional.of(itemAdded);
	}

	@Override
	public Optional<WriteResult> deleteItemFromWishlist(Wishlist wishlist, Item item) {
		String[] itemArray = { item.getItemId() };
		Query findQuery = Query.query(Criteria.where("items.itemId").in(Arrays.asList(itemArray)));
		DBObject pullUpdate = BasicDBObjectBuilder.start()
				.add("itemId", BasicDBObjectBuilder.start().add("$in", itemArray).get()).get();

		Update update = new Update().pull("items", pullUpdate);
		update.set("modifiedOn", new Date());
		WriteResult itemDeleted = mongoTemplate.updateMulti(findQuery, update, Wishlist.class);
		return Optional.of(itemDeleted);
	}
}
