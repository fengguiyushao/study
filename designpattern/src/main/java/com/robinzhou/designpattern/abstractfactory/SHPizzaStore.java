package com.robinzhou.designpattern.abstractfactory;

public class SHPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = new SHPizzaIngredientFactory();
		
		switch (type) {
		case "cheese":
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("SH style cheese pizza");
			break;

		case "veggie":
			pizza = new VeggiePizza(ingredientFactory);
			pizza.setName("SH style veggie pizza");
			break;
			
		case "clam":
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("SH style clam pizza");
			break;
			
		case "pepperoni":
			pizza = new PepperoniPizza(ingredientFactory);
			pizza.setName("SH style pepperoni pizza");
			break;
		}
		
		return pizza;
	}

}
