package com.booking.api.restassured.config;

public class EnumConstants {
	
	/*
	 * The below enum helps to define to define the API Base URLS 
	 */
	public enum API_BASE_URL{
		GetAuthTokenAPI,
		CreateBookingAPI,
		GetBookingSummaryAPI,
		GetByRoomIdAPI,
		GetByBookingIdAPI,
		UpdateBookingAPI,
		DeleteBookingAPI
	}
}
