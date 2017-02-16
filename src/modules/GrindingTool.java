package modules;

import modules.Common.EToolName;

import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

public class GrindingTool {
	private SMC600_SPN1IOGroup toolIO;
	private EK1100IOGroup beckhoffEcatIO;
	private EToolName toolName;
	
	public GrindingTool(Controller controller) {
		toolIO = new SMC600_SPN1IOGroup(controller);
		beckhoffEcatIO = new EK1100IOGroup(controller);
	    this.setToolName(EToolName.None);
	}
	
	/**
	 * Enables grinding tool valves - both of them - full speed
	 * WATCH YOUR HANDS/EYES !!!
	 */
	public void grindingStart() {
		toolIO.setDO08_1_GrinderAir1(true);
		toolIO.setDO09_1_GrinderAir2(true);
		beckhoffEcatIO.setEK1100_DO01_GrindingToolReq(true);
	}
	
	/**
	 * Enables grinding tool valve - one valve - half speed TEST ONLY
	 * WATCH YOUR HANDS/EYES !!!
	 */
	public void grindingStartHalfSpeed() {
		toolIO.setDO08_1_GrinderAir1(true);
		beckhoffEcatIO.setEK1100_DO01_GrindingToolReq(true);
	}
	
	/**
	 * This is specific to Background Task only for tool safety control. 
	 * Does not enable/disable tool request output. 
	 * DO NOT USE IT outside Background Task
	 */
	public void grindingStartNoRequest() {
		toolIO.setDO08_1_GrinderAir1(true);
		toolIO.setDO09_1_GrinderAir2(true);
	}
	
	/**
	 * Disable grinding tool
	 */
	public void grindingStop() {
		toolIO.setDO08_1_GrinderAir1(false);
		toolIO.setDO09_1_GrinderAir2(false);
		beckhoffEcatIO.setEK1100_DO01_GrindingToolReq(false);
	}
	
	/**
	 * This is specific to Background Task only for tool safety control. 
	 * Does not enable/disable tool request output. 
	 * DO NOT USE IT outside Background Task
	 */
	public void grindingStopNoRequest() {
		toolIO.setDO08_1_GrinderAir1(false);
		toolIO.setDO09_1_GrinderAir2(false);
	}
	
	public ObjectFrame setToolName(Tool sunriseTool, EToolName toolName) {
		ObjectFrame currentTCP;
		
		switch (toolName) {
		case Ball:
			currentTCP = sunriseTool.getFrame("Ball");
			break;
		case Cone:
			currentTCP = sunriseTool.getFrame("Cone");
			break;

		default:
			currentTCP = sunriseTool.getFrame("None");
			break;
		}
		return currentTCP;
	}
	
	private EToolName getToolName() {
		return toolName;
	}

	private void setToolName(EToolName toolName) {
		this.toolName = toolName;
	}
	
}
