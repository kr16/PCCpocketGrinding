package modules;


import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISafetyState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.EnablingDeviceState;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.OperationMode;

/**
 * Implementation of a cyclic background task.
 * <p>
 * It provides the {@link RoboticsAPICyclicBackgroundTask#runCyclic} method 
 * which will be called cyclically with the specified period.<br>
 * Cycle period and initial delay can be set by calling 
 * {@link RoboticsAPICyclicBackgroundTask#initializeCyclic} method in the 
 * {@link RoboticsAPIBackgroundTask#initialize()} method of the inheriting 
 * class.<br>
 * The cyclic background task can be terminated via 
 * {@link RoboticsAPICyclicBackgroundTask#getCyclicFuture()#cancel()} method or 
 * stopping of the task.
 * @see UseRoboticsAPIContext
 * 
 */
public class BackgroundTaskToolSafety extends RoboticsAPICyclicBackgroundTask {
	@Inject
	Controller KUKA_Sunrise_Cabinet_1;
	LBR botState;
	ISafetyState currentState; 
	GrindingTool btTool;
	EK1100IOGroup beckhoffIO;

	@Override
	public void initialize() {
		botState = (LBR) getDevice(KUKA_Sunrise_Cabinet_1,
				"LBR_iiwa_14_R820_1");
		beckhoffIO = new EK1100IOGroup(KUKA_Sunrise_Cabinet_1);
		btTool = new GrindingTool(KUKA_Sunrise_Cabinet_1);
		initializeCyclic(0, 10, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		if (botState.getSafetyState().getOperationMode() == OperationMode.T1
				|| botState.getSafetyState().getOperationMode() == OperationMode.T2) {
			if (botState.getSafetyState().getEnablingDeviceState() == EnablingDeviceState.NONE) {
				btTool.grindingStop();
			} else {
				if(beckhoffIO.getEK1100_DO01_GrindingToolReq()) {
					btTool.grindingStartNoRequest();
				}
			}
		}
	}
}