package com.booking.api.restassured.testrunner;

import org.junit.platform.suite.api.*;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.engine.CommonStaticVariables;
import com.booking.api.restassured.engine.ExceptionHandlers;
import com.booking.api.restassured.engine.Reporter;
import com.booking.api.restassured.engine.RestSetup;
import com.booking.api.restassured.logger.Logger;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

import java.io.File;

@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"com.booking.api.restassured.stepdefinitions"},
	    plugin = {
	        "pretty",
	        "html:target/cucumber-reports/cucumber.html",
	        "json:target/cucumber-reports/cucumber.json"
	    }
	)
public class TestRunner extends AbstractTestNGCucumberTests{
	public String ENVIRONMENT		=	"UAT";
	 @BeforeSuite
	 public void globalSetup() {
	        RestSetup restSetup			=	new RestSetup();
	        restSetup.InitializeSetup(ENVIRONMENT);
	 }
	 
	 @AfterSuite
	 public void endGlobalSetup() {
		 RestSetup restSetup			=	new RestSetup();
		 restSetup.endTest();
	 }
}