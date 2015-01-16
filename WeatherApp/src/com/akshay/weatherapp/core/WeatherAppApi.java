package com.akshay.weatherapp.core;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.akshay.weatherapp.callback.OnGetWeatherCallBack;
import com.akshay.weatherapp.core.models.Weather;
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
	private static final String KEY_COD = "cod";
	private static final String KEY_SYS = "sys";
	private static final String KEY_CITY = "name";
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
	private static final String KEY_WEATHER_DESCR = "description";

	private static final String KEY_MAIN = "main";
	private static final String KEY_MAIN_TEMPMIN = "temp_min";
	private static final String KEY_MAIN_TEMPMAX = "temp_max";
	private static final String KEY_MAIN_PRESSURE = "pressure";
	private static final String KEY_MAIN_HUMIDITY = "humidity";

	public static void init(Context context) {
		client = new AsyncHttpClient();
		synClient = new SyncHttpClient();
		myCookieStore = new PersistentCookieStore(context);
		ERROR_MSG = "Unable to Connect.Please check your Internet Connection";
	}

	public static void reInit(Context context) {
		myCookieStore.clear();
		myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
		synClient.setCookieStore(myCookieStore);
	}

	public static void retriveWeatherByCity(final Context context,
			String cityName, final OnGetWeatherCallBack mCallBack) {
		String url = URL_CITY + cityName + "&" + APPID;
		client.post(context, url, null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Weather weather = new Weather();
				try {
					int cod = response.getInt(KEY_COD);
					if (cod == 200) {
						double temperature;
						weather.setCityStr(response.getString(KEY_CITY));
						// retrieve the main temperature values
						JSONObject mObject = response.getJSONObject(KEY_MAIN);
						temperature = getTemperature(mObject
								.getDouble(KEY_MAIN_TEMPMAX));
						weather.setMaxTemp(temperature);
						temperature = getTemperature(mObject
								.getDouble(KEY_MAIN_TEMPMIN));
						weather.setMinTemp(temperature);
						weather.setHumidity(mObject
								.getDouble(KEY_MAIN_HUMIDITY));
						weather.setPressure(mObject
								.getDouble(KEY_MAIN_PRESSURE));

						// retrieve the current data for winds.
						mObject = new JSONObject(response.getString(KEY_WIND));
						weather.setWindSpeed(mObject.getDouble(KEY_WIND_SPEED));
						weather.setWindDegree(mObject
								.getDouble(KEY_WIND_DEGREE));

						mObject = new JSONObject(response.getString(KEY_SYS));
						weather.setCountryStr(mObject.getString(KEY_COUNTRY));
						JSONArray mArray = response.getJSONArray(KEY_WEATHER);
						for (int i = 0; i < mArray.length(); i++) {
							mObject = mArray.getJSONObject(i);
							weather.setDescription(mObject
									.getString(KEY_WEATHER_MAIN));
						}
						mCallBack.onSuccess(weather);
					} else {
						mCallBack.onFailure(response.getString(KEY_MSG));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
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

	public static void retriveWeatherByCity(Context context, double latitude,
			double longitude, final OnGetWeatherCallBack mCallBack) {
		String url = URL_CITY_GEO + "lat=" + latitude + "&lon=" + longitude
				+ "&" + APPID;

		client.post(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Weather weather = new Weather();
				try {
					int cod = response.getInt(KEY_COD);
					if (cod == 200) {
						double temperature;
						weather.setCityStr(response.getString(KEY_CITY));
						// retrieve the main temperature values
						JSONObject mObject = response.getJSONObject(KEY_MAIN);
						temperature = getTemperature(mObject
								.getDouble(KEY_MAIN_TEMPMAX));
						weather.setMaxTemp(temperature);
						temperature = getTemperature(mObject
								.getDouble(KEY_MAIN_TEMPMIN));
						weather.setMinTemp(temperature);
						weather.setHumidity(mObject
								.getDouble(KEY_MAIN_HUMIDITY));
						weather.setPressure(mObject
								.getDouble(KEY_MAIN_PRESSURE));

						// retrieve the current data for winds.
						mObject = new JSONObject(response.getString(KEY_WIND));
						weather.setWindSpeed(mObject.getDouble(KEY_WIND_SPEED));
						weather.setWindDegree(mObject
								.getDouble(KEY_WIND_DEGREE));

						mObject = new JSONObject(response.getString(KEY_SYS));
						weather.setCountryStr(mObject.getString(KEY_COUNTRY));
						JSONArray mArray = response.getJSONArray(KEY_WEATHER);
						for (int i = 0; i < mArray.length(); i++) {
							mObject = mArray.getJSONObject(i);
							weather.setDescription(mObject
									.getString(KEY_WEATHER_MAIN));
						}
						mCallBack.onSuccess(weather);
					} else {
						mCallBack.onFailure(response.getString(KEY_MSG));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				mCallBack.onFailure(ERROR_MSG);
			}
		});
	}
}
