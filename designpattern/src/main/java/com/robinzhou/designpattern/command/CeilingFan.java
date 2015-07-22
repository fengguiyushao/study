package com.robinzhou.designpattern.command;

public class CeilingFan {
	
	public static final int HIGH = 3;
	public static final int MEDIUM = 2;
	public static final int LOW = 1;
	public static final int OFF = 0;
	
	String name;
	int speed;
	
	public CeilingFan(String name) {
		this.name = name;
		this.speed = OFF;
	}
	
	public void high() {
		System.out.println(name + " ceiling fan high");
		this.speed = HIGH;
	}
	
	public void medium() {
		System.out.println(name + " ceiling fan medium");
		this.speed = MEDIUM;
	}
	
	public void low() {
		System.out.println(name + " ceiling fan low");
		this.speed = LOW;
	}
	
	public void off() {
		System.out.println(name + " ceiling fan off");
		this.speed = OFF;
	}
	
	public int getSpeed() {
		return speed;
	}
}
