package tests;


import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.JointPosition;
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
public class SpiralMotionTest extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool HotDotTest;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;

	@Override
	public void initialize() {
		// initialize your application here
		HotDotTest = getApplicationData().createFromTemplate("HotDotEE");
		nullBase = getApplicationData().getFrame("/nullBase");
		currentTCP = HotDotTest.getFrame("IronVer01");
	}

	@Override
	public void run() {
		setNewHomePosition();
		HotDotTest.attachTo(bot.getFlange());
		
		//bot home
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
	}
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(-90), Math.toRadians(56),
				0.0, Math.toRadians(-100), 0.0, Math.toRadians(-43), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
}