package application;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.naming.LimitExceededException;

import sun.rmi.log.ReliableLog.LogFile;

import modules.Common.ESearchDirection;
import modules.Common.EToolName;
import modules.GrindingTool;
import modules.TimerKCT;
import modules.TouchForceRecord;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
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
	private ObjectFrame startProcess;
	private ObjectFrame appRightCoupon;
	
	//custom
	private PrintWriter logFile;
	private GrindingTool eeTool;
	private TouchForceRecord searchPart;
	private Thread TimerThread;
	private TimerKCT grindingProcessTimer;
	
	@Override
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		PCC_EE = getApplicationData().createFromTemplate("PccGrinderVer01");
		eeTool = new GrindingTool(kuka_Sunrise_Cabinet_1);
		nullBase = getApplicationData().getFrame("/nullBase");
		startProcess = getApplicationData().getFrame("/nullBase/StartProcess");
		appRightCoupon = getApplicationData().getFrame("/nullBase/appRightCoupon");
		searchPart = new TouchForceRecord();
		grindingProcessTimer = new TimerKCT();
		TimerThread = new Thread(grindingProcessTimer);
		logFile = null;
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
		//File logging setup //////////////////////////////
		SimpleDateFormat prefixDateFormat;
		prefixDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String filePathRoot="d:/Transfer/UserXMLs/";
		try {
			logFile = new PrintWriter(filePathRoot + prefixDateFormat.format(Calendar.getInstance().getTime()) 
										+ "_" + "PCCgrinding.csv", "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		////////////////////////////////////////////////////
		
		// set home position, attach tool , move home
		setNewHomePosition();
		PCC_EE.attachTo(bot.getFlange());
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		eeTool.setTool(PCC_EE);
		currentTCP = eeTool.setCurrentTCP(EToolName.BallWorking);
		
		System.out.println("Moving to approach coupon position");
		currentTCP.moveAsync(ptp(appRightCoupon).setJointVelocityRel(0.3));
		
		double	drillOffset = 15;	//mm
		int		drillColumn = 13;
		int     drillRow = 1;
		Frame startOffsetted = startProcess.copy().setY(startProcess.copy().getY() - drillColumn*drillOffset);
		
		currentTCP.move(ptp(startOffsetted).setJointVelocityRel(0.3));
		searchPart.recordPosition(ESearchDirection.PosX, 5, 10, 1, 0, currentTCP, nullBase, bot);
		if (searchPart.getResult()) {
			TimerThread.start();
			Frame atPart = searchPart.getPosition();
			currentTCP.move(lin(startOffsetted).setCartVelocity(10));
			eeTool.grindingStart();
			grindingProcessTimer.setTimerValue(0);
			grindingProcessTimer.timerStart();
			grindingProcess(atPart);
			grindingProcessTimer.timerStop();
			String processTimer = "Process timer: " + (grindingProcessTimer.getTimerValue()/1000) + "s";
			System.out.println(processTimer);
			currentTCP.move(lin(atPart).setCartVelocity(1));
			eeTool.grindingStop();
			depthMeasure(atPart);
			
		} else {
			throw new ArithmeticException("No part detected, adjust start position , restart program");
		}
		
		currentTCP.move(lin(startOffsetted).setCartVelocity(10));
		
		System.out.println("Moving to approach coupon position");
		currentTCP.moveAsync(ptp(appRightCoupon).setJointVelocityRel(0.3));
		
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
	}
	
	public void grindingProcess(Frame atPart) {		
/*
 The maximum deflection DeltaX is the deviation from the original path in the positive
		and negative X directions. The maximum deflection is determined by the
		stiffness and amplitude which are defined for the impedance controller in the
		Cartesian X direction, e.g.:
		 - Cartesian stiffness: C = 500 N/m
		 - Amplitude: F = 5 N
		The maximum deflection results from Hooke’s law:
		DeltaX = F / C = 5 N / (500 N/m) = 1 / (100 1/m) = 1 cm
		The wavelength can be used to determine how many oscillations the robot is
		to execute between the start point and end point of the motion. The wavelength
		is determined by the frequency which is defined for the impedance controller
		with overlaid force oscillation, as well as by the programmed robot
		velocity.
		Wavelength "Lambda" is calculated as follows:
		Lambda = c / f = robot velocity / frequency
*/
		
		double sineFrequency = 2;
		double sineAmplitude = 40;
		double sineStiffness = 5000;
		
		CartesianSineImpedanceControlMode modeSine;
		modeSine = CartesianSineImpedanceControlMode.createSinePattern(CartDOF.Z, sineFrequency, sineAmplitude, sineStiffness);
		
		double handForce = 10;
		double stiffness = 4000;
		double travelDistance = 8;		//mm
		double velocity = 0.07;
		
		logFile.println("SineFrequency: " 	+ sineFrequency 
						+ " SineAmplitude: "  + sineAmplitude 
						+ " SineStiffness: " + sineStiffness);
		
		logFile.println("X(working direction)Stiffness: " + stiffness 
						+ " Additional Force: " + handForce
						+ " Travel distance: " + travelDistance
						+ " Velocity: " + velocity); 
		
		modeSine.parametrize(CartDOF.Y).setStiffness(5000);
		modeSine.parametrize(CartDOF.X).setStiffness(stiffness).setBias(handForce);
		
		CartesianSineImpedanceControlMode lissajousMode;
		lissajousMode = CartesianSineImpedanceControlMode.createLissajousPattern(CartPlane.YZ, 10.0, 50.0, 500.0);
		lissajousMode.parametrize(CartDOF.X).setStiffness(stiffness).setBias(handForce);
		
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(4500).setDamping(1);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		mode.parametrize(CartDOF.X).setStiffness(stiffness);
		currentTCP.move(lin(atPart).setCartVelocity(velocity*5).setMode(mode));
		
		//mode.parametrize(CartDOF.X).setStiffness(4500).setAdditionalControlForce(handForce);
		currentTCP.move(linRel(travelDistance, 0, 0, currentTCP).setMode(lissajousMode).setCartVelocity(velocity));
		
	}
	
	public void depthMeasure(Frame atPart) {
		double startX = atPart.getX();
		searchPart.recordPosition(ESearchDirection.PosX, 20, 10, 2, 0, currentTCP, nullBase, bot);
		double stopX = searchPart.getPosition().getX();
		String grindingDepth = "Grinding depth = " + (stopX - startX) + "mm";
		System.out.println(grindingDepth);
		logFile.println(grindingDepth);
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
        	grindingProcessTimer.timerStopAndKill();
        } catch (NullPointerException e ) {
        	System.err.println("Sacrebleu");
        }
        finally
        {
            super.dispose();
        }
    }
	
}