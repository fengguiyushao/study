package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class SHStylePepperoniPizza extends Pizza {

	public SHStylePepperoniPizza() {
		name = "SH Style Sauce and Pepperoni Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}
}
