package com.robinzhou.designpattern.iterator;

import java.util.Hashtable;
import java.util.Iterator;

public class CafeMenu implements Menu {
	
	Hashtable<String, MenuItem> menuItems;
	
	public CafeMenu() {
		menuItems = new Hashtable<String, MenuItem>();
		addItem("a cafe", "a cafe with xxx", false, 3.99);
		addItem("b cafe", "b cafe with yyy", true, 2.99);
		addItem("c cafe", "c cafe with zzz", false, 1.49);
	}
	
	public void addItem(String name,String description, boolean vegetarian, double price) {
		MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
		menuItems.put(name, menuItem);
	}
	

	@Override
	public Iterator<MenuItem> createIterator() {
		return menuItems.values().iterator();
	}

}
