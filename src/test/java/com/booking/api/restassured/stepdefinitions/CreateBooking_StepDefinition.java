package com.booking.api.restassured.stepdefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.Reporter;
import com.booking.api.restassured.payload.CreateBookingRequestPayload;

import static io.restassured.RestAssured.given;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.module.jsv.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CreateBooking_StepDefinition {
	
	public TestContext testContext 					=	new TestContext();
	public TestUtils testUtils						=	new TestUtils();
	public Config config							=	new Config();
	LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
	public Reporter Report							=	new Reporter();
	
	/*
	 * This step definition get the api name from the Feature file
	 * 
	 */
	@Given("User should able to access {string}")
	public void AssignEndPoint(String apiName) {
		try {
			int randomId = new Random().nextInt(7500) + 1;
			testContext.setContext("apiName", apiName);
			testContext.setContext("basePath", config.getAPIResourceEndPointURL(apiName, ""));
			
			System.out.println(testContext.context);
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@When("User book the hotel room with given details")
	public void createValidBooking(DataTable dataTable) {
		processCreateBooking(dataTable);
	}
	/*
	 * This Method is for Processing Creating booking request payload using Post method
	 * 
	 */
	public void processCreateBooking(DataTable dataTable) {
		try {
			List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
			int randomId = new Random().nextInt(7500) + 1;
			for(int dataCount=0;dataCount<dataList.size();dataCount++) {
				Map<String,String> dataMap			=	new HashMap<String,String>();
				dataMap								=	dataList.get(dataCount);
				
				RequestSpecification requestSpec = new RequestSpecBuilder()
										            .setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
										            .addHeader("Content-Type", "application/json")
										            .addHeader("Accept", "application/json")
										            .build();
				
				testUtils.generateCreateBookingRequest(dataMap, randomId);
				testUtils.setContext(dataMap);
				testContext.setRoomId(randomId);
				
				Map<String, Object> bookingDates 	= 	new HashMap<>();
				bookingDates.put("checkin", dataMap.get("checkin"));
				bookingDates.put("checkout", dataMap.get("checkout"));
				
				Map<String, Object> reqPayload 		= 	new HashMap<>();
				reqPayload.put("roomid", randomId);
				reqPayload.put("depositpaid", dataMap.get("depositpaid"));
				reqPayload.put("firstname", dataMap.get("firstname"));
				reqPayload.put("lastname", dataMap.get("lastname"));
				reqPayload.put("email", dataMap.get("email"));
				reqPayload.put("phone", dataMap.get("phone"));
				reqPayload.put("bookingdates", bookingDates);
				
				Response rsp 						= 	given(requestSpec)
														.log().all()
														.body(reqPayload)
														.when().post(testContext.getContext("basePath"));

				testContext.setResponse(rsp);
				if(rsp.getStatusCode() == 200) {
					Report.setReportPassStep("Success Response Recieved with Status code : 200");
					Report.setReportPassStep(rsp.getBody().asString());
					 for (String key : dataMap.keySet()) {
						 String value = dataMap.get(key);
					     testContext.setContext(key, value);
					 }
				}else {
					Report.setReportFailStep("Failure Response Recieved with Status code : "+rsp.getStatusCode());
					Report.setReportPassStep(rsp.getBody().asPrettyString());
				}
			}
			//testContext.context.clear();
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Then("Validate status code is {int}")
	public void validateStatusCode(Integer code) {
		try {
			if(testContext.getResponse() != null) {
				int responseCode			=	testContext.getResponse().getStatusCode();
				if(code == responseCode) {
					Report.setReportPassStep("Success Response Recieved with Status code : "+responseCode);
					Report.setReportPassStep(testContext.response.getBody().asPrettyString());
				}else {
					Report.setReportFailStep("Failure Response Recieved with Status code : "+responseCode);
				}
			}else {
				Report.setReportFailStep("Failure Response, Response is null : ");
			}
		}catch(NullPointerException npe) {
			
		}catch(Exception e) {
			
		}
	}
	
	@Then("The response should contain valid details")
	public void validateCreateBookingResponse() {
		Response response 			= 	testContext.getResponse();
		String responseBody 		= 	response.asString();
		String firstName			=	testUtils.getJsonValue(response, "firstname");
		String lastName				=	testUtils.getJsonValue(response, "lastname");
		String email				=	testUtils.getJsonValue(response, "email");
		String phone				=	testUtils.getJsonValue(response, "phone");
		String depositpaid			=	testUtils.getJsonValue(response, "depositpaid");
		String checkin				=	testUtils.getJsonValue(response, "checkin");
		String checkout				=	testUtils.getJsonValue(response, "checkout");
		String roomId				=	testUtils.getJsonValue(response, "roomid");
		String bookingid			=	testUtils.getJsonValue(response, "bookingid");
		
		if(firstName.trim().equalsIgnoreCase(testContext.getContext("firstname"))) {
			Report.setReportFailStep("First name validated successfully : "+firstName);
		}else {
			Report.setReportFailStep("First name is incorrectly displayed : Expected : "+testContext.getContext("firstname")+", Recieved : "+firstName);
		}
		
		if(lastName.trim().equalsIgnoreCase(testContext.getContext("lastname"))) {
			Report.setReportFailStep("Last name validated successfully : "+lastName);
		}else {
			Report.setReportFailStep("Last name is incorrectly displayed : Expected : "+testContext.getContext("lastname")+", Recieved : "+lastName);
		}
		
		if(email.trim().equalsIgnoreCase(testContext.getContext("email"))) {
			Report.setReportFailStep("Email validated successfully : "+email);
		}else {
			Report.setReportFailStep("Email is incorrectly displayed : Expected : "+testContext.getContext("email")+", Recieved : "+email);
		}
		
		if(phone.trim().equalsIgnoreCase(testContext.getContext("phone"))) {
			Report.setReportFailStep("phone validated successfully : "+email);
		}else {
			Report.setReportFailStep("phone is incorrectly displayed : Expected : "+testContext.getContext("phone")+", Recieved : "+phone);
		}
		
		if(depositpaid.trim().equalsIgnoreCase(testContext.getContext("depositpaid"))) {
			Report.setReportFailStep("depositpaid validated successfully : "+depositpaid);
		}else {
			Report.setReportFailStep("depositpaid is incorrectly displayed : Expected : "+testContext.getContext("depositpaid")+", Recieved : "+depositpaid);
		}
		

		if(checkin.trim().equalsIgnoreCase(testContext.getContext("checkin"))) {
			Report.setReportFailStep("checkin validated successfully : "+checkin);
		}else {
			Report.setReportFailStep("checkin is incorrectly displayed : Expected : "+testContext.getContext("checkin")+", Recieved : "+checkin);
		}
		
		if(checkout.trim().equalsIgnoreCase(testContext.getContext("checkout"))) {
			Report.setReportFailStep("checkout validated successfully : "+checkout);
		}else {
			Report.setReportFailStep("checkout is incorrectly displayed : Expected : "+testContext.getContext("checkout")+", Recieved : "+checkout);
		}
		
		if(roomId.trim().equalsIgnoreCase(testContext.getContext("roomId"))) {
			Report.setReportFailStep("roomId validated successfully : "+roomId);
		}else {
			Report.setReportFailStep("roomId is incorrectly displayed : Expected : "+testContext.getContext("checkout")+", Recieved : "+roomId);
		}
		
		if(bookingid.trim().equalsIgnoreCase(testContext.getContext("bookingid"))) {
			Report.setReportFailStep("bookingid validated successfully : "+bookingid);
		}else {
			Report.setReportFailStep("bookingid is incorrectly displayed : Expected : "+testContext.getContext("bookingid")+", Recieved : "+bookingid);
		}
	}
}
