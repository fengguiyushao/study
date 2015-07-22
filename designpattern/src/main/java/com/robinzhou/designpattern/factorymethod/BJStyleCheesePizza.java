package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class BJStyleCheesePizza extends Pizza {
	
	public BJStyleCheesePizza() {
		name = "BJ Style Sauce and Cheese Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}

}
