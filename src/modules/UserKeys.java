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
		
		
		
		IUserKeyListener listenerAppStart = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				// Logic for key 0 - HOLDER
				if((arg1==UserKeyEvent.KeyDown)) {

					arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
					beckhoffIO.setEK1100_DO01_GrindingToolReq(true);
					
				}
				
				if((arg1==UserKeyEvent.KeyUp)) {
					beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
					arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Grey, UserKeyLEDSize.Normal);
				}
			}
		};
		
		IUserKeyListener listenerAppRun = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				// Logic for key 0 - HOLDER
				if((arg1==UserKeyEvent.KeyDown)) {
					if(beckhoffIO.getEK1100_DO02() && appRunkeyLock){
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "GRIND");
						arg0.setText(UserKeyAlignment.BottomMiddle, "RUN");
						beckhoffIO.setEK1100_DO02(false);
					}
					if(!beckhoffIO.getEK1100_DO02() && !appRunkeyLock){
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "APP");
						arg0.setText(UserKeyAlignment.BottomMiddle, "RUN");
						beckhoffIO.setEK1100_DO02(true);
					}
				}
				if(arg1==UserKeyEvent.KeyUp){
					appRunkeyLock = !appRunkeyLock;
				}
			}
		};
		
		// Create user keys 
		
		IUserKey appStart = keybarNutRunner02.addUserKey(0, listenerAppStart, true);
		//IUserKey appRun = keybarNutRunner02.addUserKey(1, listenerAppRun, true);
	
		// Initialize correct state at start
		
		appStart.setText(UserKeyAlignment.TopMiddle, "GRIND");
		appStart.setText(UserKeyAlignment.BottomMiddle, "START");
		appStart.setLED(UserKeyAlignment.Middle, UserKeyLED.Grey, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
		
		//appRun.setText(UserKeyAlignment.TopMiddle, "APP");
		//appRun.setText(UserKeyAlignment.BottomMiddle, "PAUSE");
		//appRun.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO02(false);
		
		
		
		// Deysplay keybar
		
		keybarNutRunner02.publish();
		
	}
}
