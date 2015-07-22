package com.robinzhou.designpattern.facade;

public class PopcornPopper {
	
	public void on() {
		System.out.println("popcorn popper on");
	}
	
	public void off() {
		System.out.println("popcorn popper off");
	}
	
	public void pop() {
		System.out.println("popcorn popper pop");
	}
	
	public String toString() {
		return "It is popcorn popper";
	}

}
