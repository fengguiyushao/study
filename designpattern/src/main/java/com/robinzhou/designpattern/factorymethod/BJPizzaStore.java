package com.robinzhou.designpattern.factorymethod;

import com.robinzhou.designpattern.simplefactory.Pizza;

public class BJPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		
		switch (type) {
		case "cheese":
			pizza = new BJStyleCheesePizza();
			break;
		case "clam":
			pizza = new BJStyleClamPizza();
			break;
		case "pepperoni":
			pizza = new BJStylePepperoniPizza();
			break;
		case "veggie":
			pizza = new BJStyleVeggiePizza();
			break;
		}
		
		return pizza;
	}

}
