/**
 * 
 */
package com.example.paypro.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author jintu
 * 
 */
public class ApplicationUtil {

	public static Date parseDate(String UTCTime) throws ParseException {
		DateFormat utcFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = utcFormat.parse(UTCTime);
		return date;
	}

	public static String dateToString(Date date) {
		DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));

		System.out.println(pstFormat.format(date));
		return pstFormat.format(date);
	}
}
