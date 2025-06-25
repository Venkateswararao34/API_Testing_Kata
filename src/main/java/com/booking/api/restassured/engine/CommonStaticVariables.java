package com.booking.api.restassured.engine;

import java.io.File;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.reusables.Reusables;
import com.relevantcodes.extentreports.ExtentReports;

/*
 * This class helps to initiate reporting name in required format
 * 
 */
public class CommonStaticVariables extends Config{
	
	public static String REPORTING_PARENT_FOLDER 					=	Config.getWorkingDirectory()+File.separator+"Report"+File.separator;
	public static String REPORT_START_TIME							=	Reusables.getDateFormat("dd_mm_yy_hh_mm_ss", 0);
	public static String DEFAULT_REPORT_NAME						=	"AUTOMATION_REST_ASSURED_";
	public static String CURRENT_REPORTING_LOCATION					=	REPORTING_PARENT_FOLDER + DEFAULT_REPORT_NAME + REPORT_START_TIME;
	public static ExtentReports EXTENTTESTREPORT					=	null;
	
}
