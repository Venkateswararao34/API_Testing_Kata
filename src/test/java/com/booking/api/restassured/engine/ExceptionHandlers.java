package com.booking.api.restassured.engine;

import java.nio.charset.Charset;
import org.testng.*;

public class ExceptionHandlers {
	
	public static void PreExceptionHandler(String exception) {
		try {
			Charset UTF8_CHARSET			=	Charset.forName("UTF-8");
			Charset USACII_CHARSET			=	Charset.forName("ISO-8859-1");
			org.testng.Reporter.log(new String(exception.getBytes(UTF8_CHARSET), USACII_CHARSET));
			System.exit(1);
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
