package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class SHPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		
		switch (type) {
		case "cheese":
			pizza = new SHStyleCheesePizza();
			break;
		case "clam":
			pizza = new SHStyleClamPizza();
			break;
		case "pepperoni":
			pizza = new SHStylePepperoniPizza();
			break;
		case "veggie":
			pizza = new SHStyleVeggiePizza();
			break;
		}
		
		return pizza;
	}

}
