package modules;


import java.util.concurrent.TimeUnit;

import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
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
 */
public class BackgroundTaskDispencer extends RoboticsAPICyclicBackgroundTask {
	private Controller kuka_Sunrise_Cabinet_1;
	private SMC600_SPN1IOGroup dispenserIO;
	private boolean getHotDotdispense;
	private XmlParserGlobalVarsRD globalFromPLC, globalFromKRC;
	
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		initializeCyclic(0, 500, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
		dispenserIO = new SMC600_SPN1IOGroup(kuka_Sunrise_Cabinet_1);
		getHotDotdispense = false;
		String globalsFilePath = "d:/Transfer/UserXMLs/";
		String globalsFileNamePLC = "GlobalVarsHotDotPLC.xml";
		String globalsFileNameKRC = "GlobalVarsHotDotKRC.xml";
		globalFromPLC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNamePLC);
		globalFromKRC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNameKRC);
	}
	

	public void runCyclic() {
		
		if (!globalFromPLC.getVarBoolean("stopDispencer")) {
			
			if (!dispenserIO.getDI01_HotDotPresent_X101() && !getHotDotdispense) {
				ThreadUtil.milliSleep(2000);
				System.out.println("got to here");
				if (!dispenserIO.getDI01_HotDotPresent_X101() && !getHotDotdispense) {
					if (!dispenserIO.getDI01_HotDotPresent_X101()) {
						if(!dispenserIO.getDI04_DispancerAtHome()) {
							dispenserIO.setDO11_1_PneumaticCylinder(false);
							ThreadUtil.milliSleep(2000);
							while (!dispenserIO.getDI04_DispancerAtHome()) {
								//wait for piston to get home
							}
						}
						dispenserIO.setDO11_1_PneumaticCylinder(true);
						//System.out.println("Hot Dot requested");
						while (!(dispenserIO.getDI03_DispencerAtPlace() && dispenserIO.getDI01_HotDotPresent_X101())) {
							//wait for hot dot present
						}
						ThreadUtil.milliSleep(500);
						dispenserIO.setDO11_1_PneumaticCylinder(false);
						ThreadUtil.milliSleep(2000);
						if (dispenserIO.getDI02_HotDotDispencerLow_X102()) {
							globalFromKRC.setVar("dispenserLowOnHotDots","true");
						} else {
							globalFromKRC.setVar("dispenserLowOnHotDots","false");
						}
						
					} else {
					//System.out.println("Hot Dot already present");
					}
				}	
			}
		}
	}
		
}
	
