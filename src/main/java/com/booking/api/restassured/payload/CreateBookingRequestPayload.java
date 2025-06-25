package com.booking.api.restassured.payload;

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
	/*
	 * Below are the get methods to return the value assigned using constructor
	 */
	public int getBookingId() {
	 return bookingid;
	}
	public int getRoomId() {
		return roomid;
	}
	public boolean getDepositPaid() {
		return depositpaid;
	}
	public String getFirstName() {
		return firstname;
	}
	public String getLastName() {
		return lastname;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getCheckinDate() {
		return checkin;
	}
	public String getCheckoutDate() {
		return checkout;
	}
}
