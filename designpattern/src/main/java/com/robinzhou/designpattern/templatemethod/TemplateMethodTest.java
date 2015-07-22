package com.robinzhou.designpattern.templatemethod;

public class TemplateMethodTest {
	
	public static void main(String[] args) {
		Tea myTea = new Tea();
		myTea.prepareRecipe();
		
		CoffeeWithHook coffeeHook = new CoffeeWithHook();
		
		System.out.println("\n Making coffee...");
		coffeeHook.prepareRecipe();
	}

}
