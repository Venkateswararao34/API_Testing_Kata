package com.booking.api.restassured.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.Reporter;
import com.booking.api.restassured.engine.TestContext;
import com.booking.api.restassured.engine.TestUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Authentication_StepDefinition {
	public TestContext testContext 					=	new TestContext();
	public TestUtils testUtils						=	new TestUtils();
	public Config config							=	new Config();
	
	LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
	Reporter Report									=	new Reporter();
	
	/*
	 * This step definition get the api name from the Feature file
	 * 
	 */
	
	@When("User should send auth details from property file")
	public void GetAndSetRoomId() {
		try {
			String username				=	LoadEnvironmentProperties.EnvironmentMap.get("BASIC_AUTH_USERNAME");
			String password				=	LoadEnvironmentProperties.EnvironmentMap.get("BASIC_AUTH_PASSWORD");
			processAuthenticationrequest(username, password);
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This Method is for Processing Authentication request using Post method
	 * 
	 */
	private void processAuthenticationrequest(String username, String password) {
		try {
			LoadEnvironmentProperties loadProperties=	new LoadEnvironmentProperties();
			Reporter Report							=	new Reporter();
			RequestSpecification requestSpec = new RequestSpecBuilder()
		            .setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
		            .addHeader("Content-Type", "application/json")
		            .addHeader("Accept", "*/*")
		            .build();

			int roomid							=	testContext.getRoomId();
			
			
			HashMap<String,String> AUTH_MAP		=	new HashMap<String,String>();
			AUTH_MAP.put("username", username);
			AUTH_MAP.put("password", password);
			

			Response rsp 						= 	given(requestSpec)
													.log().all()
													.when()
													.body(AUTH_MAP)
													.post(testContext.getBasepath());

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
	@Then("Validate token details")
	public void validateCheckinAndCheckoutDates() {
		try {
			Response response 			= 	testContext.getResponse();
			
			if(response != null) {
				String token				=	testUtils.getJsonValue(response, "token");
				
				if(token != null && token.trim().length() != 0) {
					Report.setReportFailStep("token validated successfully : "+token);
				}else {
					Report.setReportFailStep("token is incorrectly displayed : Expected :  Minimum length of 10 , Recieved : "+token);
				}
				
			}else {
				Report.setReportFailStep("Null Reposnse received");
			}
		}catch(JsonPathException jpe) {
			jpe.printStackTrace();
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
