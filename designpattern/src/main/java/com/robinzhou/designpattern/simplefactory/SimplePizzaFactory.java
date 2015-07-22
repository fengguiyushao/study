package com.robinzhou.designpattern.simplefactory;

public class SimplePizzaFactory {
	
	public Pizza createPizza(String type) {
		Pizza pizza = null;
		
		switch (type) {
		case "cheese":
			pizza = new CheesePizza();
			break;
		case "clam":
			pizza = new ClamPizza();
			break;
		case "pepperoni":
			pizza = new PepperoniPizza();
			break;
		case "veggie":
			pizza = new VeggiePizza();
			break;
		}
		
		return pizza;
	}

}
