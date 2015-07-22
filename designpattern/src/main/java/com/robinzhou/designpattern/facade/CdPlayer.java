package com.robinzhou.designpattern.facade;

public class CdPlayer {
	
	Amplifier amplifier;
	
	public void on() {
		System.out.println("cd player on");
	}
	
	public void off() {
		System.out.println("cd player off");
	}
	
	public void eject() {
		System.out.println("cd player eject");
	}
	
	public void pause() {
		System.out.println("cd player pause");
	}
	
	public void play() {
		System.out.println("cd player play");
	}
	
	public void stop() {
		System.out.println("cd player stop");
	}
	
	public String toString() {
		return "It is cd player";
	}

}
