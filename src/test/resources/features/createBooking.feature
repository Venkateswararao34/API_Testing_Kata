#Author: Venkateswararao Machavarapu
#Description : booling.feature file
#Version: 1.0
#Date: 24/06/2025

@createBooking
Feature: Get Booking Summary Details

	Background:
		Given User should able to access "CreateBookingAPI"
		
  @validBookingDetails @happyPathDetails
  Scenario Outline: User should able to create booking request successfully
    When User book the hotel room with given details
    | firstname 	| lastname 		| depositpaid 	| email 	| phone 	| checkin 	| checkout 		|
    | <firstname> | <lastname> 	| <depositpaid> | <email> | <phone> | <checkin> | <checkout> 	|
    
    Then Validate status code is 200
    And The response should contain valid details

		Examples:
		| firstname 			| lastname 		| depositpaid | email 									| phone 		| checkin 		| checkout 		|
		| Venkateswararao | Machavarapu | true 				| venkatesh.m@yahoo.com 	| 2000000100123 | 2025-07-24 	| 2025-07-29 	|
		| Anusha 					| Machavarapu | true 				| anusha.m@gmail.com 			| 2000000200223 | 2025-07-24 	| 2025-07-29 	|	  