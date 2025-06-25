package com.booking.stepdefinitions;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

public class TestContext {
	@Getter
	@Setter
	public Map<String,String> context;
	
	@Getter
	@Setter
	public int roomId;
	
	@Getter
	@Setter
	public int bookingId;
	
	public Response response;
	
	/*
	 * 
	 * Constructor initalization
	 */
	public TestContext() {
		context			=	new HashMap<String,String> ();
	}
	/*
	 * Setting key value pair with api required parameters
	 */
	public void setContext(String apiKeyName, String apiKeyValue) {
		context.put(apiKeyName, apiKeyValue);
	}
	
	public String getContext(String reqKey) {
		String reqkeyValue		=	"";
		try {
			reqkeyValue			=	context.get(reqkeyValue);
		}catch(NoSuchElementException nsee) {
			
		}catch(NullPointerException npe) {
			
		}
		return reqkeyValue;
	}
	public void clearContext() {
		context.clear();
	}
	
	public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    
    
}
