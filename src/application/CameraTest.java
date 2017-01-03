package application;


import javax.inject.Inject;

import modules.TimerKCT;

import com.kuka.generated.ioAccess.CognexProfinetIOGroup;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class CameraTest extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool HotDotTest;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
    private TimerKCT timer;
    private CognexProfinetIOGroup CognexIO;
    private Controller kuka_Sunrise_Cabinet_1;
    
	@Override
	public void initialize() {
		// initialize your application here
		HotDotTest = getApplicationData().createFromTemplate("HotDotEEnoVRSI");
		nullBase = getApplicationData().getFrame("/nullBase");
		currentTCP = HotDotTest.getFrame("Iron");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		CognexIO = new CognexProfinetIOGroup(kuka_Sunrise_Cabinet_1);
	}

	@Override
	public void run() {
		// your application execution starts here
		//bot.move(ptpHome());
		
		CognexIO.setAcqCtrlReg0(false);
		CognexIO.setAcqCtrlReg1(false);
		CognexIO.setAcqCtrlReg7(false);
		System.out.println("reset completed: " + CognexIO.getAcqCtrlReg0() + " " + CognexIO.getAcqCtrlReg1() + " " + CognexIO.getAcqCtrlReg7());
		
	}
}