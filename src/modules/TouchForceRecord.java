package modules;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.linRel;

import java.util.List;
import java.awt.image.*;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.conditionModel.ForceCondition;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.controllerModel.Controller;
import modules.Common;
import modules.Common.searchDir;


public class TouchForceRecord {
	//The idea is to build a class to record robot position upon touching part
	//Class will return Frame as position that was recorded
	//User will define Tool and Base
	private boolean result;
	private Frame position;
	private double offset;
	public TouchForceRecord() {
		//constructor
		result = false;
		position = new Frame(0,0,0,0,0,0);
		offset = 0;
		
	}
	
	public void recordPosition(
			searchDir searchDirection, 		//search direction in TOOL  
			double brakeForce, 				//force in N to stop the search move
			double searchDistance, 			//relative to tool position distance in mm
			double searchVelocity,			//search velocity
			long recordDelay,				//delay in milliseconds (after stop) before LBR record position 
			ObjectFrame currentTcp,			//current TOOL (record position of this TOOL)
			ObjectFrame currentBase,		//current BASE (record in this base coordinates)
			LBR currentLBR) {				//current LBR machine used for motion
		//magic happens here
		
		double Xdistance, Ydistance, Zdistance;
		CoordinateAxis axisForce;
		 
		Xdistance = Ydistance = Zdistance = 0;
		
		switch (searchDirection) {
		case PosX:
			axisForce = CoordinateAxis.X;
			Xdistance = searchDistance;
			break;
		case PosY:
			axisForce = CoordinateAxis.Y;
			Ydistance = searchDistance;
			break;
		case PosZ:
			axisForce = CoordinateAxis.Z;
			Zdistance = searchDistance;
			break;
		case NegX:
			axisForce = CoordinateAxis.X;
			Xdistance = -searchDistance;
			break;
		case NegY:
			axisForce = CoordinateAxis.Y;
			Ydistance = -searchDistance;
			break;
		case NegZ:
			axisForce = CoordinateAxis.Z;
			Zdistance = -searchDistance;
			break;
		default:
			axisForce = CoordinateAxis.X;
			System.out.println("Something went wrong, Force will be searched in X direction !!!");
			break;
		}
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		ForceCondition forceCond = ForceCondition.createNormalForceCondition(currentTcp,axisForce, brakeForce);
		
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		IMotionContainer mc = currentTcp.move(linRel(Xdistance, Ydistance, Zdistance, currentTcp)
				.setMode(mode).setCartVelocity(searchVelocity).breakWhen(forceCond));
		//IMotionContainer mc = currentTcp.move(linRel(Xdistance, Ydistance, Zdistance,0,Math.toRadians(5),0, currentTcp)
		//				.setMode(mode).setCartVelocity(searchVelocity).breakWhen(forceCond));
		if (mc.hasFired(forceCond)) {
			//something was found, register position
			this.position = currentLBR.getCurrentCartesianPosition(currentTcp, currentBase);
			System.out.println("Reference position found <TouchForceRecord>");
			if (recordDelay > 0) {
				System.out.println("Delay of: " + recordDelay + " miliseconds");
				ThreadUtil.milliSleep(recordDelay);
			}
			result = true;
		} else {
			//nothing was found
			System.out.println("Reference position not found");
			result = false;
		}
	}
	
	public Frame getPosition() {
		//return result
		return position;
	}
	
	public void setPosition(Frame position) {
		//return result
		this.position = position;
	}
	
	public boolean getResult() {
		//check if we successfully register position
		return result;
	}
	
}

