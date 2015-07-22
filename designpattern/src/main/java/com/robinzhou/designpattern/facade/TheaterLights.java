package com.robinzhou.designpattern.facade;

public class TheaterLights {
	
	public void on() {
		System.out.println("theater light on");
	}
	
	public void off() {
		System.out.println("theater light off");
	}
	
	public void dim() {
		System.out.println("theater light dim");
	}
	
	public String toString() {
		return "It is theater lights";
	}

}
