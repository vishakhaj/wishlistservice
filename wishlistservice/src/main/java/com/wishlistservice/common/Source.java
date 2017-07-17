package com.wishlistservice.common;

public enum Source {

	ONLINE("online"), MARKET("market");
	
	private String source;
	
	private Source(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
	
	
}
