package com.booking.api.restassured.reusables;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/*
 * 
 * This Reusable class contains all the resuable methods
 */

public class Reusables {
	/*
	 * This method returns the date in required format eg: dd_mm_yy or any other format
	 */
	public static String getDateFormat(String DATE_FORMAT, int NUMBER_OF_DAYS) {
		String FORMATTED_DATE					=	"";
		try {
			Date date							=	new Date();
			SimpleDateFormat sdf				=	new SimpleDateFormat();
			Calendar calender					=	Calendar.getInstance();
			calender.setTime(date);
			calender.add(Calendar.DATE, NUMBER_OF_DAYS);
		}catch(NullPointerException npe) {
			npe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return FORMATTED_DATE;
	}
}
