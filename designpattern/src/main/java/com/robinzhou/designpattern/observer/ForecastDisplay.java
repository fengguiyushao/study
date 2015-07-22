package com.robinzhou.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

public class ForecastDisplay implements Observer, DisplayElement {
	
	private float currentPressure = 29.92f;
	private float lastPressure;
	private Observable observable;
	
	public ForecastDisplay(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}

	@Override
	public void display() {
		System.out.println("current pressure " + currentPressure + " last pressure " + lastPressure);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherDataNew) {
			WeatherDataNew weatherDataNew = (WeatherDataNew)o;
			lastPressure = currentPressure;
			currentPressure = weatherDataNew.getPressure();
			display();
		}
	}

}
