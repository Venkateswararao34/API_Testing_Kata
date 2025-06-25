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
    | firstname 	| lastname 		| depositpaid 	| phone 	| email 	| checkin 	| checkout 		|
    | <firstname> | <lastname> 	| <depositpaid> | <phone> | <email> | <checkin> | <checkout> 	|
    
    Then Validate status code is 200
    And The response should contain valid details

		Examples:
		| firstname 			| lastname 		| depositpaid | phone 									| email 		| checkin 		| checkout 		|
		| Venkateswararao | Machavarapu | true 				| venkatesh.m@yahoo.com 	| 020000001 | 2025-06-24 	| 2025-06-29 	|
		| Anusha 					| Machavarapu | true 				| anusha.m@gmail.com 			| 020000002 | 2025-06-24 	| 2025-06-29 	|

  @InValidBookingDetails @unhappypath
  Scenario Outline: Create Booking details request should fail when incorrect values provided
    When User book the hotel room with given invalid details
    | firstname 	| lastname 		| depositpaid 	| phone 	| email 	| checkin 	| checkout 		|
    | <firstname> | <lastname> 	| <depositpaid> | <phone> | <email> | <checkin> | <checkout> 	|
    Then Validate status code should be 400
    And The response should batch with "GetBookingSummary.json" schema
    And the response should contain the error message "<errorMsg>"

   Examples:
		  | firstname | lastname 			| depositpaid | email                  	| phone       | checkin    | checkout   | errorMsg                                                                                 |
		  |           | Macha   			| true        | venkatesh.m@gmail.com  	| 123456789102| 2025-06-01 | 2025-06-17 | size must be between 3 and 18, Firstname should not be blank                             |
		  | Venky     |          			| true        | venkatesh.m@gmail.com 	| 123456789102| 2025-06-28 | 2025-06-12 | size must be between 3 and 30, Lastname should not be blank                              |
		  | 123@/     | Machavarapu   | false         venkatesh.m@gmail.com  	| 123456789102| 2025-07-15 | 2025-07-17 | Firstname should be a valid string                                                       |
		  | Venky     |     					| true        | venkatesh.m@gmail.com 	| 123456789102| 2025-06-11 | 2025-06-12 | Lastname should be a valid string                                                        |
		  | Ven       | Machavarapu  	| false       | venkatesh.m@gmail.com   | 123456789102| 2025-08-12 | 2025-06-15 | size must be between 3 and 18                                                            |
		  | Venky     | Masaram       | false       | venkatesh.m@gmail.com   | 123456789102| 2025-09-12 | 2025-06-15 | size must be between 3 and 30                                                            |
		  | Venky     | Ma  					| false       | venkatesh m@gmail.com  	| 123456789102| 2025-10-12 | 2025-10-15 | must be a well-formed email address                                                      |
		  | V     		| M  						| false       | venkateshm.@gmail.com  	| 123         | 2025-11-12 | 2025-11-15 | size must be between 11 and 21                                                           |
		  | Venky     | Macha  				| false       | venkatesh@m.com        	| 123456789102|            | 2025-01-15 | must not be null                                                                         |
		  | Ve     		| Machavarapu  	| false       | venkatesh.m@gmail.com   | 123456789102| 2025-12-12 |            | must not be null                                                                         |
		  