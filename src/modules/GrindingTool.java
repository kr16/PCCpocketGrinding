package modules;

import modules.Common.EToolName;

import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1_4valvesonlyIOGroup;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

public class GrindingTool {
	private SMC600_SPN1_4valvesonlyIOGroup SMC_IO;
	private EK1100IOGroup beckhoffEcatIO;
	private Tool sunriseTool;
	private String cutterName;
	private double cutterDiameter;
	private double cutterRadius;
	private ObjectFrame currentTCP;
	
	

	public GrindingTool(Controller controller) {
		SMC_IO = new SMC600_SPN1_4valvesonlyIOGroup(controller);
		beckhoffEcatIO = new EK1100IOGroup(controller);
	}
	
	/**
	 * Enables grinding tool valves - both of them - full speed
	 * WATCH YOUR HANDS/EYES !!!
	 */
	public void grindingStart() {
		if(!StaticGlobals.disableTool) {
			SMC_IO.setSMC_DO01A_GrinderValve(true);
			beckhoffEcatIO.setEK1100_DO01_GrindingToolReq(true);
		} else {
			System.err.println("Tool is disabled !!!");
		}
	}

	/**
	 * This is specific to Background Task only for tool safety control. 
	 * Does not enable/disable tool request output. 
	 * DO NOT USE IT outside Background Task
	 */
	public void grindingStartNoRequest() {
		long cutterStartDelay = 500;	//so many milisecs to start spinning full speed
		if(!StaticGlobals.disableTool) {
			SMC_IO.setSMC_DO01A_GrinderValve(true);
			ThreadUtil.milliSleep(cutterStartDelay);
		} else {
			System.err.println("Tool is disabled !!!");
		}
	}

	/**
	 * Disable grinding tool
	 */
	public void grindingStop() {
		long cutterStopDelay = 1000;	//so many milisecs to fully stop spinning
		SMC_IO.setSMC_DO01A_GrinderValve(false);
		beckhoffEcatIO.setEK1100_DO01_GrindingToolReq(false);
		ThreadUtil.milliSleep(cutterStopDelay);	//stop delay
	}
	
	/**
	 * This is specific to Background Task only for tool safety control. 
	 * Does not enable/disable tool request output. 
	 * DO NOT USE IT outside Background Task
	 */
	public void grindingStopNoRequest() {
		SMC_IO.setSMC_DO01A_GrinderValve(false);
	}
	
	public void setTool(Tool sunriseTool) {
		setSunriseTool(sunriseTool);
	}
	
	public ObjectFrame setCurrentTCP(EToolName toolName) {
		
		switch (toolName) {
		
		case BallSD51SC:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			break;
		case BallSD6:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			break;
		case BallSD3:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			break;
		case BallSD5SC:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			break;
		case BallWorkingSD51SC:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			this.setCutterName(toolName);
			this.setCutterDiameter(6.35);
			break;
		case BallWorkingSD3:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			this.setCutterName(toolName);
			this.setCutterDiameter(9.6);
			break;
		case BallWorkingSD5SC:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			this.setCutterName(toolName);
			this.setCutterDiameter(12.7);
			break;
		case BallWorkingSD6:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			this.setCutterName(toolName);
			this.setCutterDiameter(15.8);
			break;
			
		case HCR:
			currentTCP = sunriseTool.getFrame(toolName.toString());
			
		default:
			currentTCP = sunriseTool.getFrame("None");
			break;
		}
		return this.getCurrentTCP();
	}
	
	public ObjectFrame getCurrentTCP() {
		return currentTCP;
	}
	
	public Tool getSunriseTool() {
		return sunriseTool;
	}

	public void setSunriseTool(Tool sunriseTool) {
		this.sunriseTool = sunriseTool;
	}

	public double getCutterDiameter() {
		return cutterDiameter;
	}

	public void setCutterDiameter(double cutterDiameter) {
		this.cutterDiameter = cutterDiameter;
		this.setCutterRadius(cutterDiameter/2);
	}

	public double getCutterRadius() {
		return cutterRadius;
	}

	public void setCutterRadius(double cutterRadius) {
		this.cutterRadius = cutterRadius;
	}

	public String getCutterName() {
		return cutterName;
	}

	public void setCutterName(EToolName cutterName) {
		this.cutterName = cutterName.toString();
	}
}
