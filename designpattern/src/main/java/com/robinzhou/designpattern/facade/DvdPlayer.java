package com.robinzhou.designpattern.facade;

public class DvdPlayer {

	Amplifier amplifier;
	
	public void on() {
		System.out.println("dvd player on");
	}
	
	public void off() {
		System.out.println("dvd player off");
	}
	
	public void eject() {
		System.out.println("dvd player eject");
	}
	
	public void pause() {
		System.out.println("dvd player pause");
	}
	
	public void play() {
		System.out.println("dvd player play");
	}
	
	public void stop() {
		System.out.println("dvd player stop");
	}
	
	public void setSurroundAudio() {
		System.out.println("dvd player set surround audio");
	}
	
	public void setTwoChannelAudio() {
		System.out.println("dvd player set two channel audio");
	}
	
	public String toString() {
		return "It is dvd player";
	}
}
