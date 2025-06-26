package com.booking.api.restassured.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.Reporter;
import com.booking.api.restassured.payload.CreateBookingRequestPayload;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingSummary_StepDefinition {
	public TestContext testContext 					=	new TestContext();
	public TestUtils testUtils						=	new TestUtils();
	public Config config							=	new Config();
	
	LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
	Reporter Report									=	new Reporter();
	
	/*
	 * This step definition get the api name from the Feature file
	 * 
	 */
	@Given("User should able to fetch booking summary API {string}")
	public void AssignEndPoint(String apiName) {
		try {
			testContext.setContext("apiName", apiName);
			testContext.setContext("basePath", config.getAPIResourceEndPointURL(apiName, ""));
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Then("User gets the room id from Create Booking api")
	public void GetAndSetRoomId() {
		try {
			testContext.setContext("roomid", testContext.getContext("roomid"));
			processBookingSummary();
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * This Method is for Processing booking summary request payload using Get method
	 * 
	 */
	private void processBookingSummary() {
		try {
			LoadEnvironmentProperties loadProperties=	new LoadEnvironmentProperties();
			Reporter Report							=	new Reporter();
			RequestSpecification requestSpec = new RequestSpecBuilder()
		            .setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
		            .addHeader("Content-Type", "application/json")
		            .addHeader("Accept", "application/json")
		            .build();

			int roomid							=	testContext.getRoomId();
		

			Response rsp 						= 	given(requestSpec)
													.log().all()
													.queryParam("roomid", roomid)
													.when().get(testContext.getContext("basePath"));

			testContext.setResponse(rsp);

			if(rsp.getStatusCode() == 200) {
				Report.setReportPassStep("Success Response Recieved with Status code : 200");
				Report.setReportPassStep(rsp.getBody().asString());
			}else {
				Report.setReportFailStep("Failure Response Recieved with Status code : "+rsp.getStatusCode());
				Report.setReportPassStep(rsp.getBody().asPrettyString());
			}
			//testContext.context.clear();
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Then("Response should match with {string} and {string}")
	public void validateCheckinAndCheckoutDates(String checkIn, String checkOut) {
		try {
			Response response 				= 	testContext.getResponse();
			//String responseBody 			= 	response.asString();
			if(response != null) {
				String checkin				=	"";
				String checkout				=	"";
				
				try {
					checkin					=	testUtils.getJsonValue(response, "checkin");
					checkout				=	testUtils.getJsonValue(response, "checkout");
				}catch(JsonPathException jpe) {
				}
				if(checkin.trim().equalsIgnoreCase(checkIn)) {
					Report.setReportFailStep("checkIn validated successfully : "+checkin);
				}else {
					Report.setReportFailStep("checkIn is incorrectly displayed : Expected : "+testContext.getContext("checkin")+", Recieved : "+checkin);
				}
				
				if(checkOut.trim().equalsIgnoreCase(checkout)) {
					Report.setReportFailStep("checkout validated successfully : "+checkOut);
				}else {
					Report.setReportFailStep("checkout is incorrectly displayed : Expected : "+testContext.getContext("checkout")+", Recieved : "+checkout);
				}
			}else {
				Report.setReportFailStep("Null Reposnse received ");
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
