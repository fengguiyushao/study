package com.robinzhou.designpattern.simplefactory;

public class PepperoniPizza extends Pizza {

	public PepperoniPizza() {
		name = "Sauce and Pepperoni Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}

}
