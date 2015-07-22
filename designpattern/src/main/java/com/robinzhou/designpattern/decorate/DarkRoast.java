package com.robinzhou.designpattern.decorate;

public class DarkRoast extends Beverage {
	
	public String getDescription() {
		return "Dark Roast Coffee";
	};

	@Override
	public double cost() {
		return .99;
	}
}
