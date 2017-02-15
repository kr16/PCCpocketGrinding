package modules;


import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISafetyState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.EnablingDeviceState;
import com.kuka.roboticsAPI.deviceModel.LBR;

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

	@Override
	public void initialize() {
		botState = (LBR) getDevice(KUKA_Sunrise_Cabinet_1,
				"LBR_iiwa_14_R820_1");
		currentState = botState.getSafetyState();
		btTool = new GrindingTool(KUKA_Sunrise_Cabinet_1);
		initializeCyclic(0, 500, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		if (currentState.getEnablingDeviceState() == EnablingDeviceState.NONE) {
			//btTool.grindingStop();
		}
	}
}