package com.akshay.weatherapp.core.models;

public class ForeCast {

	double pressure, humidity, windDegree, dayTemp, nightTemp, eveTemp,
			mornTemp, windSpeed;
	String weather, dateTime, description;

	public ForeCast() {
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

	public double getDayTemp() {
		return dayTemp;
	}

	public void setDayTemp(double dayTemp) {
		this.dayTemp = dayTemp;
	}

	public double getNightTemp() {
		return nightTemp;
	}

	public void setNightTemp(double nightTemp) {
		this.nightTemp = nightTemp;
	}

	public double getEveTemp() {
		return eveTemp;
	}

	public void setEveTemp(double eveTemp) {
		this.eveTemp = eveTemp;
	}

	public double getMornTemp() {
		return mornTemp;
	}

	public void setMornTemp(double mornTemp) {
		this.mornTemp = mornTemp;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
