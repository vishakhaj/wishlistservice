package com.wishlistservice.common;

public class Client {

	private String abbreviation;

	private String shortString;

	public Client(String abbreviation, String shortString) {
		this.abbreviation = abbreviation;
		this.shortString = shortString;
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

}
