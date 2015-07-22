package com.robinzhou.designpattern.proxy;

import java.rmi.RemoteException;

public class GumballMonitor {
	
	GumballMachineRemote machine;
	
	public GumballMonitor(GumballMachineRemote machine) {
		this.machine = machine;
	}
	
	public void report() {
		try {
			System.out.println("Gumball Machine:" + machine.getLocation());
			System.out.println("Gumball inventory:" + machine.getCount() + " gumballs");
			System.out.println("Current state:" + machine.getState());
		} catch(RemoteException e){
			e.printStackTrace();
		}
	}

}
