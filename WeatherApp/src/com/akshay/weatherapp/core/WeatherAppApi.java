package com.akshay.weatherapp.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.akshay.weatherapp.callback.OnGetDailyForeCastCallBack;
import com.akshay.weatherapp.callback.OnGetWeatherCallBack;
import com.akshay.weatherapp.core.models.ForeCast;
import com.akshay.weatherapp.core.models.Weather;
import com.akshay.weatherapp.utils.WeatherAppUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.SyncHttpClient;

public class WeatherAppApi {

	private static AsyncHttpClient client;
	private static SyncHttpClient synClient;
	private static PersistentCookieStore myCookieStore;

	private static final String APPID = "APPID=a3db1231e0bb7f98afa2d3ec398e0782";
	private static final String URL_CITY = "http://api.openweathermap.org/data/2.5/weather?q=";
	private static final String URL_CITY_GEO = "http://api.openweathermap.org/data/2.5/weather?";
	private static final String URL_FORECAST_CITY = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	private static final String URL_FORECAST_GEO = "http://api.openweathermap.org/data/2.5/forecast/daily?";
	private static final String KEY_COD = "cod";
	private static final String KEY_SYS = "sys";
	private static final String KEY_NAME = "name";
	private static final String KEY_MSG = "message";
	private static final String KEY_COUNTRY = "country";
	private static String ERROR_MSG = null;
	/* KEYS FOR WIND */
	private static final String KEY_WIND_SPEED = "speed";
	private static final String KEY_WIND_DEGREE = "deg";
	private static final String KEY_WIND = "wind";
	/* KEYS FOR WERATHER */
	private static final String KEY_WEATHER = "weather";
	private static final String KEY_WEATHER_MAIN = "main";
	private static final String KEY_DATETIME = "dt";

	private static final String KEY_CITY = "city";
	private static final String KEY_LIST = "list";
	private static final String KEY_DAY = "day";
	private static final String KEY_EVENING = "eve";
	private static final String KEY_MORNING = "morn";
	private static final String KEY_NIGHT = "night";

	private static final String KEY_MAIN = "main";
	private static final String KEY_MAIN_TEMP = "temp";
	private static final String KEY_MAIN_PRESSURE = "pressure";
	private static final String KEY_MAIN_HUMIDITY = "humidity";

	public static void init(Context context) {
		client = new AsyncHttpClient();
		synClient = new SyncHttpClient();
		myCookieStore = new PersistentCookieStore(context);
		ERROR_MSG = "Unable to Connect. Please check your Internet Connection";
	}

	public static void reInit(Context context) {
		myCookieStore.clear();
		myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
		synClient.setCookieStore(myCookieStore);
	}

