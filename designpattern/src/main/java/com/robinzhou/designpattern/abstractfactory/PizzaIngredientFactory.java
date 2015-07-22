package com.robinzhou.designpattern.abstractfactory;

public interface PizzaIngredientFactory {

	public Dough createDough();
	
	public Sauce createSauce();
	
	public Cheese createCheese();
	
	public Veggies[] createVeggies();
	
	public Pepperoni createPepperoni();
	
	public Clams createClam();
}