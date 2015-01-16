package com.akshay.weatherapp.utils;

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
}
