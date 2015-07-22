package com.robinzhou.designpattern.command;

public class GarageDoor {
	
	String name;
	
	public GarageDoor(String name) {
		this.name = name;
	}
	
	public void up() {
		System.out.println("garage door is open");
	}

	public void down() {
		System.out.println("garage door is close");
	}
	
	public void stop() {
		System.out.println("garage door is stop");
	}
	
	public void lightOn() {
		System.out.println("garage light on");
	}
	
	public void lightOff() {
		System.out.println("garage light off");
	}
}
