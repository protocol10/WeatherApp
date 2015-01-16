package com.akshay.weatherapp;

import com.akshay.weatherapp.core.WeatherAppApi;

import android.app.Application;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		WeatherAppApi.init(this);
	}
}
