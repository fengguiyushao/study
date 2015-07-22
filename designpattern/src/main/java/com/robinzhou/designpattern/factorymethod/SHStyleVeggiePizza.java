package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class SHStyleVeggiePizza extends Pizza {

	public SHStyleVeggiePizza() {
		name = "SH Style Sauce and Veggie Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}
}
