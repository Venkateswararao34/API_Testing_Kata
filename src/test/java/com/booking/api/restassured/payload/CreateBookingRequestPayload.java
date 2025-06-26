package com.booking.api.restassured.payload;

import com.booking.api.restassured.engine.TestContext;

public class CreateBookingRequestPayload {
	public int bookingid;
	public int roomid;
	public boolean depositpaid;
	public String firstname;
	public String lastname;
	public String email;
	public String phone;
	public String checkin;
	public String checkout;
	
	/*
	 * Constructor to set assign teh values
	 */
	public CreateBookingRequestPayload(int bookingid, int roomid, boolean depositpaid, String firstname,
			String lastname, String email, String phone,
			String checkin, String checkout) {
		this.bookingid			=	bookingid;
		this.roomid				=	roomid;
		this.depositpaid		=	depositpaid;
		this.firstname			=	firstname;
		this.lastname			=	lastname;
		this.email				=	email;
		this.phone				=	phone;
		this.checkin			=	checkin;
		this.checkout			=	checkout;
	}
	
}
