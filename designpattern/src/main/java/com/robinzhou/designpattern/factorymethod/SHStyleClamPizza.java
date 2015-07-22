package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class SHStyleClamPizza extends Pizza {

	public SHStyleClamPizza() {
		name = "SH Style Sauce and Clam Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
		
		toppings.add("Grated Reggiano Cheese");
	}

}
