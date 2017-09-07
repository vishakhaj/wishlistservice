package com.wishlistservice.repositoryimpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;
import com.wishlistservice.config.resttemplate.RestTemplateConfig;
import com.wishlistservice.domain.Item;
import com.wishlistservice.repository.ItemRepository;
import com.wishlistservice.urls.UrlFactory;

@Repository
public class ItemRepositoryImpl implements ItemRepository{

	@Autowired
	private UrlFactory apiUrlFactory;

	@Autowired
	private RestTemplateConfig restTemplateConfig;

	private Cache<Client, Map<Locale, List<Item>>> cache;

	public ItemRepositoryImpl() {
		cache = CacheBuilder.newBuilder().build();
	}

	@Override
	public void cacheAllItems() throws URISyntaxException {
		Map<Client, Map<Locale, List<Item>>> items = getAllItems();
		cache.invalidateAll();
		cache.putAll(items);
	}

	private Map<Client, Map<Locale, List<Item>>> getAllItems() throws URISyntaxException {
		Map<Client, Map<Locale, List<Item>>> mapByClient = new HashMap<>();
		for (Item item : items()) {
			Client client = Clients.clientByShortName(item.getClient());
			Map<Locale, List<Item>> resultByLocale = mapByClient.get(client);
			if (resultByLocale == null) {
				mapByClient.put(client, resultByLocale = new HashMap<>());
			}
			Locale locale = Locale.forLanguageTag(item.getLocale());
			List<Item> itemList = resultByLocale.get(locale);
			if (itemList == null) {
				resultByLocale.put(locale, itemList = new ArrayList<>());
			}
			itemList.add(item);

		}
		return mapByClient;
	}

	private List<Item> items() throws URISyntaxException {
		URI items = apiUrlFactory.createItemUrl();
		List<Item> itemList = Arrays.asList(restTemplateConfig.restTemplate().getForObject(items, Item[].class));

		return itemList;
	}

	@Override
	public Item findItemByItemId(String itemId) {
		for (Entry<Client, Map<Locale, List<Item>>> clientEntry : cache.asMap().entrySet()) {
			Map<Locale, List<Item>> localeMap = clientEntry.getValue();
			if (localeMap != null) {
				for (Entry<Locale, List<Item>> localeEntry : localeMap.entrySet()) {
					List<Item> items = localeEntry.getValue();
					for (Item item : items) {
						if (Objects.equals(item.getItemId(), itemId)) {
							return item;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<Item> findAllItems() {
		System.out.println("code goes in find all items");
		for(Entry<Client, Map<Locale, List<Item>>>  clientEntry : cache.asMap().entrySet()){
			Map<Locale, List<Item>> localeMap = clientEntry.getValue();
			if(localeMap!=null){
				for(Entry<Locale, List<Item>> localeEntry : localeMap.entrySet()){
					System.out.println("code returns items");
					List<Item> items = localeEntry.getValue();
					return items;
				}
			}
		}
		System.out.println("code returns null");
		return null;
	}
}
