package com.wishlistservice.common;

import java.util.Locale;

public class Client {

	private String abbreviation;

	private String shortString;

	private Locale[] locales;

	public Client(String abbreviation, String shortString, Locale... locales) {
		this.abbreviation = abbreviation;
		this.shortString = shortString;
		this.locales = locales;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getShortString() {
		return shortString;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		return abbreviation.equalsIgnoreCase(((Client) o).abbreviation);
	}

	@Override
	public int hashCode() {
		return abbreviation.hashCode();
	}

	public boolean isMultiLanguage() {
		return locales.length > 1;
	}

	/**
	 * Returns the "opposite" locale for this client. If more than 2 locales exist for a client, the first matching
	 * other locale is returned. If this client has only 1 locale, null is returned.
	 * 
	 * @param locale
	 * @return
	 */
	public Locale getCounterPartLocale(Locale locale) {
		if (!isMultiLanguage())
			return null;

		for (Locale otherLocale : locales) {
			if (!otherLocale.equals(locale)) {
				return otherLocale;
			}
		}

		throw new IllegalArgumentException("Client has more than 1 locale but all locales are the exact same!?");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [");
		builder.append("abbreviation=");
		builder.append(abbreviation);
		builder.append(", shortString=");
		builder.append(shortString);
		builder.append("]");
		return builder.toString();
	}

	public Locale[] getLocales() {
		return locales;
	}

}
