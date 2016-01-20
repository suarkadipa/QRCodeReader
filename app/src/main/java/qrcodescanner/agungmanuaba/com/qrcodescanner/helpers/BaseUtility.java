package qrcodescanner.agungmanuaba.com.qrcodescanner.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Spinner;
/**
 * Helper Utility which provide method to convert value safely
 *
 */
public class BaseUtility {
	
	/**
	 * Convert Date to String
	 * Return string empty when date = null
	 * Return date base on date format when not null
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date, String format) {
		if (date == null) {
			return "";
		}else {
			return DateFormat.format(format, date).toString();
		}
	}
	
	/**
	 * Convert String to Date
	 * Parse dateString using Simple Date Format based on Date Format
	 * @param dateString
	 * @return
	 */
	public static Date convertStringToDate(String dateString, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());	 
		try {
			if (isInvalid(dateString)) {
				return null;
			}else {
				dateString = formatDate(dateString);
				Date date = formatter.parse(dateString);
				return date;
			}			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date convertStringToDateLocal(String dateString, String format) {
		try {
			if (isInvalid(dateString)) {
				return null;
			} else {
				SimpleDateFormat sdfUTC	=	new SimpleDateFormat(format);
				sdfUTC.setTimeZone(TimeZone.getTimeZone(Constants.UTC));
				Date dateUTC	=	sdfUTC.parse(dateString);
				Log.d("UTC Date", sdfUTC.format(dateUTC));
				
				SimpleDateFormat sdfLocal	=	new SimpleDateFormat(format);
				sdfLocal.setTimeZone(TimeZone.getDefault());
				
				String dateLocalString	=	sdfLocal.format(dateUTC);
				Log.d("Local Date", sdfLocal.format(dateLocalString));
				return sdfLocal.parse(dateLocalString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date convertStringToDateUTC(String dateString, String format) {
		try {
			if (isInvalid(dateString)) {
				return null;
			} else {
				SimpleDateFormat sdfLocal	=	new SimpleDateFormat(format);
				sdfLocal.setTimeZone(TimeZone.getDefault());
				Date dateLocal	=	sdfLocal.parse(dateString);
				
				SimpleDateFormat sdfUTC	=	new SimpleDateFormat(format);
				sdfUTC.setTimeZone(TimeZone.getTimeZone(Constants.UTC));
				
				String dateUTCString	=	sdfUTC.format(dateLocal);
				
				return sdfUTC.parse(dateUTCString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Convert Time to String
	 * @param date
	 * @return string time based on Time Format
	 */
	public static String convertTimeToString(Date date, String format) {
		if (date == null) {
			return "";
		}else {
			return DateFormat.format(format, date).toString();
		}
	}
	
	/**
	 * Convert String to Time
	 * @param dateString
	 * @return Parse time using Simple Date Format based on Time Format
	 */
	public static Date convertStringToTime(String dateString, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());	 
		try {
			if (isInvalid(dateString)) {
				return null;
			}else {
				Date date = formatter.parse(dateString);
				return date;
			}			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertStringToTimeLocal(String timeString, String format) {
		try {
			if (isInvalid(timeString)) {
				return null;
			} else {
				SimpleDateFormat sdfUTC	=	new SimpleDateFormat(format);
				sdfUTC.setTimeZone(TimeZone.getTimeZone(Constants.UTC));
				Date timeUTC	=	sdfUTC.parse(timeString);
//				Log.d("UTC Time", sdfUTC.format(timeUTC));
				
				SimpleDateFormat sdfLocal	=	new SimpleDateFormat(format);
				sdfLocal.setTimeZone(TimeZone.getDefault());
				
				return sdfLocal.format(timeUTC);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertStringToDateStringUTC(String dateString, String format) {
		try {
			if (isInvalid(dateString)) {
				return null;
			} else {
				SimpleDateFormat sdfLocal	=	new SimpleDateFormat(format);
				sdfLocal.setTimeZone(TimeZone.getDefault());
				Date timeLocal	=	sdfLocal.parse(dateString);
//				Log.d("local Time", sdfLocal.format(timeLocal));
				
				SimpleDateFormat sdfUTC	=	new SimpleDateFormat(format);
				sdfUTC.setTimeZone(TimeZone.getTimeZone(Constants.UTC));
				String result = sdfUTC.format(timeLocal);
				return result;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parse String to Integer
	 * @param num
	 * @return
	 */
	public static Integer toInteger(String num) {
		if (isInvalid(num)) {
			return 0;
		}else {
			return Integer.parseInt(num);
		}
	}
	
	/**
	 * Check if sent string data is invalid
	 * @param data
	 * @return
	 */
	private static boolean isInvalid(String data) {
		return (data == null || data.equalsIgnoreCase(Constants.STRING_NULL) || data.isEmpty());
	}

	/**
	 * Parse String to Double
	 * @param num
	 * @return
	 */
	public static Double toDouble(String num) {
		if (isInvalid(num)) {
			return 0.0;
		}else {
			return Double.parseDouble(num);
		}
	}
	
	/**
	 * Parse String to Float
	 * @param num
	 * @return
	 */
	public static Float toFloat(String num) {
		if (isInvalid(num)) {
			return 0f;
		}else {
			return Float.parseFloat(num);
		}
	}
	
	/**
	 * Parse String to Boolean
	 * @param bool
	 * @return
	 */
	public static Boolean toBoolean(String bool){
		if (bool.contentEquals(Constants.STRING_FALSE) || bool.equalsIgnoreCase(Constants.STRING_TRUE)) {
			return Boolean.parseBoolean(bool);
		}else {
			return null;
		}
	}
	
	/**
	 * Parse integer to String
	 * @param num
	 * @return
	 */
	public static String toString(int num) {
		return Integer.toString(num);
	}
	
	/**
	 * parse boolean to String
	 * @param bool
	 * @return
	 */
	public static String toString(boolean bool) {
		return Boolean.toString(bool);
	}
	
	/**
	 * Parse Double to String
	 * @param num
	 * @return
	 */
	public static String toString(double num) {
		return Double.toString(num);
	}
	
	/**
	 * Get Value
	 * @param data
	 * @return string empty when data = null
	 */
	public static String getValue(String data) {
		if (isInvalid(data)) {
			return "";
		}else {
			return data;
		}
	}
	
	/**
	 * Get Integer Value
	 * @param data
	 * @return 0 when data = null
	 */
	public static Integer getIntValue(Integer data) {
		if (data == null || data.equals(Constants.STRING_NULL)) {
			return 0;
		}
		else {
			return data;
		}
	}
	
	/**
	 * Get Double Value
	 * @param data
	 * @return 0 when data = null
	 */
	public static Double getDoubleValue(Double data){
		if (data == null || data.equals(Constants.STRING_NULL)) {
			return 0.0;
		}
		else {
			return data;
		}
	}

	public static String convertJSONDate(String rawJSONDate, String format) {
		try {
			String dateString = formatStringDate(rawJSONDate);
			long timeinmilliseconds = Long.parseLong(dateString);

			return convertMilisToDate(timeinmilliseconds, format);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date convertJSONDate(String rawJSONDate) {
		try {
			String dateString = formatStringDate(rawJSONDate);	
			long timeinmilliseconds = Long.parseLong(dateString);
	        return new Date(timeinmilliseconds);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date convertJSONDateToDateUTC(String rawJSONDate) {
		try {
			String dateFormat = Constants.TIME_FORMAT;
			
			SimpleDateFormat sdf	=	new SimpleDateFormat(dateFormat);
			sdf.setTimeZone(TimeZone.getTimeZone(Constants.UTC));
			
			Date completedDate = BaseUtility
					.convertJSONDate(rawJSONDate);
			String completedDateString = BaseUtility.convertDateToString(
					completedDate, dateFormat);
			
			Date dateUTC	=	sdf.parse(completedDateString);
//			Log.d("UTC date", sdf.format(dateUTC));
			
			SimpleDateFormat sdfLocal	=	new SimpleDateFormat(dateFormat, Locale.getDefault());
			sdfLocal.setTimeZone(TimeZone.getDefault());
			String dateLocalString	=	sdfLocal.format(dateUTC);
//			Log.d("GMT+7 date", sdfLocal.format(dateUTC));
			return sdfLocal.parse(dateLocalString);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @param rawJSONDate
	 * @return
	 */
	public static Date convertJSONDateToDate(String rawJSONDate) {
		try {
			String dateFormat = Constants.TIME_FORMAT;
			
			SimpleDateFormat sdf	=	new SimpleDateFormat(dateFormat, Locale.getDefault());
			return sdf.parse(rawJSONDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String convertJSONDateToLongString(String date) {
		try {
			long dateInMiliseconds = convertJSONDateToLong(date);
			return Long.toString(dateInMiliseconds);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static Long convertJSONDateToLong(String date) {
		try {
			date = formatStringDate(date);
			long dateInMiliseconds = Long.parseLong(date);
			return dateInMiliseconds;
		} catch (Exception e) {
			return null;
		}
	}
	
	private static String formatStringDate(String date){
		date = date.replace("/Date(", "").replace(")/", "");
		String[] dateParts = date.split("[+-]");
		return dateParts[0];
	}
	

	/**
	 * Ensure date string is in correct date format
	 * @param dateString
	 * @return
	 */
	private static String formatDate(String dateString) {
		String newDate = dateString.replace("/Date(", "");
		newDate = newDate.replace(")/", "");
		newDate = newDate.replace("/", "-");
		return newDate;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String convertMilisToDate(long milliSeconds, String dateFormat)
	{
	    // Create a DateFormatter object for displaying date in specified format.
	    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

	    // Create a calendar object that will convert the date and time value in milliseconds to date. 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return formatter.format(calendar.getTime());
	}
	
	/**
	 * Get Selected Spinner Item position.
	 *
	 * @param spin the spin
	 * @param id the id
	 * @return the selected position
	 */
	public static int getSelectedPosition(Spinner spin, String id) {
		int position = 0;
		if (spin == null || id == null || id.isEmpty()) {
			return position;
		}
		return position;
	}
}
