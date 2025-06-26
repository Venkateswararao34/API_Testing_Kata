#Author: Venkateswararao Machavarapu
#Description : booling.feature file
#Version: 1.0
#Date: 25/06/2025

@tokenDetails
Feature: Get Authentication token Details

	
  @GetTokenDeatils @happyPathDetails
  Scenario Outline: Fetch Auth token details
    Given User should able to access "GetAuthTokenAPI"
    When User should send auth details from property file
    Then Validate status code is 200
    Then Validate token details