package com.wishlistservice.config.consul;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "LockIndex", "Key", "Flags", "Value", "CreateIndex", "ModifyIndex" })
public class ConsulConfigItem {

	@JsonProperty("LockIndex")
	private Integer lockIndex;
	@JsonProperty("Key")
	private String key;
	@JsonProperty("Flags")
	private Integer flags;
	@JsonProperty("Value")
	private String value;
	@JsonProperty("CreateIndex")
	private Integer createIndex;
	@JsonProperty("ModifyIndex")
	private Integer modifyIndex;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("LockIndex")
	public Integer getLockIndex() {
		return lockIndex;
	}

	@JsonProperty("LockIndex")
	public void setLockIndex(Integer lockIndex) {
		this.lockIndex = lockIndex;
	}

	@JsonProperty("Key")
	public String getKey() {
		return key;
	}

	@JsonProperty("Key")
	public void setKey(String key) {
		this.key = key;
	}

	@JsonProperty("Flags")
	public Integer getFlags() {
		return flags;
	}

	@JsonProperty("Flags")
	public void setFlags(Integer flags) {
		this.flags = flags;
	}

	@JsonProperty("Value")
	public String getValue() {
		return value;
	}

	@JsonProperty("Value")
	public void setValue(String value) {
		this.value = value;
	}

	@JsonProperty("CreateIndex")
	public Integer getCreateIndex() {
		return createIndex;
	}

	@JsonProperty("CreateIndex")
	public void setCreateIndex(Integer createIndex) {
		this.createIndex = createIndex;
	}

	@JsonProperty("ModifyIndex")
	public Integer getModifyIndex() {
		return modifyIndex;
	}

	@JsonProperty("ModifyIndex")
	public void setModifyIndex(Integer modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
