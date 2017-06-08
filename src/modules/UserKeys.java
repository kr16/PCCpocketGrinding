package modules;


import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1_4valvesonlyIOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPIBackgroundTask;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.EnablingDeviceState;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.ioModel.AbstractIO;
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
	private SMC600_SPN1_4valvesonlyIOGroup SMC_IO;
	private boolean appRunkeyLock;
	private LBR botState;
	private IUserKey myGrindManualStart;
	private AbstractIO doManualGrinderControll;
	
	public void initialize() {
		controller = getController("KUKA_Sunrise_Cabinet_1");
		botState = (LBR) getDevice(controller,"LBR_iiwa_14_R820_1");
		beckhoffIO = new EK1100IOGroup(controller);
		SMC_IO = new SMC600_SPN1_4valvesonlyIOGroup(controller);
		appRunkeyLock = false;
		doManualGrinderControll = SMC_IO.getOutput("SMC_DO01A_GrinderValve");
	}

	public void run() {
		createChooseAppKeyBar();
		while(true){
			if ((botState.getSafetyState().getEnablingDeviceState() != EnablingDeviceState.NONE) && 
					(doManualGrinderControll.getBooleanIOValue()))
			{
				myGrindManualStart.setLED(UserKeyAlignment.Middle, UserKeyLED.Green, UserKeyLEDSize.Normal);	
			} else {
				myGrindManualStart.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
			}

			ThreadUtil.milliSleep(500);
		}
	}
	
	private void createChooseAppKeyBar() {
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		IUserKeyBar keybarNutRunner02 = getApplicationUI().createUserKeyBar("IOs");
		IUserKeyBar keybarHCR = getApplicationUI().createUserKeyBar("HCR");
			
		IUserKeyListener listenerGrindManualReq = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				if(!StaticGlobals.disableTool) {
					if((arg1==UserKeyEvent.KeyDown)) {
						if (botState.getSafetyState().getEnablingDeviceState() != EnablingDeviceState.NONE) {
							if (!doManualGrinderControll.getBooleanIOValue()) {
								SMC_IO.setSMC_DO01A_GrinderValve(true);
								StaticGlobals.grindManualReqKey = true;
							} else {
								SMC_IO.setSMC_DO01A_GrinderValve(false);
								StaticGlobals.grindManualReqKey = false;
							}
						} else {  // key was pushed but dead man was not 
							myGrindManualStart.setText(UserKeyAlignment.TopMiddle, "DEAD");
							myGrindManualStart.setText(UserKeyAlignment.BottomMiddle, "MAN");
							ThreadUtil.milliSleep(750);
							myGrindManualStart.setText(UserKeyAlignment.TopMiddle, "GRIND");
							myGrindManualStart.setText(UserKeyAlignment.BottomMiddle, "START");
							myGrindManualStart.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
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
		
		IUserKeyListener listenerHGteachPos = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {

				if((arg1==UserKeyEvent.KeyDown)) {	
					arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
					beckhoffIO.setEK1100_DO03(true);
				}
				
				if((arg1==UserKeyEvent.KeyUp)) {
					arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red,UserKeyLEDSize.Normal);
					beckhoffIO.setEK1100_DO03(false);
				}

			}
		};
		
		IUserKeyListener listenerHGteachEnd = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {

				if((arg1==UserKeyEvent.KeyDown)) {	
					if (!beckhoffIO.getEK1100_DO04()) {
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
					} else {
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red,UserKeyLEDSize.Normal);
					}
					beckhoffIO.setEK1100_DO04(!beckhoffIO.getEK1100_DO04());
				}
				
				if((arg1==UserKeyEvent.KeyUp)) {
					
				}

			}
		};

		IUserKeyListener listenerHCREnable = new IUserKeyListener() {
			@Override
			public void onKeyEvent(IUserKey arg0, UserKeyEvent arg1) {
				if((arg1==UserKeyEvent.KeyDown)) {
					if(!StaticGlobals.hcrEnable) {
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Green,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "HCR");
						arg0.setText(UserKeyAlignment.BottomMiddle, "ON");
						StaticGlobals.hcrEnable = true;
					} else {						
						arg0.setLED(UserKeyAlignment.Middle, UserKeyLED.Red,UserKeyLEDSize.Normal);
						arg0.setText(UserKeyAlignment.TopMiddle, "HCR");
						arg0.setText(UserKeyAlignment.BottomMiddle, "OFF");
						StaticGlobals.hcrEnable = false;
					}
				}
				if(arg1==UserKeyEvent.KeyUp){
					//nothing here;
				}
			}
		};
		
		
		// Create user keys 
		
		myGrindManualStart = keybarNutRunner02.addUserKey(0, listenerGrindManualReq, true);
		IUserKey disableTool = keybarNutRunner02.addUserKey(1, listenerDisableTool, true);
		IUserKey handGuidingTeachPos = keybarNutRunner02.addUserKey(2, listenerHGteachPos, true);
		IUserKey handGuidingEnd = keybarNutRunner02.addUserKey(3, listenerHGteachEnd, true);
		
		IUserKey hcrEnable = keybarHCR.addUserKey(0, listenerHCREnable, true);
		
		// Initialize correct state at start
		
		myGrindManualStart.setText(UserKeyAlignment.TopMiddle, "GRIND");
		myGrindManualStart.setText(UserKeyAlignment.BottomMiddle, "START");
		myGrindManualStart.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
		
		disableTool.setText(UserKeyAlignment.TopMiddle, "TOOL");
		disableTool.setText(UserKeyAlignment.BottomMiddle, "ON");
		disableTool.setLED(UserKeyAlignment.Middle, UserKeyLED.Green, UserKeyLEDSize.Normal);
		StaticGlobals.disableTool = false;
		
		handGuidingTeachPos.setText(UserKeyAlignment.TopMiddle, "REC");
		handGuidingTeachPos.setText(UserKeyAlignment.BottomMiddle, "POS");
		handGuidingTeachPos.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO03(false);
		
		handGuidingEnd.setText(UserKeyAlignment.BottomMiddle, "DONE");
		handGuidingEnd.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO04(false);
		
		hcrEnable.setText(UserKeyAlignment.TopMiddle, "HCR");
		hcrEnable.setText(UserKeyAlignment.BottomMiddle, "OFF");
		hcrEnable.setLED(UserKeyAlignment.Middle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		beckhoffIO.setEK1100_DO16(false);
		
		// Deysplay keybar
		
		keybarNutRunner02.publish();
		keybarHCR.publish();
	}
}
