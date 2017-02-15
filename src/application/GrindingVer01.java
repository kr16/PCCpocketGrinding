package application;


import javax.inject.Inject;

import modules.Common.EToolName;
import modules.GrindingTool;

import com.kuka.common.ThreadUtil;
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
public class GrindingVer01 extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool PCC_EE;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos, centerPos;
	private ObjectFrame referencePos;
	
	private GrindingTool eeTool;

	@Override
	public void initialize() {
		PCC_EE = getApplicationData().createFromTemplate("PccGrinderVer01");
		nullBase = getApplicationData().getFrame("/nullBase");
		//set current TCP here
		currentTCP = eeTool.setToolName(PCC_EE, EToolName.Ball);
	}

	@Override
	public void run() {
		
		// set home position, attach tool , move home
		setNewHomePosition();
		PCC_EE.attachTo(bot.getFlange());
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		while (true) {
			eeTool.grindingStart();
			ThreadUtil.milliSleep(5000);
			eeTool.grindingStop();
			ThreadUtil.milliSleep(2000);
		}
	}
	
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(0), Math.toRadians(-20),
				Math.toRadians(0), Math.toRadians(-105), Math.toRadians(0), Math.toRadians(60), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
}