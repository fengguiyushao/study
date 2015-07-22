package com.robinzhou.designpattern.simplefactory;

public class ClamPizza extends Pizza {

	public ClamPizza() {
		name = "Sauce and Clam Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}
}