	/**
	 * Method to retrieve weather data using City Name.
	 * 
	 * @param context
	 * @param cityName
	 * @param mCallBack
	 */
	public static void retriveWeatherByCity(String cityName,
			final OnGetWeatherCallBack mCallBack) {
		String url = URL_CITY + cityName + "&" + APPID;
		client.post(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				retriveCityData(response, mCallBack);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				mCallBack.onFailure(ERROR_MSG);
			}
		});
	}

	/**
	 * 
	 * Method to retrieve weather data using current location.
	 * 
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @param mCallBack
	 */
	public static void retriveWeatherByCity(double latitude, double longitude,
			final OnGetWeatherCallBack mCallBack) {
		String url = URL_CITY_GEO + "lat=" + latitude + "&lon=" + longitude
				+ "&" + APPID;

		client.post(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				retriveCityData(response, mCallBack);

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				mCallBack.onFailure(ERROR_MSG);
			}
		});
	}

	/**
	 * Method to retrieve weather forecast by city name.
	 * 
	 * @param cityName
	 * @param mCallBack
	 */
	public static void retrieveWeatherForeCast(String cityName,
			final OnGetDailyForeCastCallBack mCallBack) {
		String url = URL_FORECAST_CITY + cityName + "&" + APPID + "&cnt=14";
		client.post(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				retriveForeCastData(response, mCallBack);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				mCallBack.onFailure(ERROR_MSG);
			}
		});
	}

	/**
	 * Common method to update UI from the response received through ForeCast.
	 * 
	 * @param response
	 * @param mCallBack
	 */
	private static void retriveForeCastData(JSONObject response,
			OnGetDailyForeCastCallBack mCallBack) {
		try {
			if (response.getInt(KEY_COD) == 200) {
				double temperature;
				JSONObject mObject = response.getJSONObject(KEY_CITY);

				JSONArray mArray = response.getJSONArray(KEY_LIST);
				List<ForeCast> list = new ArrayList<ForeCast>();
				for (int i = 0; i < mArray.length(); i++) {
					mObject = mArray.getJSONObject(i);
					String dateStr = WeatherAppUtils.retrieveDate(mObject
							.getLong(KEY_DATETIME));
					ForeCast foreCast = new ForeCast();
					foreCast.setDateTime(dateStr);
					foreCast.setHumidity(mObject.getDouble(KEY_MAIN_HUMIDITY));
					foreCast.setPressure(mObject.getDouble(KEY_MAIN_PRESSURE));
					foreCast.setWindDegree(mObject.getDouble(KEY_WIND_DEGREE));
					foreCast.setWindSpeed(mObject.getDouble(KEY_WIND_SPEED));
					JSONObject lObject = mObject.getJSONObject(KEY_MAIN_TEMP);
					temperature = getTemperature(lObject.getDouble(KEY_DAY));
					foreCast.setDayTemp(temperature);
					temperature = getTemperature(lObject.getDouble(KEY_EVENING));
					foreCast.setEveTemp(temperature);
					temperature = getTemperature(lObject.getDouble(KEY_MORNING));
					foreCast.setMornTemp(temperature);
					temperature = getTemperature(lObject.getDouble(KEY_NIGHT));
					foreCast.setNightTemp(temperature);

					JSONArray wArray = mObject.getJSONArray(KEY_WEATHER);
					for (int j = 0; j < wArray.length(); j++) {
						lObject = wArray.getJSONObject(j);
						foreCast.setDescription(lObject
								.getString(KEY_WEATHER_MAIN));
					}
					list.add(foreCast);
				}
				mCallBack.onSuccess(list);
			} else {
				mCallBack.onStatusFailure(response.getString(KEY_MSG));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Common method to update UI from the response received through API.
	 * 
	 * @param response
	 * @param mCallBack
	 */
	private static void retriveCityData(JSONObject response,
			OnGetWeatherCallBack mCallBack) {
		Weather weather = new Weather();
		try {
			int cod = response.getInt(KEY_COD);
			if (cod == 200) {
				double temperature;
				weather.setCityStr(response.getString(KEY_NAME));
				// retrieve the main temperature values
				JSONObject mObject = response.getJSONObject(KEY_MAIN);
				temperature = getTemperature(mObject.getDouble(KEY_MAIN_TEMP));
				weather.setTemperature(temperature);
				weather.setHumidity(mObject.getDouble(KEY_MAIN_HUMIDITY));
				weather.setPressure(mObject.getDouble(KEY_MAIN_PRESSURE));

				// retrieve the current data for winds.
				mObject = new JSONObject(response.getString(KEY_WIND));
				weather.setWindSpeed(mObject.getDouble(KEY_WIND_SPEED));
				weather.setWindDegree(mObject.getDouble(KEY_WIND_DEGREE));

				// retrieve country name
				mObject = new JSONObject(response.getString(KEY_SYS));
				weather.setCountryStr(mObject.getString(KEY_COUNTRY));
				// retrieve temperature
				JSONArray mArray = response.getJSONArray(KEY_WEATHER);
				for (int i = 0; i < mArray.length(); i++) {
					mObject = mArray.getJSONObject(i);
					weather.setDescription(mObject.getString(KEY_WEATHER_MAIN));
				}
				mCallBack.onSuccess(weather);
			} else {
				mCallBack.onFailure(response.getString(KEY_MSG));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to retrieve weather forecast by current location.
	 * 
	 * @param latitude
	 * @param longitude
	 * @param mCallBack
	 */
	public static void retrieveWeatherForeCast(double latitude,
			double longitude, final OnGetDailyForeCastCallBack mCallBack) {
		String url = URL_FORECAST_GEO + "lat=" + latitude + "&lon=" + longitude
				+ "&cnt=14" + "&APPID";
		client.post(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				retriveForeCastData(response, mCallBack);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				mCallBack.onFailure(ERROR_MSG);
			}
		});
	}

	/**
	 * Method to calculate the temperature
	 * 
	 * @param d
	 * @return
	 */
	private static double getTemperature(double d) {
		return d - 273.5;
	}

}