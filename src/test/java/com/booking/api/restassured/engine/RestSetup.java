package com.booking.api.restassured.engine;

import java.io.File;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.booking.api.restassured.config.Config;
import com.booking.api.restassured.config.LoadEnvironmentProperties;
import com.booking.api.restassured.logger.Logger;
import com.booking.api.restassured.testrunner.TestRunner;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.DisplayOrder;

/*
 * 
 * This is the first class which executes first when ever test case is executed
 */
public class RestSetup{
	
	public static Reporter Report				=	new Reporter();
	public String MODE_OF_EXECUTION				=	"";
	public String TEST_CASE_NAME				=	"";
	public int ROW								=	0;
	
	
	
	/*This method executes before every test in order load the pre-requisities like
	 *  i) Environment to be executed
	 *  ii) Where do you want to execute like Local or Node
	 *  iii) What should be the reporting file name like
	 *  iv) Which language you want to select on the webpage
	 */
	
	@BeforeSuite
	public void setupBeforeTestSuite(ITestContext context) {
		
		//Object Declaration
		Config config							=	new Config();
		Logger log								=	new Logger();
		LoadEnvironmentProperties loadEnvProps	=	new LoadEnvironmentProperties();
		
		String ENVIRONMENT						=	context.getCurrentXmlTest().getParameter("environment");
		String REPORT_NAME						=	context.getCurrentXmlTest().getParameter("reportName");
		String REPORT_APPEND					=	context.getCurrentXmlTest().getParameter("reportAppend");
		MODE_OF_EXECUTION						=	context.getCurrentXmlTest().getParameter("modeOfExecution");
		TEST_CASE_NAME							=	context.getCurrentXmlTest().getParameter("testCaseName");
		REPORT_NAME								=	TEST_CASE_NAME+ "_" +CommonStaticVariables.REPORT_START_TIME;
		
		
		/*
		 * The  below block sets all the pre-requisite received from testNG xml
		 */
		if(ENVIRONMENT == null) {
			ExceptionHandlers.PreExceptionHandler("Please provide environment details in testng xml");
		}else {
			config.setWorkingDirectory();
			config.setPropertyFilePath();
			config.setExecutionEnvironment(ENVIRONMENT);
			config.setReportName(REPORT_NAME);
			config.setReportAppend(REPORT_APPEND);
			loadEnvProps.loadPropertiesFromFile();
		}
		/*
		 * The  below block Creates Output folder if it doesnt exists
		 */
		File reportlocation								=	new File(CommonStaticVariables.CURRENT_REPORTING_LOCATION);
		if(!reportlocation.exists()) {
			if(reportlocation.mkdir()) {
				log.LogMsgToConsole("Successfully created Reporting directory");
			}else {
				log.LogMsgToConsole("Unable to create Reporting directory");
			}
		}
		PreProcesing(REPORT_NAME, Integer.toString(ROW));
		/*
		 * The  below block appends new test case to the existing report
		 */
		if(REPORT_NAME != null && REPORT_APPEND.trim().equalsIgnoreCase("TRUE")) {
			CommonStaticVariables.EXTENTTESTREPORT			=	new ExtentReports(
					CommonStaticVariables.CURRENT_REPORTING_LOCATION + "/" +REPORT_NAME+ ".html", false, DisplayOrder.NEWEST_FIRST);
		}
	}
	
