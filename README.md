REST API Framework Hotel Booking Rest API Testing using Rest Assured


This project is an sample Rest API test automation framework for Retrieving the Hotel Booking summary details using Rest Assured, Cucumber, TestNG, and Maven. This project gives the basic example of how to retrieve booking summary details by using room id.

1. Overview:
The framework uses a Behaviour Driven development(BDD) approach using Gherkin language which makes the test scenarios in more readable and easy to understand for both technical and non-technical team members.

Application URL: https://automationintesting.online/

Booking Swagger Spec: https://automationintesting.online/booking/swagger-ui/index.html

2. Technology stack:

     •  REST API

     •	REST Assured 5.2.2

     •	Cucumber 7.22.2

     •	Maven 3.9.9

     •	TestNG 7.11

     •	Java 17

     •	Extent Report 
 
 4. Features Implemented:
    
     •	CreateBookingRequest (POST)
    
     •	BookingSummaryByRoomId (GET)
    
     •	AuthenticationToken (POST)
6. Observations:

     URL has been changed to below

     OLD Url: https://automationintesting.online/

     New Url: https://automationintesting.online/api/booking

8. Setup Instructions:

   •	Install Java either with 17 or 24
   
    •	Add required plugins for 

            o	 RestAssured
   
            o	  MavenSurefire Plugin
   
            o	Cucumber-java and Cucumber core
   
            o	Extent Report for Reporting
   
            o	TestNG (Also install testng from markaetplace)
   
9. Implentation & Execution

           •	Declare TestRunner class extending AbstractTestNGCucumberTests
   
           •	Mention all the required cucumber options for stepdefinition, features and reports
   
           •	Create stepdefinition page followed by stepdefinition classes to perform api requests
   
           •	Initiate ExtentReport class and add the test steps methods

           •	For logging either write custom logging methods or include log4j for better tracking

           •	Create required features in features folder

           •	Once proper mapping is completed, execute sample tests

        Steps to Execute:
           •	Install git in your local machine
   
           •	Create folder at your desired location

           •	Open Git bash and navigate to location where folder is created

           •	Use git clone command to pull the code from git repository to local

           •	Open your favourate IDE like Eclipse or intellij

           •	Once proper mapping is completed, execute sample tests

11. Reports

          •	Cucumber HTML reports are generated in target/cucumber-reports
    
          •	JUnit reports are available in target/surefire-reports
    
          •	Transaction logs are available in target/logs/transactions
    
          •	TestNG Reports are available in Report folder

13. Common Issues encountered:

         •	Declaring same test steps in carious step definition classes
    
         •	Parameterization
    
         •	TestNG Mapping Annotations

