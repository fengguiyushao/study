package com.robinzhou.designpattern.proxy;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ProxyServiceTest {
	
	public static void main(String[] args) {
		GumballMachineRemote gumballMachine = null;
		int count;
		if(args.length < 3) {
			System.out.println("Gumball Machine <name> <inventory>");
			System.exit(1);
		}
		
		try {
			count = Integer.parseInt(args[2]);
			gumballMachine = new GumballMachine(args[0], count);
			LocateRegistry.createRegistry(Integer.parseInt(args[1]));
			Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/gumballmachine", gumballMachine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
