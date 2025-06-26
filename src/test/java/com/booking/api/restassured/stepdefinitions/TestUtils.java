package com.booking.api.restassured.stepdefinitions;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.payload.AuthLoginPayload;
import com.booking.api.restassured.payload.CreateBookingRequestPayload;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.reset;

@RequiredArgsConstructor
public class TestUtils {
	
	@Getter
	public RequestSpecification reqSpec;
	
	
	
	public String getJsonValue(Response response, String jsonPath) {
		String requestedValue		=	"";
		try {
			requestedValue	=	response.jsonPath().getString(jsonPath);
			if(requestedValue == null) {
				requestedValue		=	"";
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return requestedValue;
	}
	
	public String getOptionalStringValue(Map<String, String> map, String key) {
		String requestedValue		=	"";
		try {
			requestedValue	=	map.get(key);
			if(requestedValue == null) {
				requestedValue		=	"";
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return requestedValue;
	}
	public String getAuthToken() {
		String token										=	"";
		try {
			LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
			Config config									=	new Config();
			token		=	getJsonValue(
							given().spec(reqSpec)
							.body(new AuthLoginPayload(loadProperties.EnvironmentMap.get("BASIC_AUTH_USERNAME"), 
														loadProperties.EnvironmentMap.get("BASIC_AUTH_PASSWORD")))
							.when()
							.post(config.getAPIResourceEndPointURL("GetAuthTokenAPI", "")), "token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	public void initRequest() {
		try {
			LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
			reset();
	
			reqSpec 					= 	new RequestSpecBuilder()
											.setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
											.setAccept("*/*")
											.setContentType("application/json")
											.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public CreateBookingRequestPayload generateCreateBookingRequest(Map<String, String> dataMap, int randomId) {
		return new CreateBookingRequestPayload(
				randomId, 
				randomId,
				Boolean.parseBoolean(getOptionalStringValue(dataMap, "depositpaid")),
				getOptionalStringValue(dataMap, "firstname"), 
				getOptionalStringValue(dataMap, "lastname"),
				getOptionalStringValue(dataMap, "email"), 
				getOptionalStringValue(dataMap, "phone"),
				getOptionalStringValue(dataMap, "checkin"), 
				getOptionalStringValue(dataMap, "checkout"));
		
		
	}
	public void setContext(Map<String,String> dataMap) {
		TestContext testContext				=	new TestContext();
		for(String key : dataMap.keySet()) {
			testContext.context.put(key, dataMap.get(key));
		}
		
	}
	public AuthLoginPayload generateAuthLoginRequest(String username, String password) {
		return new AuthLoginPayload(
				username, 
				password);
	}
	public void GeneratePostRequestSpecificationBuilderWithToken(String token) {
		LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
		Config config									=	new Config();
		
		reqSpec = new RequestSpecBuilder()
				.setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
				.setAccept("*/*")
				.setContentType("application/json")
				.addCookie("token="+token)
				.build();
	}
	public void GeneratePostRequestSpecificationBuilderWithAuthentication(String username, String password) {
		LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
		Config config									=	new Config();
		
		HashMap<String,String> AUTH_MAP					=	new HashMap<String,String>();
		AUTH_MAP.put("username", username);
		AUTH_MAP.put("password", password);
		
		reqSpec = new RequestSpecBuilder()
				.setBaseUri(loadProperties.EnvironmentMap.get("APPLICATION_URL"))
				.setAccept("*/*")
				.setContentType("application/json")
				.setBody(AUTH_MAP)
				.build();
	}
}
