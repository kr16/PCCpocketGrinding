package modules;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.linRel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.OperationMode;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;

public class SinusMode extends RoboticsAPIApplication {



	private double additionalZForce;
	private double zProgress;
	private double zStiffness;
	private double travelVelocity;		//	mm/s this overwrites PLC var
	private double travelDistance;	//	mm	this overwrites PLC var
	private long totalTime;	//	s 	this overwrites PLC var
	private double expectedDepth;	//how deep robot is expected to grind
	private boolean timeOut;

	//List<GlobalVars> data = new ArrayList<GlobalVars>();
	List<Double> depthPosArray = new ArrayList<Double>();

	private TimerKCT forceTimer;

	public SinusMode (	double additionalZForce,
			double zProgress,
			double travelDistance,
			double travelVelocity,
			long totalTime,
			CartDOF oscillationAxis,
			double expectedDepth,
			double zStiffness)
	{
				this.setAdditionalZForce(additionalZForce);
				this.setzProgress(zProgress);
				this.setTravelDistance(travelDistance);
				this.setTravelVelocity(travelVelocity);
				this.setTotalTime(totalTime);
				this.setExpectedDepth(expectedDepth);
				this.setzStiffness(zStiffness);	
				//this.init();
	}

	public void executeMode(	final GrindingTool eeTool, 		//currentTCP to execute motion
								final Frame atPart, 			//start position recorded
								final LBR bot, 						//actual robot object to read its position
								TimerKCT grindingProcessTimer, 	//process global timer
								PrintWriter logFile) 			//process global logfile
	{
		
		ObjectFrame currentTCP = eeTool.getCurrentTCP();
		CartesianSineImpedanceControlMode sineMode;
		//set Spiral oscillation parameters
		sineMode = CartesianSineImpedanceControlMode.createSinePattern(CartDOF.Y, 1, 1, 3000);
		//set work direction force parameters
		sineMode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		sineMode.parametrize(CartDOF.ROT).setStiffness(300);
		sineMode.parametrize(CartDOF.Z).setBias(-getAdditionalZForce()).setStiffness(getzStiffness());
		sineMode.setStayActiveUntilPatternFinished(true);

		//Max force at which we should quit
		ForceComponentCondition TCPforce;
		double maxForce = 60;
		TCPforce = new ForceComponentCondition(currentTCP,CoordinateAxis.Z, -maxForce, maxForce);

		////////////////// logging ///////////////////
		logFile.println("Mode: sinusMode");
		
		if(StaticGlobals.disableTool) {
			logFile.println("EndEffector disabled - DRY RUN ONLY");
		}
		logFile.println("Mode: Sine");
		logFile.println("Frequency: " 	+ 1 
				+ " Amplitude: "  + 1 
				+ " PlaneStiffness: " + getAdditionalZForce()
				+ " Max deflection: " + (1/getAdditionalZForce()*1000) + "mm");
		logFile.println("Oscillation time: " + getTotalTime() + "seconds,  this is motion cut off time too");
		logFile.println("Working direction stiffness: " + getzStiffness() 
				+ " Additional Force: " + getAdditionalZForce());
		logFile.println("TCP Travel distance : " + travelDistance
				+ "mm, TCP Velocity: " + travelVelocity + " mm/s");
		logFile.println("Theoretical TCP travel time: " + travelDistance/travelVelocity + " seconds");
		logFile.println("Max force for stopping grinding: " + maxForce);

		///////////////////////////////////////////////


		//Move to part running
		/////////////////////////////////////////////////////////////////////////////////////////
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		double atPartVelocity;
		if (bot.getSafetyState().getOperationMode() == OperationMode.T1) {
			atPartVelocity = 2;
		} else {
			atPartVelocity = 0.2;
		}
		currentTCP.move(lin(atPart).setCartVelocity(atPartVelocity).setMode(mode));

		grindingProcessTimer.setTimerValue(0);	//timer set to 0
		grindingProcessTimer.timerStart();		//timer start

		//////////////////////Grinding happens here////////////////////


		///////////////////////////Simple attempt drive forward with force and spring/////////////////////
		logFile.println();
		logFile.println("SIMPLE ATTEMPT TO DRIVE FORWARD, IGNORE ALL DATA ABOVE !!!");
		double additionalForce = 20;
		double addForceOverTime = 3;
		double forceTimePeriod = 20;
		double zPos = 0.1;
		double zOffset = 0;
		double zStiffenss = 4500;
		travelVelocity = 3;		//	mm/s this overwrites PLC var
		travelDistance = 5;	//	mm	this overwrites PLC var
		double totalTimeSecs = 100.0;	//	s 	this overwrites PLC var
		logFile.println("ACTUAL DATA FOR SIMPLE ATTEMPT:");
		logFile.println("travel distance: " + travelDistance);
		logFile.println("travel velocity: " + travelVelocity);
		logFile.println("force in Z: " + additionalForce);
		logFile.println("Z stiffness: " + zStiffenss);
		logFile.println("Z travel increment: " + zPos);
		logFile.println("Theoretical Total time: " + totalTimeSecs + "s");
		logFile.println("Theoretical depth: " + totalTimeSecs*travelVelocity/travelDistance*zPos + "mm");

		CartesianImpedanceControlMode modeLine = new CartesianImpedanceControlMode();
		modeLine.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);
		modeLine.parametrize(CartDOF.ROT).setStiffness(300);
		modeLine.parametrize(CartDOF.Z).setStiffness(zStiffenss);
		modeLine.parametrize(CartDOF.Z).setAdditionalControlForce((-1) * additionalForce);
		System.out.println("Sine oscillation running");
		boolean bConditionResult = false;
		boolean bTimerFlag = false;
		currentTCP.move(linRel(-travelDistance/2, 0, -zPos,currentTCP).setCartVelocity(travelVelocity).setMode(modeLine));
		//forceTimerThread.start();
		forceTimer.setTimerValue(0);
		forceTimer.timerStart();
		while (!bConditionResult) {

			currentTCP.move(linRel(travelDistance, 0, -zPos,currentTCP).setCartVelocity(travelVelocity).setMode(modeLine));

			currentTCP.move(linRel(-travelDistance, 0, -zPos,currentTCP).setCartVelocity(travelVelocity).setMode(modeLine));

			zOffset = zOffset + 2*zPos;
			if(forceTimer.getTimerValue()/1000 > forceTimePeriod) {
				forceTimer.setTimerValue(0);
				additionalForce = additionalForce + addForceOverTime;
				//modeLine.parametrize(CartDOF.Z).setAdditionalControlForce((-1) * additionalForce);
				System.out.println(		"Theoretical Z position offset = " + zOffset
						//String.format("%.2f", Double.valueOf(sineTravelDistance.getText())
						+	" Current Pos Z: " + String.format("%.2f", bot.getCurrentCartesianPosition(currentTCP).getX())
						+	" Real timer: " + grindingProcessTimer.getTimerValue()/1000
						+	" Force: " + additionalForce);
			}
			if((grindingProcessTimer.getTimerValue()/1000 > totalTimeSecs)) {
				bConditionResult = true;
				forceTimer.timerStop();
				System.out.println("Sine osciallation finished");
			}
		}

