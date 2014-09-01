
package org.gw.commons.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * get the elapsed time between the two given dates
	 * 
	 * @param first
	 *            the first date
	 * @param second
	 *            the second date
	 * @return the elapsed time between the two dates
	 */
	public static long elapsedTimeInSeconds(Date first, Date second) {
		long elapsedTime = (second.getTime() - first.getTime());
        long elapsedTimeSecs = elapsedTime / 1000;
		return elapsedTimeSecs;
	}

	public static long secondsUntil(Date date) {

		long millisecondsRemaining = date.getTime() - (new Date()).getTime();
		if (millisecondsRemaining > 0) {
			long secondsRemaining = millisecondsRemaining / 1000;
			return secondsRemaining;

		} else {
			return 0;
		}
	}

	/**
	 * Returns the given {@link java.util.Date} rounded down to the minute 13:43:58.456
	 * becomes 13:43:00.000.
	 * 
	 * @param date
	 *            The {@link java.util.Date} to round.
	 * @return The given {@link java.util.Date} rounded down to the minute.
	 */
	public static Date roundDownToMinute(Date date) {
		long millis = date.getTime();
		long rounded = millis / 60000 * 60000;
		return new Date(rounded);
	}

	/**
	 * Returns the given {@link java.util.Date} rounded up to the minute 13:43:58.456
	 * becomes 13:44:00.000.
	 * 
	 * @param date
	 *            The {@link java.util.Date} to round.
	 * @return The given {@link java.util.Date} rounded up to the minute.
	 */
	public static Date roundUpToMinute(Date date) {
		long millis = date.getTime();
		long rounded = millis / 60000 * 60000;
		rounded += 60000;
		return new Date(rounded);
	}

	/**
	 * Returns the given {@link java.util.Date} rounded down to the minute 13:43:58.456
	 * becomes 13:43:58.000.
	 * 
	 * @param date
	 *            The {@link java.util.Date} to round.
	 * @return The given {@link java.util.Date} rounded down to the minute.
	 */
	public static Date roundDownToSecond(Date date) {
		long millis = date.getTime();
		long rounded = millis / 1000 * 1000;
		return new Date(rounded);
	}

	/**
	 * Returns the given {@link java.util.Date} rounded up to the minute 13:43:58.456
	 * becomes 13:43:59.000.
	 * 
	 * @param date
	 *            The {@link java.util.Date} to round.
	 * @return The given {@link java.util.Date} rounded up to the minute.
	 */
	public static Date roundUpToSecond(Date date) {
		long millis = date.getTime();
		long rounded = millis / 1000 * 1000;
		rounded += 1000;
		return new Date(rounded);
	}

	/**
	 * convert a Date to seconds since epoch
	 * 
	 * @date the date to convert
	 * @return a long representing the number of seconds since epoch represented
	 *         by the date
	 */
	public static long dateToSecondsSinceEpoch(Date date) {
		// rounds down
		long secondsSinceEpoch = date.getTime() / 1000;
		return secondsSinceEpoch;
	}
	
	/**
	 * zero the HH:MM:ss:SSS fields of the given date
	 * 
	 * @param date
	 * @return a copy of the given date with the time of day fields zeroed
	 */
	public synchronized static Date zeroTimeOfDay(Date date) {

		Date zeroedDate = null;
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		zeroedDate = cal.getTime();
		
		return zeroedDate;
	}
}
