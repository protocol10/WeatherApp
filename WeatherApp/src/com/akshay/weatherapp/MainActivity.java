package com.akshay.weatherapp;

import java.text.DecimalFormat;

import com.akshay.weatherapp.callback.OnGetWeatherCallBack;
import com.akshay.weatherapp.core.WeatherAppApi;
import com.akshay.weatherapp.core.models.Weather;
import com.akshay.weatherapp.utils.WeatherAppUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private static final String TAG = "WeatheApp";
	private TextView lblLocation, lblTemperature, lblPressure, lblDescription,
			lblHumidity, lblWind, lblWindDegree;
	private EditText txtCity;
	private Button btnSubmit, btnLocation;

	private String cityStr, temperatureStr, locationStr;
	private Context context;
	private ProgressDialog progressDialog;

	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		lblTemperature = (TextView) findViewById(R.id.lbl_temperature);
		lblLocation = (TextView) findViewById(R.id.lbl_location);
		lblPressure = (TextView) findViewById(R.id.lbl_pressure);
		lblDescription = (TextView) findViewById(R.id.lbl_description);
		lblHumidity = (TextView) findViewById(R.id.lbl_humidity);
		lblWind = (TextView) findViewById(R.id.lbl_wind);
		lblWindDegree = (TextView) findViewById(R.id.lbl_wind_degree);

		int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if (res == ConnectionResult.SUCCESS) {
			locationClient = new LocationClient(context,
					new GooglePlayServicesClient.ConnectionCallbacks() {

						@Override
						public void onDisconnected() {
							Log.i(TAG, "onDisconnected");
						}

						@Override
						public void onConnected(Bundle arg0) {
							Log.i(TAG, "onConnected");
						}
					},
					new GooglePlayServicesClient.OnConnectionFailedListener() {

						@Override
						public void onConnectionFailed(ConnectionResult arg0) {
							Log.i(TAG, "onConnectionFailed");
						}
					});
			locationClient.connect();
		}
		clearAll();
		txtCity = (EditText) findViewById(R.id.txt_city);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnLocation = (Button) findViewById(R.id.btn_location);
		btnLocation.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
	}

	/**
	 * Method to clear all TextField.
	 */
	private void clearAll() {
		lblLocation.setText("");
		lblTemperature.setText("");
		lblPressure.setText("");
		lblDescription.setText("");
		lblHumidity.setText("");
		lblWind.setText("");
		lblWindDegree.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			cityStr = txtCity.getText().toString();
			if (cityStr.length() != 0) {
				retriveWeather(context, cityStr);

			} else {
				WeatherAppUtils.showMessage(getApplicationContext(),
						"Please enter City Name");
			}
			break;
		case R.id.btn_location:
			if (locationClient != null && locationClient.isConnected()) {
				Location loc = locationClient.getLastLocation();
				if (loc != null) {
					retriveWeather(context, loc.getLatitude(),
							loc.getLongitude());
				}

			}
			break;
		default:
			break;
		}
	}

	protected void updateUI(Weather weather) {
		if (weather != null) {
			dismissProgressDialog();
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");
			locationStr = weather.getCityStr() + ", " + weather.getCountryStr();
			lblLocation.setText(locationStr.toString());
			temperatureStr = decimalFormat.format(weather.getMaxTemp()) + "/"
					+ decimalFormat.format(weather.getMinTemp()) + " \u2103";
			lblTemperature.setText(temperatureStr);
			lblHumidity.setText(weather.getHumidity() + " %");
			lblWind.setText(weather.getWindSpeed() + " mph");
			lblWindDegree.setText(decimalFormat.format(weather.getWindDegree())
					+ " \u00b0");
			lblDescription.setText(weather.getDescription());
		}
	}

	/**
	 * Method to call the weather API based on City Name
	 * 
	 * @param c
	 * @param city
	 */
	private void retriveWeather(Context c, String city) {
		showProgressDialog(c);
		WeatherAppApi.retriveWeatherByCity(c, city, new OnGetWeatherCallBack() {

			@Override
			public void onSuccess(Weather weather) {
				updateUI(weather);
			}

			@Override
			public void onStatusFailure(String message) {
				dismissProgressDialog();
				WeatherAppUtils.showMessage(getApplicationContext(), message);
			}

			@Override
			public void onFailure(String message) {
				dismissProgressDialog();
				WeatherAppUtils.showMessage(getApplicationContext(), message);
			}
		});
	}

	/**
	 * Method to retrieve weather using latitude and longitude.
	 * 
	 * @param c
	 * @param latitude
	 * @param longitude
	 */
	private void retriveWeather(Context c, double latitude, double longitude) {
		showProgressDialog(c);
		WeatherAppApi.retriveWeatherByCity(c, latitude, longitude,
				new OnGetWeatherCallBack() {

					@Override
					public void onSuccess(Weather weather) {
						updateUI(weather);
					}

					@Override
					public void onStatusFailure(String message) {
						WeatherAppUtils.showMessage(getApplicationContext(),
								message);
					}

					@Override
					public void onFailure(String message) {
						WeatherAppUtils.showMessage(getApplicationContext(),
								message);
					}
				});
	}

	/**
	 * Method to show ProgressDialog.
	 * 
	 * @param c
	 */
	private void showProgressDialog(Context c) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(c);
		}
		progressDialog.setMessage("Please wait ...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	/**
	 * Method to dismiss Progress Dialog.
	 */
	private void dismissProgressDialog() {
		progressDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationClient != null) {
			locationClient.connect();
		}

	}
}
