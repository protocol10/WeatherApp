package com.akshay.weatherapp.callback;

import com.akshay.weatherapp.core.models.ForeCast;

import java.util.List;

public interface OnGetDailyForeCastCallBack {

	public void onSuccess(List<ForeCast> list);

	public void onStatusFailure(String message);

	public void onFailure(String message);
}
