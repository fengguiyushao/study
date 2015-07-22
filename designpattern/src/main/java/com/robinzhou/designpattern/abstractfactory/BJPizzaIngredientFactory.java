package com.robinzhou.designpattern.abstractfactory;

public class BJPizzaIngredientFactory implements PizzaIngredientFactory {

	@Override
	public Dough createDough() {
		return new ThickCrustDough();
	}

	@Override
	public Sauce createSauce() {
		return new PlumTomatoSauce();
	}

	@Override
	public Cheese createCheese() {
		return new Mozzarella();
	}

	@Override
	public Veggies[] createVeggies() {
		Veggies veggies[] = {new BlackOllves(), new Spinach(), new EggPlant()};
		return veggies;
	}

	@Override
	public Pepperoni createPepperoni() {
		return new SllcadPepperoni();
	}

	@Override
	public Clams createClam() {
		return new FrozenClams();
	}

}
