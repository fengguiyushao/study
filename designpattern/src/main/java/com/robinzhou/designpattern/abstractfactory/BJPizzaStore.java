package com.robinzhou.designpattern.abstractfactory;

public class BJPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = new BJPizzaIngredientFactory();
		
		switch (type) {
		case "cheese":
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("BJ style cheese pizza");
			break;

		case "veggie":
			pizza = new VeggiePizza(ingredientFactory);
			pizza.setName("BJ style veggie pizza");
			break;
			
		case "clam":
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("BJ style clam pizza");
			break;
			
		case "pepperoni":
			pizza = new PepperoniPizza(ingredientFactory);
			pizza.setName("BJ style pepperoni pizza");
			break;
		}
		
		return pizza;
	}

}
