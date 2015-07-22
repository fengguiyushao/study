package com.robinzhou.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionsDisplayNew implements Observer, DisplayElement {
	
	Observable observable;
	private float temperature;
	private float humidity;

	public CurrentConditionsDisplayNew(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherDataNew) {
			WeatherDataNew weatherData = (WeatherDataNew)o;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}

}
