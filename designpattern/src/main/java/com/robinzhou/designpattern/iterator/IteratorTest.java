package com.robinzhou.designpattern.iterator;

import java.util.ArrayList;

public class IteratorTest {

	public static void main(String[] args) {
		Menu pancakeHouseMenu = new PancakeHouseMenu();
		Menu dinerMenu = new DinerMenu();
		Menu cafeMenu = new CafeMenu();
		
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(pancakeHouseMenu);
		menus.add(dinerMenu);
		menus.add(cafeMenu);
		
		Waitress waitress = new Waitress(menus);
		
		waitress.printMenu();
	}
}
