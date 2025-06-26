#Author: Venkateswararao Machavarapu
#Description : booling.feature file
#Version: 1.0
#Date: 25/06/2025

@bookingSummary
Feature: Get Booking Summary Details

	
  @CreateBookingSummaryWithValidDeatils @happyPathDetails
  Scenario Outline: Create and Fetch booking details with valid id
    Given User should able to access "CreateBookingAPI"
    When User book the hotel room with given details
    | firstname 	| lastname 		| depositpaid 	| email 	| phone 	| checkin 	| checkout 		|
    | <firstname> | <lastname> 	| <depositpaid> | <email> | <phone> | <checkin> | <checkout> 	|
    
    Then Validate status code is 200
    Given User should able to fetch booking summary API "GetBookingSummaryAPI"
    Then User gets the room id from Create Booking api
    Then Validate status code is 200
    And Response should match with "<checkin>" and "<checkout>"

		Examples:
		| firstname 			| lastname 		| depositpaid | email 									| phone 		| checkin 		| checkout 		|
		| Venkateswararao | Machavarapu | true 				| venkatesh.m@yahoo.com 	| 020000001 | 2025-07-24 	| 2025-07-29 	|
		| Anusha 					| Machavarapu | true 				| anusha.m@gmail.com 			| 020000002 | 2025-07-24 	| 2025-07-29 	|