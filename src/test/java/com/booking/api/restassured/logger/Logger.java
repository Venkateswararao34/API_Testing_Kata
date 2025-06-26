package com.booking.api.restassured.logger;

public class Logger {
	public boolean CONSOLE_STATUS		=	false;
	public boolean ERROR_STATUS			=	false;
	
	public void LogMsgToConsole(String logMsg) {
		try {
			if(CONSOLE_STATUS) {
				System.out.println(logMsg);
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception npe) {
			npe.printStackTrace();
		}
	}
	public void LogErrorToConsole(String errMsg) {
		try {
			if(CONSOLE_STATUS) {
				System.out.println(errMsg);
			}
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception npe) {
			npe.printStackTrace();
		}
	}
}
