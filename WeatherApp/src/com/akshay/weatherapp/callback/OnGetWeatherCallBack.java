package com.akshay.weatherapp.callback;

import com.akshay.weatherapp.core.models.Weather;

public interface OnGetWeatherCallBack {

	public void onSuccess(Weather weather);

	public void onStatusFailure(String message);

	public void onFailure(String message);
}
