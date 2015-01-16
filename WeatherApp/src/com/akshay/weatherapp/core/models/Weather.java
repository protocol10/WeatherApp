package com.akshay.weatherapp.core.models;

public class Weather {

	String cityStr, countryStr, weatherStr, description;

	double pressure, humidity, windDegree, minTemp, maxTemp, windSpeed;

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getCountryStr() {
		return countryStr;
	}

	public void setCountryStr(String countryStr) {
		this.countryStr = countryStr;
	}

	public String getWeatherStr() {
		return weatherStr;
	}

	public void setWeatherStr(String weatherStr) {
		this.weatherStr = weatherStr;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getWindDegree() {
		return windDegree;
	}

	public void setWindDegree(double windDegree) {
		this.windDegree = windDegree;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
