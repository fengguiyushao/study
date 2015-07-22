package com.robinzhou.designpattern.state;

public interface State {

	public void insertQuarter();
	
	public void ejectQuarter();
	
	public void turnCrank();
	
	public void dispense();
	
}
