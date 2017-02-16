package application;


import javax.inject.Inject;

import modules.Common.EToolName;
import modules.GrindingTool;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.sun.org.apache.bcel.internal.generic.NEW;

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
	private Controller kuka_Sunrise_Cabinet_1;
	private Tool PCC_EE;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos, centerPos;
	private ObjectFrame referencePos;
	
	private GrindingTool eeTool;

	@Override
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		PCC_EE = getApplicationData().createFromTemplate("PccGrinderVer01");
		eeTool = new GrindingTool(kuka_Sunrise_Cabinet_1);
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
			ThreadUtil.milliSleep(10000);
			eeTool.grindingStop();
			ThreadUtil.milliSleep(2000);
		}
	}
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(0), Math.toRadians(10),
				Math.toRadians(0), Math.toRadians(-115), Math.toRadians(0), Math.toRadians(-25), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
	@Override
    public void dispose()
    {
        try {
        	eeTool.grindingStop();
        } catch (NullPointerException e ) {
        	System.err.println("Sacrebleu");
        }
        finally
        {
            super.dispose();
        }
    }
	
}