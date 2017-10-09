package com.corpse.util;

import java.security.Principal;

import org.springframework.security.core.Authentication;

import com.corpse.model.User;

public class Common {

	public final static String ARRAY_STRING_TYPE = "com.corpse.util.ArrayStringType"; 
	public final static String MAP_JSON_TYPE = "com.corpse.util.MapJsonType";
	public final static String LIST_JSON_TYPE = "com.corpse.util.ListJsonType";
	public final static String ROLE_OWNER = "ROLE_OWNER";

	public static String log(String username, String message) {
		return "[ " + username + " ] " + message;
	}

	public static User getUser(Principal principal) {
		Authentication authentication = (Authentication) principal;
		if(authentication == null) {
			return null;
		}

		User user = (User) authentication.getPrincipal();
		return user;
	}
}
