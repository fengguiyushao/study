package com.robinzhou.designpattern.abstractfactory;

public class AbstractFactoryTest {
	
	public static void main(String[] args) {
		PizzaStore shPizzaStore = new SHPizzaStore();
		Pizza shCheesePizza = shPizzaStore.orderPizza("cheese");
		shCheesePizza.display();
		
		PizzaStore bjPizzaStore = new BJPizzaStore();
		Pizza bjClamPizza = bjPizzaStore.orderPizza("clam");
		bjClamPizza.display();
	}

}
