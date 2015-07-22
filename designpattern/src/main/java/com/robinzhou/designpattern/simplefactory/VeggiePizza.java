package com.robinzhou.designpattern.simplefactory;

public class VeggiePizza extends Pizza {

	public VeggiePizza() {
		name = "Sauce and Veggie Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}

}
