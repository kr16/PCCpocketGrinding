package modules;

import java.util.ArrayList;

import com.kuka.roboticsAPI.geometricModel.Frame;

public class IIWAethernetComm {
	private Frame posFrame;
	private ArrayList<Frame> recPositions; // = new ArrayList<Frame>();
	
	//Constructor
	public IIWAethernetComm(Frame posFrame) {
		init();
		this.setPosFrame(posFrame);
		recPositions.add(posFrame);
	}

	private void init() {
		recPositions = new ArrayList<Frame>();
	}
	public Frame getPosFrame() {
		return posFrame;
	}

	public void setPosFrame(Frame posFrame) {
		this.posFrame = posFrame;
	}
	
}

