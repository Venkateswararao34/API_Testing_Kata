package com.booking.api.restassured.payload;

public class AuthLoginPayload {
	public String username;
	public String password;
	

	/*
	 * Constructor to set assign the values
	 */
	public AuthLoginPayload(String username, String password) {
		this.username			=	username;
		this.password			=	password;
	}
	
	/*
	 * The below methods are for getting values which are assigned using constructor
	 */
	
	public String getUserName() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
