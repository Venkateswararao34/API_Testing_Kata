package com.booking.api.restassured.config;

import java.io.File;

import com.booking.api.restassured.config.EnumConstants.API_BASE_URL;

public class Config {
	/*
	 * Config class contains all the system configuration settings
	 * Created By : Venkateswararao Machavarapu
	 * Created On : 24/06/2025
	 */
	public static String WORKING_DIRECTORY			=	"";
	public static String PROPERTY_FILE_PATH			=	"";
	public static String EXECUTION_ENVIRONMENT_NAME	=	"";
	public static String REPORT_NAME				=	"";
	public static String REPORT_APPEND				=	"";
	public static String API_END_POINT				=	"";
	
	/*
	 * This method sets the current working directory path
	 * 
	 */
	public static void setWorkingDirectory() {
		try {
			WORKING_DIRECTORY						=	System.getProperty("user.dir");
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	/*
	 * This method sets the current execution environment
	 * 
	 */
	public static void setExecutionEnvironment(String ENV) {
		try {
			EXECUTION_ENVIRONMENT_NAME				=	ENV;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	/*
	 * This method sets the current execution environment property file path
	 * 
	 */
	public static void setPropertyFilePath() {
		try {
			PROPERTY_FILE_PATH						=	"PropertyFiles"+File.separator+"EnvironmentProperties"+File.separator;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	/*
	 * This method sets the current execution report name
	 * 
	 */
	public static void setReportName(String reportName) {
		try {
			REPORT_NAME								=	reportName;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	/*
	 * This method sets prefixed report name
	 * 
	 */
	public static void setReportAppend(String reportAppend) {
		try {
			REPORT_APPEND								=	reportAppend;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	/*
	 * This method gets the current execution directory
	 * 
	 */
	public static String getWorkingDirectory() {
		return WORKING_DIRECTORY;
	}
	/*
	 * This method gets the current execution file path
	 * 
	 */
	public static String getPropertyFilePath() {
		return PROPERTY_FILE_PATH;
	}
	/*
	 * This method gets the current execution environment
	 * 
	 */
	public static String getExecutionEnvironment() {
		return EXECUTION_ENVIRONMENT_NAME;
	}
	/*
	 * This method gets the current execution report name
	 * 
	 */
	public static String getReportName() {
		return REPORT_NAME;
	}
	public static String getReportAppend() {
		return REPORT_APPEND;
	}
	
	public void setAPIEndPoint(String endpoint) {
		API_END_POINT			=	endpoint;
	}
	
	public String getAPIResourceEndPointURL(String apiName, String id) {
		String requireEndPoint			=	"";
		try {
			//LoadEnvironmentProperties loadProperties		=	new LoadEnvironmentProperties();
			setAPIEndPoint(LoadEnvironmentProperties.EnvironmentMap.get("APPLICATION_URL"));
			requireEndPoint									=	API_END_POINT+getAPIEndPointToBeAppended(API_BASE_URL.valueOf(apiName), id);
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return requireEndPoint;
	}
	public String getAPIEndPointToBeAppended(API_BASE_URL apiName, String id) {
		String requireEndPoint			=	"";
		try {
			switch(apiName) {
			case GetAuthTokenAPI:
				requireEndPoint			=	 "/api/auth/login";
				break;
			case CreateBookingAPI:
				requireEndPoint			=	"/api/booking";
				break;
			case GetBookingSummaryAPI:
				requireEndPoint			=	"/api/booking/summary/";
				break;
			case GetByRoomIdAPI:
				requireEndPoint			=	"/api/booking/"+id;
				break;
			case GetByBookingIdAPI:
				requireEndPoint			=	"/api/booking/"+id;
				break;
			case UpdateBookingAPI:
				requireEndPoint			=	"/api/booking/"+id;
				break;
			case DeleteBookingAPI:
				requireEndPoint			=	"/api/booking/"+id;
				break;
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return requireEndPoint;
	}
}
