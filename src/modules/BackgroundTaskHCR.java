package modules;


import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;

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
public class BackgroundTaskHCR extends RoboticsAPICyclicBackgroundTask {
	@Inject
	Controller KUKA_Sunrise_Cabinet_1;
	EK1100IOGroup beckhoffIO;
	
	@Override
	public void initialize() {
		// initialize your task here
		initializeCyclic(0, 100, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
		beckhoffIO = new EK1100IOGroup(KUKA_Sunrise_Cabinet_1);
	}

	@Override
	public void runCyclic() {
		// your task execution starts here
		//Output 16 on EK1100 enables power to dual channel relay that fakes X11 
		//into HCR mode (single digital out enables -> dual safe A B circuit to X11)
		//Input 3 on EK1100 comes from HCR button on the handle
		if (StaticGlobals.hcrEnable) {
			if (beckhoffIO.getEK1100_DI03()) {
				beckhoffIO.setEK1100_DO16(true);
			} else {
				beckhoffIO.setEK1100_DO16(false);
			}
		}
	}
}