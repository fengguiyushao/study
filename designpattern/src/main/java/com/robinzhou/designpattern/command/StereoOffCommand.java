package com.robinzhou.designpattern.command;

public class StereoOffCommand implements Command {
	
	Stereo Stereo;
	
	public StereoOffCommand(Stereo stereo) {
		this.Stereo = stereo;
	}

	@Override
	public void execute() {
		Stereo.off();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
