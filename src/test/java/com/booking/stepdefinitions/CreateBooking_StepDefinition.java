package com.booking.stepdefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.Reporter;

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
	
	public TestContext testContext;
	public TestUtils testUtils;
	public Config config;
	LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
	Reporter Report									=	new Reporter();
	
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
	
	@Then("Validate status code is {int}")
	public void validateStatusCode(Integer code) {
		//assertEquals(code, testContext.getResponse().getStatusCode(), "Unexpected status code");
		int responseCode			=	testContext.getResponse().getStatusCode();
		if(code == responseCode) {
			Report.setReportFailStep("Failure Response Recieved with Status code : "+responseCode);
			Report.setReportPassStep(testContext.response.getBody().asPrettyString());
		}
	}
	
	@Then("The response should contain valid details")
	public void validateCreateBookingResponse() {
		Response response = testContext.getResponse();
		
		String firstName			=	testContext.getContext("firstname");
		String lastName				=	testContext.getContext("lastname");
		String email				=	testContext.getContext("email");
		String phone				=	testContext.getContext("phone");
		String depositpaid			=	testContext.getContext("depositpaid");
		String checkin				=	testContext.getContext("checkin");
		String checkout				=	testContext.getContext("checkout");
		String roomId				=	testUtils.getJsonValue(response, "roomid");
		String bookingid			=	testUtils.getJsonValue(response, "bookingid");
		
	}
}
