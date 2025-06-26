package com.booking.api.restassured.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

public class TestContext {
	@Getter
	@Setter
	public static Map<String,String> context;
	
	
	public static int roomId;
	public static String token;
	public static String basepath;
	@Getter
	@Setter
	public int bookingId;
	
	public static Response response;
	
	/*
	 * 
	 * Constructor initalization
	 */
	public TestContext() {
		this.context			=	new HashMap<String,String> ();
	}
	/*
	 * Setting key value pair with api required parameters
	 */
	public void setContext(String apiKeyName, String apiKeyValue) {
		this.context.put(apiKeyName, apiKeyValue);
	}
	
	public String getContext(String reqKey) {
		String reqkeyValue		=	"";
		try {
			reqkeyValue			=	this.context.get(reqKey);
		}catch(NoSuchElementException nsee) {
			
		}catch(NullPointerException npe) {
			
		}
		return reqkeyValue;
	}
	public void clearContext() {
		this.context.clear();
	}
	
	public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    public void setRoomId(int roomId) {
    	this.roomId	=	roomId;
    }
    public int getRoomId() {
    	return roomId;
    }
    public void setToken(String token) {
    	this.token	=	token;
    }
    public String getToken() {
    	return token;
    }
    public void setBasepath(String path) {
    	this.basepath	=	path;
    }
    public String getBasepath() {
    	return basepath;
    }
}
