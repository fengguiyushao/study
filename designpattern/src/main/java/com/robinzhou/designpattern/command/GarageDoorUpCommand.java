package com.robinzhou.designpattern.command;

public class GarageDoorUpCommand implements Command {
	
	GarageDoor garageDoor;
	
	public GarageDoorUpCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		this.garageDoor.up();
	}

	@Override
	public void undo() {
		this.garageDoor.down();
	}

}
