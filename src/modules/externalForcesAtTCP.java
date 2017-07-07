package modules;

import java.util.ArrayList;
import java.util.List;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.sensorModel.ForceSensorData;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class externalForcesAtTCP implements Runnable  {

	private LBR bot;
	private ObjectFrame tcp;
	private ForceSensorData data;
	private List<Double> extForces; 
	private int command;
	private long interval;
	private boolean running;
	
	public externalForcesAtTCP(LBR bot, ObjectFrame tcp) {
		this.bot = bot;
		this.tcp = tcp;
		command = 2;
		interval = 100;
		init();
	}

	public void init() {
		data = bot.getExternalForceTorque(tcp);
		extForces = new ArrayList<Double>();
	}
	
	
	
	public void runLocal() {
		setRunning(true);
		while (command > 0) {
			if (command == 1) {
				extForces.add(data.getForce().getX());
				ThreadUtil.milliSleep(interval);
			}
		}
	}

	@Override
	public void run() {
		runLocal();
	}

	public ObjectFrame getTcp() {
		return tcp;
	}

	public void setTcp(ObjectFrame tcp) {
		this.tcp = tcp;
	}

	public int getCommand() {
		return command;
	}

	/**
	 * @param command int; 0 - cancel thread, 1 - collect data, 2 and higher do not collect data
	 **/
	public void setCommand(int command) {
		this.command = command;
	}

	public List<Double> getExtForces() {
		return extForces;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}



}
