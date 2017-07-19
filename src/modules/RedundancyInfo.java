package modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kuka.common.params.IParameter;
import com.kuka.common.params.IParameterSet;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.redundancy.IRedundancyCollection;

public class RedundancyInfo extends RoboticsAPIApplication{
	private int StatusParameter;
	private int TurnParameter;
	private double E1Parameter;
	private String fUserFrameName;
	
	private Map<String, Object> userMap = new HashMap<String, Object>();
	
	
	public RedundancyInfo() {
		
	}
	
	public Map<String, Object> getRedundancyData(ObjectFrame fUserFrame) {
		init();
		return collectionStruggles(fUserFrame);
	}
	
	private void init() {
		userMap.clear();	//clear map
	}
	
	private Map<String, Object> collectionStruggles(ObjectFrame fUserFrame) {
			this.setfUserFrameName(fUserFrame.getName());
			System.out.println(this.getfUserFrameName());
			System.out.println(fUserFrame.copy());
			Collection<IRedundancyCollection> fUserFrameRedundancyInfoCollection =  fUserFrame.getRedundancyInformation().values();
			if (fUserFrameRedundancyInfoCollection.size() == 1) {
				IParameterSet newSet = fUserFrameRedundancyInfoCollection.iterator().next().getAllParameters();
				//get iterator for parameter set
				Iterator<IParameter<?>> paramSetIterator = newSet.iterator();
				int loopCounter = 0;
				while (paramSetIterator.hasNext()) {
					loopCounter++;
					IParameter<?> yaParam = paramSetIterator.next();
					switch (loopCounter) {
					case 1:
						this.setStatusParameter((Integer) yaParam.value());
						this.userMap.put("StatusParameter", getStatusParameter());
						break;
					case 2:
						this.setTurnParameter((Integer) yaParam.value());
						this.userMap.put("TurnParameter", getTurnParameter());
						break;
					case 3:
						this.setE1Parameter((Double) yaParam.value());
						this.userMap.put("E1Parameter", getE1Parameter());
						break;
					default:
						throw new NoRedundancyDataException("Wrong format or wrong number of parameters for redundancy data!");
					}
				}
				//System.out.println("Map: " + this.userMap.toString());
			} else {
				throw new NoRedundancyDataException("Frame " + this.getfUserFrameName() + " has no redundancy information!");	
			}
			return this.userMap;
	}

	private class NoRedundancyDataException extends RuntimeException {
		// constructor
		public NoRedundancyDataException(String txt)
		{
			super(txt); // call superclass constructor
		}
	}
	
	@Override
	public void run() throws Exception {
		throw new NoRedundancyDataException("Not runnable class!");
	}

	public int getStatusParameter() {
		return StatusParameter;
	}

	public void setStatusParameter(int statusParameter) {
		StatusParameter = statusParameter;
	}

	public int getTurnParameter() {
		return TurnParameter;
	}

	public void setTurnParameter(int turnParameter) {
		TurnParameter = turnParameter;
	}

	public double getE1Parameter() {
		return E1Parameter;
	}

	public void setE1Parameter(double e1Parameter) {
		E1Parameter = e1Parameter;
	}

	public String getfUserFrameName() {
		return fUserFrameName;
	}

	public void setfUserFrameName(String fUserFrameName) {
		this.fUserFrameName = fUserFrameName;
	}
	
}
