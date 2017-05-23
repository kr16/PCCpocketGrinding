package modules;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.linRel;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kuka.common.ThreadInterruptedException;
import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.conditionModel.ICallbackAction;
import com.kuka.roboticsAPI.conditionModel.MotionPathCondition;
import com.kuka.roboticsAPI.conditionModel.ObserverManager;
import com.kuka.roboticsAPI.conditionModel.ReferenceType;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.OperationMode;
import com.kuka.roboticsAPI.executionModel.IFiredTriggerInfo;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
import com.kuka.roboticsAPI.sensorModel.DataRecorder;
import com.kuka.task.RoboticsAPITask;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
public class SimpleMode extends RoboticsAPIApplication {

	///////////////////////////Simple attempt drive forward with force and spring/////////////////////
	
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
	
	public SimpleMode (	double additionalZForce,
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
		this.init();
	}
	
	private void init() {
		this.setTimeOut(false);
	}
	
	/**
	 * @param currentTCP - motion tcp
	 * @param atPart - start position at Part
	 * @param bot - LBR robot object for position recording
	 * @param grindingProcessTimer - global timer
	 * @param logFile - global logfile
	 * @return Frame - robot start position (at Part) offseted by grinding motion on X or Y 
	 */
	public Frame executeMode(	final GrindingTool eeTool, 		//currentTCP to execute motion
								final Frame atPart, 			//start position recorded
								final LBR bot, 						//actual robot object to read its position
								TimerKCT grindingProcessTimer, 	//process global timer
								PrintWriter logFile) 			//process global logfile
	{
		ObjectFrame currentTCP = eeTool.getCurrentTCP();
		//local timer init 
		//forceTimer = new TimerKCT();
		//Thread forceTimerThread = new Thread(forceTimer);
		//forceTimerThread.start();
		
		//data recorder setup
		DataRecorder recData = new DataRecorder();
		Calendar myDate = Calendar.getInstance(); // date and time
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss"); // formated to your likings
		recData.setFileName("PCC_"
				+ mySdf.format(myDate.getTime()) + "_log.csv"); // file name to write to
		recData.setTimeout(-1, TimeUnit.SECONDS); // no timeout
		recData.setSampleInterval(100);
		recData.addCartesianForce(currentTCP, null);
		recData.addCurrentCartesianPositionXYZ(currentTCP, atPart);
		recData.enable(); // enable Data recorder
		
		double zOffset = 0;				//adds up travel distance
		int loopCounter = 0;			//counts how many times TCP went through cycle (down/up)
		
		double cutDown = 1;
		double cutUp = 0;
		double downOn = 1;
		double upOn = 1;
		double zDown;
		double zUp;
		double forceDown;
		double forceUp;
		double pathPercetage = 50;
		
		StringBuilder depthProgress = new StringBuilder("Depth Progress: ");
		StringBuilder timeProgress = new StringBuilder("Time Progress: ");
		StringBuilder depthPerPath = new StringBuilder("Depth per pass: ");
		
		//Logging
		logFile.println("Mode: simpleMode");
		logFile.println("travel distance: " + getTravelDistance());
		logFile.println("travel velocity: " + getTravelVelocity());
		logFile.println("force in Z: " + getAdditionalZForce());
		logFile.println("Z stiffness: " + getzStiffness());
		logFile.println("Z travel increment: " + getzProgress());
		logFile.println("Theoretical Total time: " + getTotalTime() + "s");
		logFile.println("Theoretical depth: " + getTotalTime() * getTravelVelocity() / getTravelDistance() * getzProgress() + "mm");
	
		//ESM 1 activate
		//no A1 low torque monitoring 
		System.out.println("ESM 1 active");
		bot.setESMState("1");
		
		recData.startRecording();
		
		//Move to part running
		CartesianImpedanceControlMode atPartmode = new CartesianImpedanceControlMode();
		atPartmode.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);
		atPartmode.parametrize(CartDOF.ROT).setStiffness(300);
		double atPartVelocity;
		if (bot.getSafetyState().getOperationMode() == OperationMode.T1) {
			atPartVelocity = 2;
		} else {
			atPartVelocity = 0.4;
		}
		currentTCP.move(lin(atPart).setCartVelocity(atPartVelocity));
		ThreadUtil.milliSleep(500);
		
