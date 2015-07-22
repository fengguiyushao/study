package com.robinzhou.designpattern.facade;

public class Projector {

	DvdPlayer dvdPlayer;
	
	public void on() {
		System.out.println("projector on");
	}
	
	public void off() {
		System.out.println("project");
	}
	
	public void tvMode() {
		System.out.println("dvd player tv mode");
	}
	
	public void wideScreenMode() {
		System.out.println("dvd player wide screen mode");
	}
	
	public String toString() {
		return "It is dvd player";
	}
}
