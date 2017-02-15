package modules;

import modules.Common.EToolName;

import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

public class GrindingTool {
	private SMC600_SPN1IOGroup toolIO;
	private EToolName toolName;
	
	public GrindingTool(Controller controller) {
		toolIO = new SMC600_SPN1IOGroup(controller);
	    this.setToolName(EToolName.None);
	}
	
	public void grindingStart() {
		toolIO.setDO08_1_GrinderAir1(true);
		toolIO.setDO09_1_GrinderAir2(true);
	}
	
	public void grindingStop() {
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
