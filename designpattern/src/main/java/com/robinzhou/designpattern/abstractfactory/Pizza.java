package com.robinzhou.designpattern.abstractfactory;

public abstract class Pizza {
	
	protected String name;
	protected Dough dough;
	protected Sauce sauce;
	protected Veggies veggies[];
	protected Cheese cheese;
	protected Pepperoni pepperoni;
	protected Clams clams;
	
	abstract void prepare();
	
	public void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}
	
	public void cut() {
		System.out.println("Cutting the pizza into diagonal slices");
	}
	
	public void box() {
		System.out.println("Place pizza in official PizzaStore box");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void display() {
		System.out.println("pizza name:" + name);
		if(dough != null) { 
			System.out.println("      dough:" + dough.getName());
		}
		if(sauce != null) {
			System.out.println("      sauce:" + sauce.getName());
		}
		if(cheese != null) {
			System.out.println("      Cheese:" + cheese.getName());
		}
		if(pepperoni != null) {
			System.out.println("      Pepperoni:" + pepperoni.getName());
		}
		if(clams != null) { 
			System.out.println("      Clams:" + clams.getName());
		}
		if(veggies != null) {
			System.out.println("      veggies:");
			for (Veggies v : veggies) {
				System.out.print(v.getName() + "  ");
			}
			System.out.println();
		}

	}
	
	
}
