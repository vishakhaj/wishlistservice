package com.wishlistservice.utils;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.wishlistservice.common.Client;
import com.wishlistservice.common.Clients;

@Component
public class Context {

	public static final String CLIENT = System.getenv("CLIENT");

	public static final String LOCALE = System.getenv("LOCALE");

	public static final Client client = Clients.clientByShortName(CLIENT);

	public static final Locale locale = Locale.forLanguageTag(LOCALE);
}
