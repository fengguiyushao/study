package com.robinzhou.designpattern.observer;

public class ObserverTest {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		
		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
		
		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 72, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
		
		
		//use Java Observable and Observer
		
		WeatherDataNew weatherDataNew = new WeatherDataNew();
		
		CurrentConditionsDisplayNew currentDisplayNew = new CurrentConditionsDisplayNew(weatherDataNew);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDataNew);
		
		weatherDataNew.setMeasurements(80, 65, 30.4f);
		weatherDataNew.setMeasurements(82, 72, 29.2f);
		weatherDataNew.setMeasurements(78, 90, 29.2f);
	}

}
