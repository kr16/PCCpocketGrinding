package modules;


import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1_4valvesonlyIOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISafetyState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.EnablingDeviceState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.SafetyStopType;
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
	SMC600_SPN1_4valvesonlyIOGroup SMC_IO;

	@Override
	public void initialize() {
		botState = (LBR) getDevice(KUKA_Sunrise_Cabinet_1,
				"LBR_iiwa_14_R820_1");
		beckhoffIO = new EK1100IOGroup(KUKA_Sunrise_Cabinet_1);
		SMC_IO = new SMC600_SPN1_4valvesonlyIOGroup(KUKA_Sunrise_Cabinet_1);
		btTool = new GrindingTool(KUKA_Sunrise_Cabinet_1);
		initializeCyclic(0, 10, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		//if tool is not disabled
		if (!StaticGlobals.disableTool) {
			//if T1 or T2
			
			if (botState.getSafetyState().getOperationMode() == OperationMode.T1
					|| botState.getSafetyState().getOperationMode() == OperationMode.T2) {
				//if dead man NOT enabled
				if (botState.getSafetyState().getEnablingDeviceState() == EnablingDeviceState.NONE) {
					//stop tool drop app request 
					btTool.grindingStop();
				}
//				} else {
//					//if app request present start tool 
//					if(beckhoffIO.getEK1100_DO01_GrindingToolReq()) {
//						btTool.grindingStartNoRequest();
//					}
//				}
			}
			//if AUT
			if(botState.getSafetyState().getOperationMode() == OperationMode.AUT) {
				if (StaticGlobals.grindManualReqKey) {
					btTool.grindingStartNoRequest();
				} else {
					if (!SMC_IO.getSMC_DO01A_GrinderValve()) {
						StaticGlobals.grindManualReqKey = false;
						btTool.grindingStop();
					}
				}

			}
			//if safety signal is STOP1 or STOP0 turn tool off, leave request active
			if((botState.getSafetyState().getSafetyStopSignal() == SafetyStopType.STOP0) || 
				(botState.getSafetyState().getSafetyStopSignal() == SafetyStopType.STOP1)) {
				//stop tool drop app request 
				btTool.grindingStopNoRequest();
			}
			
		} else {
			btTool.grindingStop();
		}
	}
}
	