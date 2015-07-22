package com.robinzhou.designpattern.facade;

public class Tuner {
	Amplifier amplifier;
	
	public void on() {
		System.out.println("tuner on");
	}
	
	public void off() {
		System.out.println("tuner off");
	}
	
	public void setAm() {
		System.out.println("tuner set am");
	}
	
	public void setFM() {
		System.out.println("tuner set fm");
	}
	
	public void setFrequency() {
		System.out.println("tuner set frequency");
	}
	
	public String toString() {
		return "It is tuner";
	}

}
