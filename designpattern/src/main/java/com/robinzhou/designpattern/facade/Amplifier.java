package com.robinzhou.designpattern.facade;

public class Amplifier {
	
	Tuner tuner;
	DvdPlayer dvdPlayer;
	CdPlayer cdPlayer;
	
	public void on() {
		System.out.println("amplifier on");
	}
	
	public void off() {
		System.out.println("amplifier off");
	}
	
	public void setCd(CdPlayer cdPlayer) {
		System.out.println("amplifier set cd");
		this.cdPlayer = cdPlayer;
	}
	
	public void setDvd(DvdPlayer dvdPlayer) {
		this.dvdPlayer = dvdPlayer;
	}
	
	public void setStereaSound() {
		System.out.println("amplifier set sterea sound");
	}
	
	public void setSurroundSound() {
		System.out.println("amplifier set surround sound");
	}
	
	public void setTuner(Tuner tuner) {
		this.tuner = tuner;
	}
	
	public void setVolume() {
		System.out.println("amplifier set volume");
	}
	
	public String toString() {
		return "It is amplifier";
	}

}