		//record position before we do anything
		Frame offsetedPos = atPart.copy();
		Frame grindEndPosOffset = bot.getCurrentCartesianPosition(currentTCP, offsetedPos);
		System.out.println("Offseted Pos: " + grindEndPosOffset);
		logFile.println("Offseted Pos: " + grindEndPosOffset);

		//get offsets
		double xOffsetAfterCut = grindEndPosOffset.getX();
		double yOffsetAfterCut = grindEndPosOffset.getY();
		double zOffsetAfterCut = grindEndPosOffset.getZ();

		System.out.println("Robot measured cut depth: " + String.format("%.2f",Math.abs(zOffsetAfterCut)) + "mm");
		logFile.println("Robot measured cut depth: " + Math.abs(zOffsetAfterCut) + "mm");

		//condition which way we offset depending on cut direction (X or Y)
		//Transformation tcpShift = Transformation.ofTranslation(xOffsetAfterCut, 0.0, 0.0);
		Transformation tcpShift = Transformation.ofTranslation(0.0, yOffsetAfterCut, 0.0);
		offsetedPos.transform(tcpShift);


	}
	
	public double getTravelVelocity() {
		return travelVelocity;
	}

	public void setTravelVelocity(double travelVelocity) {
		this.travelVelocity = travelVelocity;
	}

	public double getTravelDistance() {
		return travelDistance;
	}

	public void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}

	public double getAdditionalZForce() {
		return additionalZForce;
	}

	public void setAdditionalZForce(double additionalZForce) {
		this.additionalZForce = additionalZForce;
	}

	public double getzProgress() {
		return zProgress;
	}

	public void setzProgress(double zProgress) {
		this.zProgress = zProgress;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public double getzStiffness() {
		return zStiffness;
	}

	public void setzStiffness(double zStiffness) {
		this.zStiffness = zStiffness;
	}
	
	
	public double getExpectedDepth() {
		return expectedDepth;
	}

	public void setExpectedDepth(double expectedDepth) {
		this.expectedDepth = expectedDepth;
	}

	public boolean isTimeOut() {
		return timeOut;
	}

	public void setTimeOut(boolean timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		try {
			
		}
		catch (NullPointerException e) {
			System.err.println("Sacrebleu");
			e.printStackTrace();
		}
		finally {
			super.dispose();
		}
	}
}
