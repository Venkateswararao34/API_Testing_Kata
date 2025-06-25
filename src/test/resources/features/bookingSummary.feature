#Author: Venkateswararao Machavarapu
#Description : booling.feature file
#Version: 1.0
#Date: 24/06/2025

@bookingSummary @skipToken
Feature: Get Booking Summary Details

	
  @CreateBookingSummaryWithValidDeatils @happyPathDetails
  Scenario Outline: Create and Fetch booking details with valid id
    Given User should able to access create booking API "CreateBookingAPI"
    When User book the hotel room with given details
    | firstname | lastname | depositpaid | phone | email | checkin | checkout |
    | <firstname> | <lastname> | <depositpaid> | <phone> | <email> | <checkin> | <checkout> |
    
    Then Validate status code is 200
    Given User should able to fetch booking summary API "GetBookingSummaryAPI"
    Then Validate status code is 200
    And The response should batch with "GetBookingSummary.json" schema
    And Response should match with "<checkin>" and "<checkout>"

		Examples:
		| firstname | lastname | depositpaid | phone | email | checkin | checkout |
		| Venkateswararao | Machavarapu | true | venkatesh.m@yahoo.com 	| 020000001 | 2025-06-24 | 2025-06-29 |
		| Anusha 					| Machavarapu | true | anusha.m@gmail.com 		| 020000002 | 2025-06-24 | 2025-06-29 |

  @FetchBookingSummaryWithInValidDetails @unhappypath
  Scenario Outline: Fetch booking details with invalid id
    Given User should able to access "GetBookingSummaryAPI"
    Then User sends invalid room id 000
    Then Validate status code is 200
    And The response should batch with "GetBookingSummary.json" schema
    And Response should contain empty details

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
