package com.booking.stepdefinitions;

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
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingSummary_StepDefinition {
	public TestContext testContext;
	public TestUtils testUtils;
	public Config config;
	
	/*
	 * This step definition get the api name from the Feature file
	 * 
	 */
	@Given("User should able to access create booking API {string}")
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
	
	@When("User book the hotel room with given details")
	public void createValidBooking(DataTable dataTable) {
		processCreateBooking(dataTable);
	}
	
	private void processCreateBooking(DataTable dataTable) {
		try {
			LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
			Reporter Report									=	new Reporter();
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
				
				
				Response rsp 						= 	given(requestSpec)
														.body(testUtils.generateCreateBookingRequest(dataMap, randomId))
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
		    
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
