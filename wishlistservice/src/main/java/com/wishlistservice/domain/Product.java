package com.wishlistservice.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

	private Integer articleNumber;
	private String client;
	private String locale;
	private Integer brandId;
	private String brandName;
	private Double rating;
	private Long ean;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(Integer articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getEan() {
		return ean;
	}

	public void setEan(Long ean) {
		this.ean = ean;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
