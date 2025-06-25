package com.booking.api.restassured.engine;

import java.nio.charset.Charset;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*
 * This class helps to Report the test step in required format
 * For Example: If the Test step is passed : Green
 * 				If the Test step is failed: Red etc..
 * Created By : Venky
 */
public class Reporter {
	
	ExtentReports EXTENTTESTREPORT;
	public ExtentTest EXTENTTEST;
	public String REPORT_NAME;
	public String CURRENT_ROW_OF_EXECUTION;
	public int EXECUTION_OFFSET								=	0;
	public boolean TESTPASSED								=	true;
	public int REPORTING_ROW								=	0;
	public static boolean EXECUTION_STATUS					=	false;
	
	/*
	 * This method sets the execution status
	 * 
	 */
	public void setExecutionStatus() {
		try {
			EXECUTION_STATUS				=	TESTPASSED;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * This method helps to get the current Execution status
	 */
	public boolean getExecutionStatus() {
		return EXECUTION_STATUS;
	}
	/*
	 * This method helps to report the execution message or test step for test step passed
	 */
	public void setReportPassStep(String REPORTING_MESSAGE) {
		ReporterLog(REPORTING_MESSAGE.replaceAll("/", "_"), LogStatus.PASS);
	}
	/*
	 * This method helps to report the execution message or test step for test step failed
	 */
	public void setReportFailStep(String REPORTING_MESSAGE) {
		TESTPASSED		=	false;
		ReporterLog(REPORTING_MESSAGE.replaceAll("/", "_"), LogStatus.FAIL);
	}
	/*
	 * This method helps to report the execution message or test step for test step warning
	 */
	public void setReportWarningStep(String REPORTING_MESSAGE) {
		ReporterLog(REPORTING_MESSAGE.replaceAll("/", "_"), LogStatus.WARNING);
	}
	/*
	 * This method helps to report the execution message or test step for test step info
	 */
	public void setReportInfoStep(String REPORTING_MESSAGE) {
		ReporterLog(REPORTING_MESSAGE.replaceAll("/", "_"), LogStatus.INFO);
	}
	/*
	 * This method helps to set color of your test step in html report
	 */
	public void setReportingColor(String REPORTING_MESSAGE, LogStatus LS) {
		try {
			String COLOR					=	"";
			switch(LS) {
			case ERROR:
				break;
			case FAIL:
				COLOR						=	"#ff0000";
				break;
			case INFO:
				COLOR						=	"#800000";
				break;
			case PASS:
				COLOR						=	"#008000";
				break;
			case SKIP:
				break;
			case UNKNOWN:
				break;
			case WARNING:
				COLOR						=	"#ffa700";
				break;
			default:
				break;
			}
			
			ReporterLog(REPORTING_MESSAGE.replaceAll("/", "_"), LS);
			
		}catch(NumberFormatException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * This method helps to write test step to html report
	 */
	public void ReporterLog(String STEP_STATUS, LogStatus LS) {
		try {
			if(LS.name().equalsIgnoreCase("FAIL")) {
				TESTPASSED			=	false;
			}
			Charset UTF8_CHARSET	=	Charset.forName("UTF-8");
			Charset USASCII_CHARSET	=	Charset.forName("ISO-8859-1");
			EXTENTTEST.log(LS, STEP_STATUS);
			String TO_REPORT_DATA	=	"[ Reported ] " + new String((LS.name() + "\t" + STEP_STATUS).getBytes(UTF8_CHARSET), USASCII_CHARSET);
			org.testng.Reporter.log(TO_REPORT_DATA);
			REPORTING_ROW +=	1;
		}catch(NumberFormatException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
