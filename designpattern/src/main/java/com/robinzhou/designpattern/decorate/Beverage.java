package com.robinzhou.designpattern.decorate;

public abstract class Beverage {
	
	String description = "Unknown Beverage";
	
	public String getDescription() {
		return description;
	}
	
	public abstract double cost();
}
