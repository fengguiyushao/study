package com.robinzhou.designpattern.facade;

public class Screen {

	public void up() {
		System.out.println("screen up");
	}
	
	public void down() {
		System.out.println("screen down");
	}
	
	public String toString() {
		return "It is screen";
	}
}
