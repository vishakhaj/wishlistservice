package com.wishlistservice.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wishlistservice.common.UserType;

@Document(collection="users")
public class User {

	private String userId;

	private String emailAddress;

	private String nickname;

	private UserType userType;

	public User() {
	}

	public User(String userId, String emailAddress, String nickname, UserType userType) {
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.userType = userType;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getNickname() {
		return nickname;
	}

	public UserType getUserType() {
		return userType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", emailAddress=" + emailAddress + ", nickname=" + nickname + ", userType="
				+ userType + "]";
	}

}
