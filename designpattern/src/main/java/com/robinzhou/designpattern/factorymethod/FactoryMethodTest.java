package com.robinzhou.designpattern.factorymethod;

public class FactoryMethodTest {
	
	public static void main(String[] args) {
		PizzaStore shPizzaStore = new SHPizzaStore();
		shPizzaStore.orderPizza("cheese");
		
		PizzaStore bjPizzaStore = new BJPizzaStore();
		bjPizzaStore.orderPizza("clam");
	}

}
