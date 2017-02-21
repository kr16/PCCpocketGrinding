package modules;


import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPIBackgroundTask;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKey;
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKeyBar;
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKeyListener;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyAlignment;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyEvent;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyLED;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyLEDSize;

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
public class UserKeys extends RoboticsAPIBackgroundTask {
	private Controller controller;
	private EK1100IOGroup beckhoffIO; 
	private boolean appRunkeyLock;
	
	public void initialize() {
		controller = getController("KUKA_Sunrise_Cabinet_1");
		beckhoffIO = new EK1100IOGroup(controller);
		appRunkeyLock = false;
	}

	public void run() {
		createChooseAppKeyBar();
		while(true){
			ThreadUtil.milliSleep(500);
		}
	}
	
	private void createChooseAppKeyBar() {

		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		IUserKeyBar keybarNutRunner02 = getApplicationUI().createUserKeyBar("BeckhofIO");
			
		IUserKeyListener listenerGrindManualReq = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				if(!StaticGlobals.disableTool) {
					if((arg1==UserKeyEvent.KeyDown)) {
						if (!beckhoffIO.getEK1100_DO01_GrindingToolReq()) {
							arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
							beckhoffIO.setEK1100_DO01_GrindingToolReq(true);
						}else {
							beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
							arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
						}
					}
					if((arg1==UserKeyEvent.KeyUp)) {
						//nothing to do here
					}
				}
			}
		};
		
		IUserKeyListener listenerDisableTool = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				if((arg1==UserKeyEvent.KeyDown)) {
					if(!StaticGlobals.disableTool) {
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "TOOL");
						arg0.setText(UserKeyAlignment.BottomMiddle, "OFF");
						StaticGlobals.disableTool = true;
					} else {						
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "TOOL");
						arg0.setText(UserKeyAlignment.BottomMiddle, "ON");
						StaticGlobals.disableTool = false;
					}
				}
				if(arg1==UserKeyEvent.KeyUp){
					//nothing here;
				}
			}
		};
		
		// Create user keys 
		
		IUserKey grindManualReq = keybarNutRunner02.addUserKey(0, listenerGrindManualReq, true);
		IUserKey disableTool = keybarNutRunner02.addUserKey(1, listenerDisableTool, true);
	
		// Initialize correct state at start
		
		grindManualReq.setText(UserKeyAlignment.TopMiddle, "GRIND");
		grindManualReq.setText(UserKeyAlignment.BottomMiddle, "START");
		grindManualReq.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
		
		disableTool.setText(UserKeyAlignment.TopMiddle, "TOOL");
		disableTool.setText(UserKeyAlignment.BottomMiddle, "ON");
		disableTool.setLED(UserKeyAlignment.Middle, UserKeyLED.Green, UserKeyLEDSize.Normal);
		StaticGlobals.disableTool = false;
		
		// Deysplay keybar
		
		keybarNutRunner02.publish();
		
	}
}