	@AfterMethod
	public void teradown(){
		try {
			Config config							=	new Config();
			if(Report.TESTPASSED) {
				Report.ReporterLog("TEST PASSED", LogStatus.PASS);
			}else {
				Report.ReporterLog("TEST FAILED", LogStatus.FAIL);
			}
			if(config.getReportName() != null && config.getReportAppend().equalsIgnoreCase("TRUE")) {
				Report.EXTENTTESTREPORT.endTest(Report.EXTENTTEST);
				Report.EXTENTTESTREPORT.flush();
			}else {
				Report.EXTENTTESTREPORT.endTest(Report.EXTENTTEST);
				Report.EXTENTTESTREPORT.flush();
				Report.EXTENTTESTREPORT.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@AfterSuite
	public void CloseReports() {
		Config config			=	new Config();
		if(config.getReportAppend() != null && config.getReportAppend().equalsIgnoreCase("TRUE")) {
			Report.EXTENTTESTREPORT.close();
		}
	}
	public void endTest() {
		try {
			Config config							=	new Config();
			if(Report.TESTPASSED) {
				Report.ReporterLog("TEST PASSED", LogStatus.PASS);
			}else {
				Report.ReporterLog("TEST FAILED", LogStatus.FAIL);
			}
			if(config.getReportName() != null && config.getReportAppend().equalsIgnoreCase("TRUE")) {
				Report.EXTENTTESTREPORT.endTest(Report.EXTENTTEST);
				Report.EXTENTTESTREPORT.flush();
			}else {
				Report.EXTENTTESTREPORT.endTest(Report.EXTENTTEST);
				Report.EXTENTTESTREPORT.flush();
				Report.EXTENTTESTREPORT.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void PreProcesing(String scriptId, String ROW) {
		try {
			InitializeExtentReport(scriptId, ROW);
			Report.CURRENT_ROW_OF_EXECUTION	=	ROW;
			Report.ReporterLog("Test case runnning", LogStatus.INFO);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Reporter InitializeExtentReport(String reportName, String ROW) {
		Reporter report							=	null;
		try {
			report								=	new Reporter();
			report.REPORT_NAME					=	reportName;
			Config config						=	new Config();
			CommonStaticVariables CSV			=	new CommonStaticVariables();
			if(config.getReportName() != null && config.getReportAppend().equalsIgnoreCase("TRUE")) {
				CommonStaticVariables.EXTENTTESTREPORT			=	new ExtentReports(
						CommonStaticVariables.CURRENT_REPORTING_LOCATION + "/" +reportName+ ".html", true, DisplayOrder.NEWEST_FIRST);
				report.EXTENTTESTREPORT			=	CommonStaticVariables.EXTENTTESTREPORT;
				System.out.println(CommonStaticVariables.EXTENTTESTREPORT);
				System.out.println(report.EXTENTTESTREPORT);
				
				report.EXTENTTEST				=	report.EXTENTTESTREPORT.startTest(reportName+"_Row_"+ROW + ".html");
			}else {
				report.EXTENTTESTREPORT			=	new ExtentReports(CommonStaticVariables.CURRENT_REPORTING_LOCATION + "/" + reportName + "_Row_" + ROW + ".html", false);
				report.EXTENTTEST				=	report.EXTENTTESTREPORT.startTest(reportName+"_Row_"+ROW, reportName + "_Row_" + ROW);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return report;
	}
	public void InitializeSetup(String ENV) {
		
		System.out.println("Initiallizing setup");
		//Object Declaration
		Config config							=	new Config();
		Logger log								=	new Logger();
		LoadEnvironmentProperties loadEnvProps	=	new LoadEnvironmentProperties();
		config.setWorkingDirectory();
		config.setExecutionEnvironment(ENV);
		config.setPropertyFilePath();
		loadEnvProps.loadPropertiesFromFile();
		
		
		
		
		String ENVIRONMENT						=	loadEnvProps.EnvironmentMap.get("ENVIRONMENT");;
		String REPORT_NAME						=	loadEnvProps.EnvironmentMap.get("REPORT_NAME");;
		String REPORT_APPEND					=	loadEnvProps.EnvironmentMap.get("REPORT_APPEND");;
		MODE_OF_EXECUTION						=	loadEnvProps.EnvironmentMap.get("MODE_OF_EXECUTION");;
		TEST_CASE_NAME							=	loadEnvProps.EnvironmentMap.get("TEST_CASE_NAME");;
		REPORT_NAME								=	TEST_CASE_NAME+ "_" +CommonStaticVariables.REPORT_START_TIME;
		
		
		System.out.println("ENVIRONMENT : "+ENVIRONMENT);
		System.out.println("REPORT_NAME : "+REPORT_NAME);
		System.out.println("REPORT_APPEND : "+REPORT_APPEND);
		System.out.println("MODE_OF_EXECUTION : "+MODE_OF_EXECUTION);
		System.out.println("TEST_CASE_NAME : "+TEST_CASE_NAME);
		System.out.println("REPORT_NAME : "+REPORT_NAME);
		
		/*
		 * The  below block sets all the pre-requisite received from testNG xml
		 */
		if(ENVIRONMENT == null) {
			ExceptionHandlers.PreExceptionHandler("Please provide environment details in testng xml");
		}else {
			config.setReportName(REPORT_NAME);
			config.setReportAppend(REPORT_APPEND);
			System.out.println("Successfully loaded properties");
			
		}
		/*
		 * The  below block Creates Output folder if it doesnt exists
		 */
		File reportlocation								=	new File(CommonStaticVariables.CURRENT_REPORTING_LOCATION);
		if(!reportlocation.exists()) {
			if(reportlocation.mkdir()) {
				log.LogMsgToConsole("Successfully created Reporting directory");
			}else {
				log.LogMsgToConsole("Unable to create Reporting directory");
			}
		}
		PreProcesing(REPORT_NAME, Integer.toString(ROW));
		
	}
}
