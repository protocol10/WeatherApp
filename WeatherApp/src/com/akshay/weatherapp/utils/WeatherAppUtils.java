package com.akshay.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.widget.Toast;

public class WeatherAppUtils {

	/**
	 * Common method to display messages to user
	 * 
	 * @param context
	 * @param messaage
	 */
	public static void showMessage(Context context, String messaage) {
		Toast.makeText(context, messaage, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Method to convert unix time stamp to readable date
	 * 
	 * @param time
	 * @return
	 */
	public static String retrieveDate(long time) {
		long timeStamp = time * 1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy",
				Locale.getDefault());

		return dateFormat.format(timeStamp).toString();
	}
}
