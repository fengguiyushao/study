package com.robinzhou.designpattern.state;

public class WinnerState implements State {

	GumballMachine gumbalMachine;
	
	public WinnerState(GumballMachine gumballMachine) {
		this.gumbalMachine = gumballMachine;
	}
	
	@Override
	public void insertQuarter() {
		System.out.println("Please wait, we're already giving you a gumball");
	}

	@Override
	public void ejectQuarter() {
		System.out.println("Sorry, you already turned the crank");
	}

	@Override
	public void turnCrank() {
		System.out.println("Turning twice doesn't get you another gumball!");
	}

	@Override
	public void dispense() {
		System.out.println("YOU'RE A WINNER! You get two gumballs for your quarter");
		gumbalMachine.releaseBall();
		if(gumbalMachine.getCount() == 0) {
			System.out.println("Oops, out of gumballs");
			gumbalMachine.setState(gumbalMachine.getSoldOutState());
		} else {
			gumbalMachine.releaseBall();
			if(gumbalMachine.getCount() > 0){
				gumbalMachine.setState(gumbalMachine.getNoQuarterState());
			} else {
				System.out.println("Oops, out of gumballs");
				gumbalMachine.setState(gumbalMachine.getSoldOutState());
			}
		}
	}

}
