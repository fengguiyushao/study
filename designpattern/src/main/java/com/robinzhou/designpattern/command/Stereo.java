package com.robinzhou.designpattern.command;

public class Stereo {
	
	String name;
	
	public Stereo(String name) {
		this.name = name;
	}

	public void on() {
		System.out.println(name + " stereo on");
	}
	
	public void off() {
		System.out.println(name + " stereo off");
	}
	
	public void setCd() {
		System.out.println(name + " stereo set cd");
	}
	
	public void setDvd() {
		System.out.println(name + " stereo set Dvd");
	}
	
	public void setRadio() {
		System.out.println(name + " stereo set radio");
	}
	
	public void setVolume(int volume) {
		System.out.println(name + " stereo set volume " + volume);
	}
}
