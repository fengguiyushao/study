package com.robinzhou.designpattern.command;

public class CommandTest {
	
	public static void main(String[] args) {
		//simpleRemoteControl
//		SimpleRemoteControl remote = new SimpleRemoteControl();
//		Light light = new Light("Living Room");
//		GarageDoor garageDoor = new GarageDoor("");
//		LightOnCommand lightOn = new LightOnCommand(light);
//		GarageDoorUpCommand garageUp = new GarageDoorUpCommand(garageDoor);
//		
//		remote.setCommand(lightOn);
//		remote.buttonWasPressed();
//		remote.setCommand(garageUp);
//		remote.buttonWasPressed();
		
		//RemoteControl
		RemoteControl remoteControl = new RemoteControl();
		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		CeilingFan ceilingFan = new CeilingFan("Living Room");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");
		
		LightOnCommand livingRoomLightON = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
		LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
		
		CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
		CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
		
		GarageDoorUpCommand garageDoorOpen = new GarageDoorUpCommand(garageDoor);
		GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);
		
		StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);
		
		remoteControl.setCommand(0, livingRoomLightON, livingRoomLightOff);
		remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
		remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
		remoteControl.setCommand(3, stereoOnWithCD, stereoOff);
		
		System.out.println(remoteControl);
		
		remoteControl.onButtonWasPressed(0);
		remoteControl.offButtonWasPressed(0);
		remoteControl.undoButtonWasPressed();
		
		remoteControl.onButtonWasPressed(1);
		remoteControl.offButtonWasPressed(1);
		remoteControl.undoButtonWasPressed();
		
		remoteControl.onButtonWasPressed(2);
		remoteControl.offButtonWasPressed(2);
		remoteControl.undoButtonWasPressed();
		
		remoteControl.onButtonWasPressed(3);
		remoteControl.offButtonWasPressed(3);
		remoteControl.undoButtonWasPressed();
		
		
		System.out.println("------ MacroCommand ------");
		
		Command[] partyOn = {livingRoomLightON, stereoOnWithCD, ceilingFanOn};
		Command[] partyOff = {livingRoomLightOff, stereoOff, ceilingFanOff};
		
		MacroCommand partyOnMacro = new MacroCommand(partyOn);
		MacroCommand partyOffMacro = new MacroCommand(partyOff);
		
		remoteControl.setCommand(0, partyOnMacro, partyOffMacro);
		remoteControl.onButtonWasPressed(0);
		remoteControl.offButtonWasPressed(0);
		remoteControl.undoButtonWasPressed();
		
	}

}
