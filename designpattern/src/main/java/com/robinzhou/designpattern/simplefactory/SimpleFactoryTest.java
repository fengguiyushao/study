package com.robinzhou.designpattern.simplefactory;

public class SimpleFactoryTest {
	
	public static void main(String[] args) {
		SimplePizzaFactory factory = new SimplePizzaFactory();
		PizzaStore store = new PizzaStore(factory);
		
		store.orderPizza("cheese");
		store.orderPizza("clam");
		store.orderPizza("pepperoni");
		store.orderPizza("veggie");
	}

}