		///////////Trigger definition for depth checking/////////////////////
		ICallbackAction checkDepth = new ICallbackAction() {
			@Override
			public void onTriggerFired(IFiredTriggerInfo
					triggerInformation) {
				double currentDepth = getTCPpositionZoffset(atPart, bot, eeTool.getCurrentTCP());
				depthPosArray.add(currentDepth);
			}
		};
		
		///////////Path positions where trigger will be executed
		double pathPercentage1 = 25;	//TCP probes depth 3 times per path at this percentage values from destination (negative values)
		double pathPercentage2 = 50;
		double pathPercentage3 = 75;
		
		MotionPathCondition motionPath1 = new MotionPathCondition(ReferenceType.DEST, (-1)*travelDistance*pathPercentage3/100, 0);
		MotionPathCondition motionPath2 = new MotionPathCondition(ReferenceType.DEST, (-1)*travelDistance*pathPercentage2/100, 0);
		MotionPathCondition motionPath3 = new MotionPathCondition(ReferenceType.DEST, (-1)*travelDistance*pathPercentage1/100, 0);
		
		////////////////////////////////////////////////////////////////////////
		//Different attempt for force creation
		CartesianSineImpedanceControlMode desiredForce = new CartesianSineImpedanceControlMode();
		desiredForce.parametrize(CartDOF.TRANSL).setStiffness(5000);
		desiredForce.parametrize(CartDOF.ROT).setStiffness(300);
		desiredForce = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.Z, 20, 4500);
		
		//Set mode behavior
		CartesianImpedanceControlMode modeLine = new CartesianImpedanceControlMode();
		modeLine.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);			//default
		modeLine.parametrize(CartDOF.ROT).setStiffness(300);							//default
		modeLine.parametrize(CartDOF.Z).setStiffness(getzStiffness());
		modeLine.parametrize(CartDOF.Z).setAdditionalControlForce((-1) * getAdditionalZForce());
		modeLine.setMaxControlForce(9000, 9000, 5000, 500, 500, 500, true);
		
		System.out.println("Simple mode running");
		boolean bConditionResult = false;
		
		//First motion up, down and up 
		currentTCP.move(linRel((-1)*getTravelDistance()/2, 0, (-1)*getzProgress(),currentTCP).setCartVelocity(getTravelVelocity()));
		currentTCP.move(linRel(getTravelDistance(),0 , 0,currentTCP).setCartVelocity(getTravelVelocity()));
		currentTCP.move(linRel((-1)*getTravelDistance(),0 , 0,currentTCP).setCartVelocity(getTravelVelocity()));
		
		grindingProcessTimer.setTimerValue(0);
		//forceTimer.setTimerValue(0);
		grindingProcessTimer.timerStart();
		//forceTimer.timerStart();
		
		Frame recenterPos = atPart.copy();
		
		//Main motion loop
		ForceComponentCondition TCPforce = new ForceComponentCondition(currentTCP,CoordinateAxis.Z, -20, 20);
		while (!bConditionResult) {
			
			//z progress calc
			zDown	=	(-1)*getzProgress()*cutDown;
			zUp		=  	(-1)*getzProgress()*cutUp;
			forceDown	=	(-1) * getAdditionalZForce() * downOn;
			forceUp		=	(-1) * getAdditionalZForce() * upOn;
			
			//move down / right
			currentTCP.move(linRel(getTravelDistance(),0 , zDown,currentTCP).
					setCartVelocity(getTravelVelocity()).
					setMode(modeLine).
					triggerWhen(motionPath1, checkDepth).
					triggerWhen(motionPath2, checkDepth).
					triggerWhen(motionPath3, checkDepth));
			
			//move up / left
			currentTCP.move(linRel((-1)*getTravelDistance(), 0, zUp,currentTCP).
					setCartVelocity(getTravelVelocity()).
					setMode(modeLine).
					triggerWhen(motionPath1, checkDepth).
					triggerWhen(motionPath2, checkDepth).
					triggerWhen(motionPath3, checkDepth));
			
			///////////////////////////////////////////////////////////////////////
			//if cutter diameter is bigger than 10mm we attempt to equalize the cut
			//only true for SD5SC and SD6
			if (eeTool.getCutterDiameter() > 10) {

				//equalizing attempt
				currentTCP.moveAsync(linRel((getTravelDistance()*pathPercetage/100), 0, zUp, currentTCP).
						setCartVelocity(getTravelVelocity()).
						setMode(modeLine).
						setBlendingCart(3));
				currentTCP.move(linRel(((-1)*getTravelDistance()*pathPercetage/100), 0, zUp, currentTCP).
						setCartVelocity(getTravelVelocity()).
						setMode(modeLine).
						setBlendingCart(3));
			}
			
			loopCounter++;
			zOffset = zOffset + 2*getzProgress();
			//if(forceTimer.getTimerValue()/1000 > forceTimePeriod) {
			//forceTimer.setTimerValue(0);
			
			// go over collected values and check if we reach expected depth
			for (Double depthValue : depthPosArray) {
				depthPerPath.append(String.format("%.2f", depthValue) + ", ");
				if (depthValue >= expectedDepth) {
					cutDown = 0;
					cutUp = 1;
				}
				if (	(depthPosArray.get(1) >= expectedDepth) &&
						(depthPosArray.get(2) >= expectedDepth) &&
						(depthPosArray.get(3) >= expectedDepth) &&
						(depthPosArray.get(4) >= expectedDepth)) 
					bConditionResult = true;
			}
			System.out.println(depthPerPath);
			if (!bConditionResult) depthPerPath.setLength(0);
			
			System.out.println("Progress down: " + zDown + "mm" + ", Progress up: " + zUp + "mm");
			System.out.println("Force down: " + forceDown + "N" + ", Force up: " + forceUp + "N");
			
			double zCurrentDepth = Collections.max(depthPosArray);	// Collection finds max , min, sorts, etc.
			depthProgress.append(String.format("%.2f", zCurrentDepth) + "mm, ");
			timeProgress.append((String.format("%.2f", grindingProcessTimer.getTimerValue()/1000)) + "s, ");
			//setAdditionalZForce(getAdditionalZForce() + addForceOverTime);
			//modeLine.parametrize(CartDOF.Z).setAdditionalControlForce((-1) * getAdditionalZForce());
			System.out.println(	"Theoretical Z position offset = " + String.format("%.2f", zOffset)
					+			"  Expected: "	+	expectedDepth
					+			"\nCurrent deepest: " + String.format("%.2f", zCurrentDepth) + "mm"
					+			"\nCurrent time: " + String.format("%.2f", grindingProcessTimer.getTimerValue()/1000) + "s"
					);
			depthPosArray.clear();
			if((grindingProcessTimer.getTimerValue()/1000 > getTotalTime())) {
				bConditionResult = true;
				this.setTimeOut(true);
				System.out.println("Simple mode finished");
			}
		}
		
		recData.stopRecording();
		
		logFile.println(timeProgress);
		logFile.println(depthProgress);
		
		//Calculate robot offset from start position at Part
		Frame offsetedPos = atPart.copy();
		Frame grindEndPosOffset = bot.getCurrentCartesianPosition(currentTCP, offsetedPos);
		System.out.println("Offset values from start position: " + grindEndPosOffset);
		logFile.println("Offset values from start position: " + grindEndPosOffset);
	
		System.out.println("Robot measured cut depth: " + String.format("%.2f", Math.abs(grindEndPosOffset.getZ())) + "mm");
		logFile.println("Robot measured cut depth: " + String.format("%.2f", Math.abs(grindEndPosOffset.getZ())) + "mm");
		logFile.println("Last pass depth values: " + depthPerPath);
		//condition which way we offset depending on cut direction (X or Y)
		//Transformation tcpShift = Transformation.ofTranslation(grindEndPosOffset.getX(), 0.0, 0.0);
		Transformation tcpShift = Transformation.ofTranslation(0.0, grindEndPosOffset.getY(), 0.0);
		offsetedPos.transform(tcpShift);	
		return offsetedPos;
	}
	
	private double getTCPpositionZoffset(Frame startPos, LBR bot, ObjectFrame currentTCP) {
		//calculate current position(currentTCP) offset from refrence (startPos)
		Frame currentOffset = bot.getCurrentCartesianPosition(currentTCP, startPos);
		return Math.abs(currentOffset.getZ());
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

	
	
	

