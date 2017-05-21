package com.wishlistservice.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Clients {

	MMDE("MediaDE", Locale.GERMANY), SEDE("SaturnDE", Locale.GERMANY);

	private static Map<String, Client> clientsByName = new HashMap<>();

	private static Map<String, Client> clientsByAbbr = new HashMap<>();

	private String shortName;

	private Locale[] locales;
	
	static {
		for (Clients client : values()) {
			Client clientInfo = new Client(client.name(), client.shortName);
			clientsByName.put(client.shortName, clientInfo);
			clientsByAbbr.put(client.name(), clientInfo);
		}
	}
	
	public static Client clientByShortName(String shortName) {
		return clientsByName.get(shortName);
	}

	private Clients(String shortString, Locale... locales) {
		this.shortName = shortString;
		this.locales = locales;
	}

	public String getShortName() {
		return shortName;
	}

	public Locale[] getLocales() {
		return locales;
	}

}
