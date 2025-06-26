package com.booking.api.restassured.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.Reporter;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
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
	
	private void processCreateBooking(DataTable dataTable) {
		try {
			LoadEnvironmentProperties loadProperties=	new LoadEnvironmentProperties();
			Reporter Report							=	new Reporter();
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
														.body(reqPayload)
														.when().post(testContext.getContext("basePath"));

				testContext.setResponse(rsp);
				System.out.println("Received Status Code : "+rsp.getStatusCode());
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
	
	@Then("Response should match with {string} and {string}")
	public void validateCheckinAndCheckoutDates(String checkIn, String checkOut) {
		try {
			Response response 			= 	testContext.getResponse();
			//String responseBody 		= 	response.asString();
			if(response != null) {
				String checkin				=	testUtils.getJsonValue(response, "checkin");
				String checkout				=	testUtils.getJsonValue(response, "checkout");
				
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
				Report.setReportFailStep("cNull Reposnse received ");
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
