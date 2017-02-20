package application;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.naming.LimitExceededException;

import modules.Common.ESearchDirection;
import modules.Common.EToolName;
import modules.GrindingTool;
import modules.TouchForceRecord;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.motionModel.PositionHold;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
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
	private ObjectFrame startProcess, centerPos;
	private ObjectFrame referencePos;
	
	private GrindingTool eeTool;
	private TouchForceRecord searchPart;
	
	@Override
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		PCC_EE = getApplicationData().createFromTemplate("PccGrinderVer01");
		eeTool = new GrindingTool(kuka_Sunrise_Cabinet_1);
		nullBase = getApplicationData().getFrame("/nullBase");
		startProcess = getApplicationData().getFrame("/nullBase/StartProcess");
		searchPart = new TouchForceRecord();
	}

	@Override
	public void run() {
		
		//check for robot referencing and run referncing program if needed
//		if (!referencingStateCheck(bot)) {
//			System.out.println("Running position and GSM reference program");
//			//how do I execute a different java program??? 
//			PositionAndGMSReferencing posAndGSMapp = new PositionAndGMSReferencing();
//			posAndGSMapp.initialize();
//			posAndGSMapp.run();
//		}
		
		// set home position, attach tool , move home
		setNewHomePosition();
		PCC_EE.attachTo(bot.getFlange());
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		eeTool.setTool(PCC_EE);
		eeTool.setCurrentTCP(EToolName.BallWorking);
		
		currentTCP.move(ptp(startProcess).setJointVelocityRel(0.3));
		searchPart.recordPosition(ESearchDirection.PosX, 5, 10, 10, 0, currentTCP, nullBase, bot);
		if (searchPart.getResult()) {
			Frame atPart = searchPart.getPosition();
			currentTCP.move(lin(startProcess).setCartVelocity(10));
			//eeTool.grindingStart();
			grindingProcess(atPart);
			eeTool.grindingStop();
		} else {
			throw new ArithmeticException("No part detected, adjust start position , restart program");
		}
		
		currentTCP.move(lin(startProcess).setCartVelocity(10));
		
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
	}
	
	public void grindingProcess(Frame atPart) {
		double frequency = 1;
		double amplitude = 5;
		double stiffness = 4000;
		double handForce = 10;
		double travelDistance = 4.5;		//mm
		double velocity = 0.2;
		
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		mode.parametrize(CartDOF.X).setStiffness(stiffness).setAdditionalControlForce(handForce);
		currentTCP.move(lin(atPart).setCartVelocity(velocity).setMode(mode));
		currentTCP.move(linRel(travelDistance, 0, 0, currentTCP).setMode(mode).setCartVelocity(velocity));
		
	}
	
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(0), Math.toRadians(10),
				Math.toRadians(0), Math.toRadians(-115), Math.toRadians(0), Math.toRadians(-25), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
	
	private boolean referencingStateCheck(LBR bot) {
		String torqueReferencing = null;
		String positionReferencing = null;
		boolean bTorqueReferencing = true;
		boolean bPositionReferencing = true;
		
		if (!bot.getSafetyState().areAllAxesGMSReferenced()) {
			//check if all joints are torque referenced
			torqueReferencing = "Not all joint are torque referenced";
			bTorqueReferencing = false;
			System.err.println(torqueReferencing);
		}
		if (!bot.getSafetyState().areAllAxesPositionReferenced()) {
			//check if all joints are position referenced
			positionReferencing = "Not all joints are position referenced";
			bPositionReferencing = false;
			System.err.println(positionReferencing);
		}
		if (!bTorqueReferencing || !bPositionReferencing) {
			return false;
		} else {
			return true;
		}
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