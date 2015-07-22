package com.robinzhou.designpattern.composite;

import java.util.Iterator;

public class MenuItem extends MenuComponent {
	
	String name;
	String description;
	boolean vegetatian;
	double price;
	
	public MenuItem(String name, String description, boolean vegeratian, double price) {
		this.name = name;
		this.description = description;
		this.vegetatian = vegeratian;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public boolean isVegetarian() {
		return vegetatian;
	}
	
	public void print() {
		System.out.print("  " + getName());
		if(isVegetarian()) {
			System.out.print("(v)");
		}
		System.out.println(", " +getPrice());
		System.out.println("    -- " +getDescription());
	}

	@Override
	public Iterator<MenuComponent> createIterator() {
		return new NullIterator();
	}
	

}
