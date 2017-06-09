package application;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.swing.text.StyledEditorKit.BoldAction;

import sun.awt.SunHints.Value;

import modules.Common.EOscillationModes;
import modules.Common.ESearchDirection;
import modules.Common.EToolName;
import modules.RedundancyInfo;
import modules.GrindingTool;
import modules.SpiralMotion;
import modules.StaticGlobals;
import modules.StreamDataCommLib;
import modules.TimerKCT;
import modules.TouchForceRecord;
import modules.XmlParserGlobalVarsRD;
import modules.SimpleMode;

import com.kuka.common.ThreadInterruptedException;
import com.kuka.common.ThreadUtil;
import com.kuka.common.params.IParameter;
import com.kuka.common.params.IParameterSet;
import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import static com.kuka.roboticsAPI.motionModel.HRCMotions.*;
import com.kuka.roboticsAPI.conditionModel.BooleanIOCondition;
import com.kuka.roboticsAPI.conditionModel.ConditionObserver;
import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.conditionModel.ForceCondition;
import com.kuka.roboticsAPI.conditionModel.ICondition;
import com.kuka.roboticsAPI.conditionModel.IRisingEdgeListener;
import com.kuka.roboticsAPI.conditionModel.JointTorqueCondition;
import com.kuka.roboticsAPI.conditionModel.NotCondition;
import com.kuka.roboticsAPI.conditionModel.NotificationType;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.JointEnum;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.OperationMode;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.geometricModel.math.Vector;
import com.kuka.roboticsAPI.geometricModel.redundancy.IRedundancyCollection;
import com.kuka.roboticsAPI.ioModel.AbstractIO;
import com.kuka.roboticsAPI.motionModel.HandGuidingMotion;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.PositionHold;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
import com.kuka.roboticsAPI.persistenceModel.IPersistenceEngine;
import com.kuka.roboticsAPI.persistenceModel.XmlApplicationDataSource;
import com.kuka.roboticsAPI.persistenceModel.templateModel.GeometricObjectTemplate;
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
public class GrindingVer03 extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Controller kuka_Sunrise_Cabinet_1;
	private Tool PCC_EE;
	// Bases
	private ObjectFrame nullBase;
	// TCPs
	private ObjectFrame currentTCP;
	// Postitions
	private ObjectFrame posStartProcess, posTestPos;
	private ObjectFrame posAppRightCoupon;

	// custom
	private PrintWriter logFile, test1dumpFile;
	private GrindingTool eeTool;
	private TouchForceRecord searchPart;
	private Thread grindingTimerThread, forceTimerThread;
	private TimerKCT grindingProcessTimer, forceTimer;
	private boolean airTest;
	private EOscillationModes currentMode;
	private String currentDateTime;
	private String globalsFilePath;
	private String globalsFileNamePLC, globalsFileNameKRC;
	private XmlParserGlobalVarsRD globalVarFromPLC, globalVarFromKRC;
	private int processCounter;
	private int pressCounter;
	private Frame offsetedPos;
	EK1100IOGroup beckhoffIO;
	private AbstractIO pbFlangeTeach; //This is like SIGNAL in KRL but read only
	private AbstractIO pbUserKeysHGteachPos;
	private boolean maxForceExceeded;
	private boolean loopExitCondition;
	private SimpleMode shape;
	ArrayList<Frame> recPositions;
	private IMotionContainer positionHoldContainer;
	private StreamDataCommLib iiwaDataStream;
	
	
	@Override
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		PCC_EE = getApplicationData().createFromTemplate("PccGrinderVer01");
		eeTool = new GrindingTool(kuka_Sunrise_Cabinet_1);
		nullBase = getApplicationData().getFrame("/nullBase");
		posStartProcess = getApplicationData().getFrame(
				"/nullBase/StartProcess");
		posTestPos = getApplicationData().getFrame("/testBase/testPos");
		posAppRightCoupon = getApplicationData().getFrame(
				"/nullBase/appRightCoupon");
		searchPart = new TouchForceRecord();
		grindingProcessTimer = new TimerKCT();
		grindingTimerThread = new Thread(grindingProcessTimer);
		grindingTimerThread.setDaemon(true);
		grindingTimerThread.start();			//timer thread starts - does not start timer
		forceTimer = new TimerKCT();
		forceTimerThread = new Thread(forceTimer);
		beckhoffIO = new EK1100IOGroup(kuka_Sunrise_Cabinet_1);
		pbFlangeTeach = beckhoffIO.getInput("EK1100_DI03");	//declare input from inputs io group
		pbUserKeysHGteachPos = beckhoffIO.getOutput("EK1100_DO03"); //push button on grinding guard
		pressCounter = 0;
		loopExitCondition = false;
		
		// TimerThread = new Thread(forceTimer);
		logFile = null;

		globalsFilePath = "d:/Transfer/UserXMLs/";
		globalsFileNamePLC = "GlobalVarsPCCPLC.xml";
		globalsFileNameKRC = "GlobalVarsPCCKRC.xml";
		globalVarFromPLC = new XmlParserGlobalVarsRD(globalsFilePath,
				globalsFileNamePLC);
		globalVarFromKRC = new XmlParserGlobalVarsRD(globalsFilePath,
				globalsFileNameKRC);

		this.setMaxForceExceeded(false);
		airTest = false;
		recPositions = new ArrayList<Frame>();
		iiwaDataStream = new StreamDataCommLib("172.31.1.230", 30008);
		
	}

	@Override
	public void run() {

		// check for robot referencing and run referncing program if needed
		// if (!referencingStateCheck(bot)) {
		// System.out.println("Running position and GSM reference program");
		// //how do I execute a different java program???
		// PositionAndGMSReferencing posAndGSMapp = new
		// PositionAndGMSReferencing();
		// posAndGSMapp.initialize();
		// posAndGSMapp.run();
		// }
		// File logging setup //////////////////////////////
		SimpleDateFormat prefixDateFormat;
		prefixDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String filePathRoot = "d:/Transfer/PCCGrindingLogs/";
		currentDateTime = prefixDateFormat.format(Calendar.getInstance()
				.getTime());
		processCounter = globalVarFromKRC.getVarInteger("processCounter");
		processCounter++;
		globalVarFromKRC.setVar("processCounter",
				String.valueOf(processCounter));

		try {
			logFile = new PrintWriter(filePathRoot + currentDateTime + "_"
					+ "PCCgrinding_Process_" + processCounter + ".csv", "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			test1dumpFile = new PrintWriter(filePathRoot + currentDateTime + "_" + "collectionDumpFile.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////////////////////////////////////
		
		// set home position, attach tool , move home
		setNewHomePosition();
		PCC_EE.attachTo(bot.getFlange());

		//ESM 2 activate
		//A1 monitoring -15 , 15
		System.out.println("ESM 2 active");
		bot.setESMState("2");

		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.2));

		eeTool.setTool(PCC_EE);
		
		// ///////////////DEMO purposes only HOW TO USE Geometric Operator from
		// KRC4//////////////////
		// First example is explicit recalculation of values
		//Frame tool1TCP = currentTCP.copy(); // copy frame from TCP
		//double toolOffsetZ = eeTool.getCutterRadius();
		// This is equivalent of: newTool = tool1TCP : {X 0, Y 0, Z -7.9, A 0, B
		// 0, C 0}
		// with no new tool assignment
		//tool1TCP.transform(Transformation.ofTranslation(0, 0, -toolOffsetZ));

		// Second example more elegant and advanced solution JUST AN EXAMPLE it
		// does not set anything in current project

		//Enable this for tool TCP calculation
		/*
		eeTool.setCurrentTCP(EToolName.BallSD5SC);
		Frame tool2TCP = eeTool.getCurrentTCP().copy();
		eeTool.setCurrentTCP(EToolName.BallWorkingSD5SC);
		currentTCP = eeTool.getCurrentTCP();
		System.out.println("Current cutter: " + eeTool.getCutterName());
		double toolOffsetZ = eeTool.getCutterRadius();
		Transformation tcpShift = Transformation.ofTranslation(0.0, 0.0, -toolOffsetZ);
		System.out.println("Ball Tool: " + tool2TCP);
		System.out.println("Offseting by radius: " + -toolOffsetZ + "mm");
		tool2TCP.transform(tcpShift);
		System.out.println("offsettedTool BallWorking:" + tool2TCP);
		 */

		// This below is not working as expected
		// ObjectFrame newToolFr = myTool.addChildFrame("offsetedTool",
		// myTool.getFrame("Ball"), tcpShift);
		// And a subsequent change
		// Transformation newTcpOffset = Transformation.ofTranslation(-5,0,0);
		// myTool.changeFramePosition(newToolFr ,newTcpOffset);
		// ///////////////////////////////////////////////////////////////////////////////////////////
		
		

		//Set tool per PLC selection screen
		eeTool.setCurrentTCP(EToolName.valueOf(globalVarFromPLC.getVarString("currentCutter")));
		currentTCP = eeTool.getCurrentTCP();
		PCC_EE.setDefaultMotionFrame(currentTCP);

		System.out.println("Current Cutter: " + currentTCP.getAdditionalParameter("Comment"));

		System.out.println("Moving to approach coupon position");
		//currentTCP.moveAsync(ptp(posAppRightCoupon).setJointVelocityRel(0.2)
		//		.setBlendingCart(10));
		currentTCP.move(ptp(posAppRightCoupon).setJointVelocityRel(0.2));
		
		Frame testFrame = bot.getCurrentCartesianPosition(currentTCP);
		System.out.println(testFrame.toString());
		
		
		IRedundancyCollection myCollection = testFrame.getRedundancyInformationForDevice(bot).copy();
		System.out.println(myCollection.getAllParameters());
		System.out.println(myCollection.toString());
		
		///////////////////////////////////////////////////////////////////
		/////             HRC                   ///////////////////////////
		///// handGuide function test /experimental use ///////////////////
		///// set TRUE to enable //////////////////////////////////////////
		handGuideMe(true); 
		///////////////////////////////////////////////////////////////////
		
		String noteString = "Air run";

		if (airTest) {
			logFile.println("Note: " + noteString);
		}

		Frame atPart;
		Frame startOffsetted = posStartProcess.copy();

		////////////////////////////////////////////////////////////////////////////////////////////////
		//Hand guiding enabled - NOT HRC !!! Impedance mode 
		/////////////////////////////

		BooleanIOCondition HandIO = new BooleanIOCondition(pbFlangeTeach, true);  //declare condition for that input
		boolean bHandGuide = globalVarFromPLC.getVarBoolean("bHandGuide");
		boolean bHandGuideManualGrinding = globalVarFromPLC.getVarBoolean("bHandGuideManualGrinding");
		
		if (bHandGuide && bHandGuideManualGrinding) {			//hand guiding for manual grinding with IIWA 
			handGuideRecord(posAppRightCoupon.copy(), true);
		}
		if (bHandGuide) {										//hand guiding with recording positions
			System.out.println("Hand guiding enabled moving to reference position");
			ArrayList<Frame> recordedPositions = handGuideRecord(posAppRightCoupon.copy(), false);
			if (recordedPositions.size() > 0 ) {
				for (Frame position: recordedPositions) {
					System.out.println(position.toString());
				}
				getObserverManager().waitFor(HandIO.invert());		//wait for NOT HandIO condition
				System.out.println("Press button to start");
				getObserverManager().waitFor(HandIO);				//wait for HandIO
				//below is all in one solution (no AbstarctIO declaration or BooleanIOCOndition
				//KRL version of WAITFOR($IN[3]) 
				//getObserverManager().waitFor(new BooleanIOCondition(beckhoffIO.getInpu0t("EK1100_DI03"), true));
				for (Frame position: recordedPositions) {
					currentTCP.move(ptp(posAppRightCoupon).setJointVelocityRel(0.2));
					
					
					startOffsetted = position;
					double touch_position_offset = 1;
					Transformation tcpShift = Transformation.ofTranslation(0.0, 0.0, touch_position_offset);
					startOffsetted.transform(tcpShift);

					System.out.println("Moving to defect position");
					currentTCP.move(ptp(startOffsetted).setJointVelocityRel(0.2));
					if (!airTest) {
						searchPart.recordPosition(ESearchDirection.NegZ, 5, 10, 1, 0,
								currentTCP, nullBase, bot);
						if (searchPart.getResult()) {
							atPart = searchPart.getPosition();
						} else {
							throw new ArithmeticException(
									"No part detected, adjust start position , restart program");
						}

					} else {
						atPart = startOffsetted;
					}

					currentTCP.move(lin(startOffsetted).setCartVelocity(10));
					if (!airTest) {
						eeTool.grindingStart();
					}
					logFile.println("Process number: " + processCounter);
					logFile.println("Cutter info:\n " + currentTCP.getName() + "\n" + currentTCP.getAdditionalParameter("Comment") + "\n");
					System.out.println("Process number: " + processCounter);
					grindingProcess(atPart);				//grinding subroutine
					grindingProcessTimer.timerStopAndKill();		//timer stop
					String processTimer = "Process timer: "
							+ (grindingProcessTimer.getTimerValue() / 1000) + "s";
					System.out.println(processTimer);
					logFile.println(processTimer);

					StaticGlobals.grindManualReqKey = false;
					ThreadUtil.milliSleep(500);
					eeTool.grindingStop();

					//Edge finish if no timeout 
					if (!shape.isTimeOut()) {
						edgeFinish(offsetedPos, shape.getTravelDistance(), eeTool.getCutterRadius());
					}

					//ESM 2 activate
					//A1 monitoring -15 , 15
					System.out.println("ESM 2 active");
					bot.setESMState("2");
					currentTCP.move(linRel(0, 0, 10).setCartVelocity(10).setBlendingCart(5));
				}
			} else {
				throw new ArithmeticException("this is not possible ??????");
			}
		}
		System.out.println("Moving to approach coupon position");
		currentTCP.moveAsync(ptp(posAppRightCoupon).setJointVelocityRel(0.2).setBlendingCart(10));

		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.2));

	}

	public void grindingProcess(Frame atPart) {

		switch (EOscillationModes.valueOf(globalVarFromPLC
				.getVarString("oscillationMode"))) {
		case spiralMode:
			spiralModeExec(atPart);
			break;
		case sineMode:
			sinusModeExec(atPart);
			break;
		case simpleMode:
			simpleModeExec(atPart);
			break;
		default:
			throw new ArithmeticException(
					"Not possible switch/case situation, <GrindingVer01>");
		}
	}

	public void simpleModeExec(Frame atPart) {
		
		CartDOF oscillationAxis;
		switch (CartDOF.valueOf(globalVarFromPLC.getVarString("simpleWorkingDirection"))) {
		case X:
			oscillationAxis = CartDOF.X;
			break;
		case Y:
			oscillationAxis = CartDOF.Y;
			break;
		default:
			oscillationAxis = null;
			break;
		}

		double additionalZForce = globalVarFromPLC.getVarDouble("simpleWorkingDirAdditionalForce");
		double zProgress = globalVarFromPLC.getVarDouble("simpleZProgress");
		double travelDistance = globalVarFromPLC.getVarDouble("simpleTravelDistance");
		double travelVelocity = globalVarFromPLC.getVarDouble("simpleTravelVelocity");
		long totalTime = globalVarFromPLC.getVarLong("simpleTotalTime");
		double expectedDepth = globalVarFromPLC.getVarDouble("simpleExpectedDepth");
		double zStiffness = globalVarFromPLC.getVarDouble("simpleWorkingDirStiffness");
		
		shape = new SimpleMode(	additionalZForce, zProgress, travelDistance,
								travelVelocity, totalTime, oscillationAxis, 
								expectedDepth, zStiffness);
		offsetedPos = shape.executeMode(eeTool, atPart, bot, grindingProcessTimer, logFile);
		
	}
	
	public void sinusModeExec(Frame atPart) {
		
		CartDOF oscillationAxis;
		switch (CartDOF.valueOf(globalVarFromPLC.getVarString("sineWorkingDirection"))) {
		case X:
			oscillationAxis = CartDOF.X;
			break;
		case Y:
			oscillationAxis = CartDOF.Y;
			break;

		default:
			oscillationAxis = null;
			break;
		}

		double frequency = globalVarFromPLC.getVarDouble("sineFrequency"); 
		double amplitude = globalVarFromPLC.getVarDouble("sineAmplitude"); 
		double planeStiffness = globalVarFromPLC.getVarDouble("sinePlaneStiffness");

		double workingDirStiffness = globalVarFromPLC.getVarDouble("sineWorkingDirStiffness");
		double workingDiradditionalForce = globalVarFromPLC.getVarDouble("sineWorkingDirAdditionalForce");
		double travelDistance = globalVarFromPLC.getVarDouble("sineTravelDistance");		
		double travelVelocity = globalVarFromPLC.getVarDouble("sineTravelVelocity");

		long totalTimeSecs = (long) (travelDistance/travelVelocity); 




		double additionalZForce = globalVarFromPLC.getVarDouble("simpleWorkingDirAdditionalForce");
		double zProgress = globalVarFromPLC.getVarDouble("simpleZProgress");
		long totalTime = globalVarFromPLC.getVarLong("simpleTotalTime");
		double expectedDepth = globalVarFromPLC.getVarDouble("simpleExpectedDepth");
		double zStiffness = globalVarFromPLC.getVarDouble("simpleWorkingDirStiffness");

		shape = new SimpleMode(	additionalZForce, zProgress, travelDistance,
				travelVelocity, totalTime, oscillationAxis, 
								expectedDepth, zStiffness);
		offsetedPos = shape.executeMode(eeTool, atPart, bot, grindingProcessTimer, logFile);
		
	}
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// HCR attempt /////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	public void handGuideMe(boolean isEnabled) {
		if(!isEnabled) return;		//if we got false we don't execute
		
		 
				
				//Then we need to give back actual tool used before and used correct one for position teaching
				//or maybe its just COM??? and tcp does not matter
		ObjectFrame currentWorkingTCP = eeTool.setCurrentTCP(EToolName.valueOf(currentTCP.getName()));
		System.out.println("DEBUG: " + currentWorkingTCP.toString() + " name = " + currentWorkingTCP.getName());
		
		System.out.println(eeTool.setCurrentTCP(EToolName.HCR)); 
		System.out.println("DEBUG: " + currentTCP.toString() + " name = " + currentTCP.getName());
		
		//Enable ability to guide the robot with HCR button
		//This is done in BackgroundTaskHCR
		StaticGlobals.hcrEnable = true;
		
		
		//Max torque at which we should quit
		double maxTorque = 4;
		
		HandGuidingMotion hgMotion;
		
		JointTorqueCondition axis7twist = new JointTorqueCondition(bot, JointEnum.J7, -maxTorque, maxTorque);
		
		
		//settings for axis limits in HCR mode
		double a1N = Math.toRadians(-160);
		double a2N = Math.toRadians(0);
		double a3N = Math.toRadians(-10);
		double a4N = Math.toRadians(-115);
		double a5N = Math.toRadians(-160);
		double a6N = Math.toRadians(-90);
		double a7N = Math.toRadians(-160);
		
		double a1P = Math.toRadians(160);
		double a2P = Math.toRadians(115);
		double a3P = Math.toRadians(10);
		double a4P = Math.toRadians(0);
		double a5P = Math.toRadians(160);
		double a6P = Math.toRadians(90);
		double a7P = Math.toRadians(160);
		
		boolean a1Act = false;
		boolean a2Act = true;
		boolean a3Act = true;
		boolean a4Act = false;
		boolean a5Act = false;
		boolean a6Act = false;
		boolean a7Act = false;
		
		//condition for user press button that enables DO 03 on beckhoff
		//push button for teaching position is setup in UserKeys as digital output 3 true/false (momentary push button)
		BooleanIOCondition hgTeachButton = new BooleanIOCondition(beckhoffIO.getOutput("EK1100_DO03"), true);
		
		//this works like interrupt type solution 
		//create listener and its code to execute on rising edge
		IRisingEdgeListener listener_hgTeachButton = new IRisingEdgeListener() {
			
			@Override
			public void onRisingEdge(ConditionObserver conditionObserver, Date time,
					int missedEvents) {
				pressCounter++;
				recPositions.add(bot.getCurrentCartesianPosition(currentTCP, nullBase));
				System.out.println("Hola! :" + time);
			}
		};
		//create observer that connects event (teach button) with rising edge listener and its code
		ConditionObserver hgTeachButtonObserver = getObserverManager().createConditionObserver(hgTeachButton, NotificationType.MissedEvents, listener_hgTeachButton);
		//enabled observer (interupt)
		hgTeachButtonObserver.enable();
		
		//create hand guiding object with  and set its axis limits based on previous data
		//additional parameters (axis limit violation on start)
		hgMotion = handGuiding().
				setAxisLimitsMin(a1N, a2N, a3N, a4N, a5N, a6N, a7N).
				setAxisLimitsMax(a1P, a2P, a3P, a4P, a5P, a6P, a7P).
				setAxisLimitsEnabled(a1Act, a2Act, a3Act, a4Act, a5Act, a6Act, a7Act).
				setAxisLimitViolationFreezesAll(false).
				setPermanentPullOnViolationAtStart(true).
				setAxisSpeedLimit(0.5);
		
		//loop that puts robot into HRC mode 
		//condition is smartPad push button from UserKeys (sets/resets EK1100 DO 4, hold button)
		while (!beckhoffIO.getEK1100_DO04()) {
			System.out.println("Goint into Free motion...");
			positionHoldContainer = currentTCP.move(hgMotion);
		}
		
		System.out.println("... and done");
		positionHoldContainer.cancel();		//cancel HCR
		hgTeachButtonObserver.disable();	//observer canceled (no points recording)	
		beckhoffIO.setEK1100_DO04(false); 	//reset smart pad button
		
		System.out.println("Hand Guide Canceled");
		StaticGlobals.hcrEnable = false;
		
		String newPosName = null; 
		int frameCounter = 0;
		
		//Attempt to remove all P1, P2, P3... from RoboticsAPI.data.xml
		// Get the object to manipulate the RoboticsAPI.data.xml file where all the application data is stored
		final IPersistenceEngine engine = this.getContext().getEngine(IPersistenceEngine.class);
		final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
		System.out.println(defaultDataSource.getName());
		System.out.println(defaultDataSource.getDataFile());
		
		System.out.println(defaultDataSource.loadAllFrames());
		
		Collection<? extends ObjectFrame> test1Collection = defaultDataSource.loadAllFrames();
		Iterator<? extends ObjectFrame> test1Iterator  = test1Collection.iterator();
		Map<String, Object> asMap = new HashMap<String, Object>();
		Object[] asArray = test1Collection.toArray();
		for (int i = 0; i < asArray.length-1; i+=2) {
			  //String key = (String) asArray[i];
			  //Object value = asArray[i+1];
			  //asMap.put(key, value);
				System.out.println(asArray[i]);
				System.out.println(asArray[i+1]);
			}
		System.out.println(asMap.toString());
		
		System.out.println("HOLA ! i pozamiatane misiek");
		
		System.out.println(test1Collection.size());
		System.out.println(test1Collection.toArray().length);
		System.out.println(test1Collection.toString());
		System.out.println(test1Collection.contains("testBase"));
		
		test1dumpFile.println(test1Collection.toString());
		test1dumpFile.flush();
		test1dumpFile.close();
		
		
		for(Frame aFrame : recPositions) {
			newPosName = "PCC_ManualPos_" + (frameCounter++);
			System.out.println(aFrame.toString());
			//System.out.println(aFrame.getRedundancyInformation().toString());
			
			Map<String, IRedundancyCollection> meMap = aFrame.getRedundancyInformation();
			System.out.println("Map size: " + meMap.size() + "\nValues: " + meMap.values());
			System.out.println("Map keys:" + meMap.keySet());
			
			try {
				
				ObjectFrame fNewFrame = defaultDataSource.addFrame(nullBase);
				
				defaultDataSource.removeFrame(getApplicationData().getFrame("/nullBase/P4"));
				defaultDataSource.removeFrame(getApplicationData().getFrame("/nullBase/P9"));
				defaultDataSource.renameFrame(getApplicationData().getFrame("/nullBase/P5"), "Diablo5");
				
				defaultDataSource.changeFrameTransformation(fNewFrame, aFrame.transformationTo(fNewFrame));
				defaultDataSource.saveFile(false);
				getLogger().info("Stored a new point!");
			}
			catch (Exception ex){
				getLogger().info("Exception: " + ex.toString());
			}	
		}
		
		getApplicationControl().halt();
		
	}
	
	public void setESMState(String esmNumber, LBR roBot) {
		System.out.println("ESM: " + esmNumber + " enabled");
		roBot.setESMState(esmNumber);
	}
	
	public void sineModeExec(Frame atPart) {
		
		CartDOF oscillationAxis;
		switch (CartDOF.valueOf(globalVarFromPLC.getVarString("sineWorkingDirection"))) {
		case X:
			oscillationAxis = CartDOF.X;
			break;
		case Y:
			oscillationAxis = CartDOF.Y;
			break;

		default:
			oscillationAxis = null;
			break;
		}
		
		double frequency = globalVarFromPLC.getVarDouble("sineFrequency"); 
		double amplitude = globalVarFromPLC.getVarDouble("sineAmplitude"); 
		double planeStiffness = globalVarFromPLC.getVarDouble("sinePlaneStiffness");
		
		double workingDirStiffness = globalVarFromPLC.getVarDouble("sineWorkingDirStiffness");
		double workingDiradditionalForce = globalVarFromPLC.getVarDouble("sineWorkingDirAdditionalForce");
		double travelDistance = globalVarFromPLC.getVarDouble("sineTravelDistance");		
		double travelVelocity = globalVarFromPLC.getVarDouble("sineTravelVelocity");

		long totalTimeSecs = (long) (travelDistance/travelVelocity); 
		
		CartesianSineImpedanceControlMode sineMode;
		//set Spiral oscillation parameters
		sineMode = CartesianSineImpedanceControlMode.createSinePattern(oscillationAxis, frequency, amplitude, planeStiffness);
		//set work direction force parameters
		sineMode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		sineMode.parametrize(CartDOF.ROT).setStiffness(300);
		sineMode.parametrize(CartDOF.Z).setBias(-workingDiradditionalForce).setStiffness(workingDirStiffness);
		sineMode.setStayActiveUntilPatternFinished(true);

		//Max force at which we should quit
		ForceComponentCondition TCPforce;
		double maxForce = 60;
		TCPforce = new ForceComponentCondition(currentTCP,CoordinateAxis.Z, -maxForce, maxForce);
		
		////////////////// logging ///////////////////
		logFile.println(currentDateTime);
		System.out.println("Process number: " + processCounter);
		logFile.println("Process number: " + processCounter);
		if(StaticGlobals.disableTool) {
			logFile.println("EndEffector disabled - DRY RUN ONLY");
		}
		logFile.println("Mode: Sine");
		logFile.println("Frequency: " 	+ frequency 
				+ " Amplitude: "  + amplitude 
				+ " PlaneStiffness: " + planeStiffness
				+ " Max deflection: " + (amplitude/planeStiffness*1000) + "mm");
		logFile.println("Oscillation time: " + totalTimeSecs + "seconds,  this is motion cut off time too");
		logFile.println("Working direction stiffness: " + workingDirStiffness 
				+ " Additional Force: " + workingDiradditionalForce);
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
		totalTimeSecs = 100;	//	s 	this overwrites PLC var
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
		forceTimerThread.start();
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
									+	" Current Pos Z: " + String.format("%.2f", bot.getCurrentCartesianPosition(currentTCP, nullBase).getX())
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
		offsetedPos = atPart.copy();
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

		//////////////////////////////////////////////////////////////////////////////////
		
		
		///////Dual sine mode - unknown position :( ///////////////
		/*boolean bTimerFlag = false;
		
		while (!bConditionResult) {
			if((grindingProcessTimer.getTimerValue())/1000 > (totalTimeSecs/2)  && !bTimerFlag) {
				bTimerFlag = true;
				System.out.println("Half Way Through: " + grindingProcessTimer.getTimerValue()/1000);
				positionHoldContainer.cancel();
				sineMode = CartesianSineImpedanceControlMode.createSinePattern(oscillationAxis, 0.25, amplitude/2, planeStiffness);
				sineMode.parametrize(CartDOF.Z).setBias(-workingDiradditionalForce).setStiffness(workingDirStiffness);
				positionHoldContainer = currentTCP.moveAsync(linRel(0, 0, -travelDistance,currentTCP).setCartVelocity(travelVelocity).setMode(sineMode));	
			}
			if((grindingProcessTimer.getTimerValue()/1000 > totalTimeSecs)) {
				bConditionResult = true;
				System.out.println("Sine osciallation finished");
			}
		}*/
		//////////////////////////////////////////////////////////////
		
		//////Condition monitoring (time/force)
		/*IMotionContainer positionHoldContainer;
		positionHoldContainer = currentTCP.moveAsync(linRel(0, 0, -travelDistance,currentTCP).setCartVelocity(travelVelocity).setMode(sineMode));
		boolean bConditionResult = false;
		bConditionResult = getObserverManager().waitFor(TCPforce, totalTimeSecs,TimeUnit.SECONDS);
		if (bConditionResult) { 
			System.out.println("Max Force exceeded");
		} else {
			System.out.println("Sine osciallation finished");
		}
		positionHoldContainer.cancel();*/
		///////////////////////////////////////////////////////////////
		
	}

	public void edgeFinish(Frame atPart, double cutLength, double cutterRadius) {
		double cutLengthPercentage;
		if (cutLength <= eeTool.getCutterRadius()) {
			cutLengthPercentage = 20; // Percentage
		} else {
			cutLengthPercentage = 80; // Percentage
		}
		double cutterRadiusPercentage = 80; // Percentage
		double cutterRadiusBlending;
		StringBuilder cutPercentage = new StringBuilder();
		cutPercentage.append("Cut Length: " + cutLength);
		cutPercentage.append(" Cut Length%: " + cutLengthPercentage);
		cutLength = cutLength * cutLengthPercentage / 100;
		cutPercentage.append(" = " + cutLength);
		cutPercentage.append(" ** Cutter Radius: " + cutterRadius);
		cutPercentage.append(" Cutter%: " + cutterRadiusPercentage);
		cutterRadius = cutterRadius * cutterRadiusPercentage / 100;
		if (cutterRadius <= 5) {
			cutterRadiusBlending = 5;
			cutPercentage.append("blending radius <= 5mm, default 5 set(minimum), radius ");
		} else {
			cutterRadiusBlending = cutterRadius;
		}
		cutPercentage.append(" = " + cutterRadius);
		System.out.println(cutPercentage);

		double finishVelocity = 5;
		double finishZforce = 5; // N
		double zStiffness = 4000;
		double zOffset = 0;
		double zDir = -1; // tool dir positive Z = 1, negative Z = -1

		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		mode.parametrize(CartDOF.Z).setAdditionalControlForce(
				zDir * finishZforce);
		mode.parametrize(CartDOF.Z).setStiffness(zStiffness);

		// Move to leftLower corner, travel up (leftUpper), exec upperArc,
		// travel down(rightLower), exec lowerArc

		// move to center
		currentTCP.move(lin(atPart).setCartVelocity(finishVelocity));
		
		//getApplicationControl().halt();
		eeTool.grindingStart();

		
		// leftLower
		currentTCP.move(linRel(cutLength / 2, -cutterRadius, (zDir * zOffset))
				.setCartVelocity(finishVelocity));
		currentTCP.move(linRel(0, 0, 0).setCartVelocity(finishVelocity)
				.setMode(mode));
		// travel up (leftUpper)
		currentTCP.moveAsync(linRel(-cutLength, 0, (zDir * zOffset))
				.setCartVelocity(finishVelocity).setBlendingCart(cutterRadiusBlending)
				.setMode(mode));
		// upperARC ???
		currentTCP.moveAsync(linRel(-cutterRadius, cutterRadius,
				(zDir * zOffset)).setCartVelocity(finishVelocity)
				.setBlendingCart(cutterRadiusBlending).setMode(mode));
		currentTCP.moveAsync(linRel(cutterRadius, cutterRadius,
				(zDir * zOffset)).setCartVelocity(finishVelocity)
				.setBlendingCart(cutterRadiusBlending).setMode(mode));
		// travel down(rightLower)
		currentTCP.moveAsync(linRel(cutLength, 0, (zDir * zOffset))
				.setCartVelocity(finishVelocity).setBlendingCart(cutterRadiusBlending)
				.setMode(mode));
		// lowerARC ???
		currentTCP.moveAsync(linRel(cutterRadius, -cutterRadius,
				(zDir * zOffset)).setCartVelocity(finishVelocity)
				.setBlendingCart(cutterRadiusBlending).setMode(mode));
		currentTCP.moveAsync(linRel(-cutterRadius, -cutterRadius,
				(zDir * zOffset)).setCartVelocity(finishVelocity)
				.setBlendingCart(cutterRadiusBlending).setMode(mode));
		// overlap a bit
		currentTCP.move(linRel(-cutLength / 4, 0, (zDir * zOffset))
				.setCartVelocity(finishVelocity).setMode(mode));

		// off the surface
		mode.parametrize(CartDOF.Z).setAdditionalControlForce(
				(-1) * zDir * finishZforce);
		currentTCP.move(linRel(0, 0, 0).setCartVelocity(finishVelocity)
				.setMode(mode));

		eeTool.grindingStop();

		// move to center
		currentTCP.move(lin(atPart).setCartVelocity(finishVelocity));

	}

	public void spiralModeExec(Frame atPart) {

		CartPlane cartPlane = CartPlane.XY;
		double frequency = globalVarFromPLC.getVarDouble("spiralFrequency");
		double amplitude = globalVarFromPLC.getVarDouble("spiralAmplitude");
		double planeStiffness = globalVarFromPLC
				.getVarDouble("spiralPlaneStiffness");
		long totalTimeSecs = globalVarFromPLC.getVarLong("spiralTotalTimeSecs");
		long holdTime = globalVarFromPLC.getVarLong("spiralHoldTime");
		long riseTime = globalVarFromPLC.getVarLong("spiralRiseTime");
		long fallTime = globalVarFromPLC.getVarLong("spiralFallTime");

		double workingDirStiffness = globalVarFromPLC
				.getVarDouble("spiralWorkingDirStiffness");
		double workingDiradditionalForce = globalVarFromPLC
				.getVarDouble("spiralWorkingDirAdditionalForce");
		double travelDistance = globalVarFromPLC
				.getVarDouble("spiralTravelDistance");
		double travelVelocity = globalVarFromPLC
				.getVarDouble("spiralTravelVelocity");

		totalTimeSecs = totalTimeSecs + holdTime;
		CartesianSineImpedanceControlMode spiralMode;
		// set Spiral oscillation parameters
		spiralMode = CartesianSineImpedanceControlMode.createSpiralPattern(
				cartPlane, frequency, amplitude, planeStiffness, totalTimeSecs);
		// set work direction force parameters
		spiralMode.parametrize(CartDOF.Z).setBias(-workingDiradditionalForce)
				.setStiffness(workingDirStiffness);
		spiralMode.setRiseTime(riseTime);
		spiralMode.setFallTime(fallTime);
		spiralMode.setHoldTime(holdTime);
		spiralMode.setStayActiveUntilPatternFinished(true);

		// Max force at which we should quit
		ForceComponentCondition TCPforce;
		double maxForce = 70;
		TCPforce = new ForceComponentCondition(currentTCP, CoordinateAxis.Z,
				-maxForce, maxForce);

		// //////////////// logging ///////////////////
		logFile.println(currentDateTime);
		System.out.println("Process number: " + processCounter);
		logFile.println("Process number: " + processCounter);
		if (StaticGlobals.disableTool) {
			logFile.println("EndEffector disabled - DRY RUN ONLY");
		}
		logFile.println("Mode: Spiral");
		logFile.println("Frequency: " + frequency + " Amplitude: " + amplitude
				+ " PlaneStiffness: " + planeStiffness + " Max deflection: "
				+ (amplitude / planeStiffness * 1000) + "mm");
		logFile.println("Oscillation time (rise/hold/fall): " + totalTimeSecs
				+ "seconds,  this is motion cut off time too");
		logFile.println("\tRise time: " + spiralMode.getRiseTime());
		logFile.println("\tHold time: " + spiralMode.getHoldTime());
		logFile.println("\tFall time: " + spiralMode.getFallTime());
		logFile.println("Working direction stiffness: " + workingDirStiffness
				+ " Additional Force: " + workingDiradditionalForce);
		logFile.println("TCP Travel distance : " + travelDistance
				+ "mm, TCP Velocity: " + travelVelocity + " mm/s");
		logFile.println("Theoretical TCP travel time: " + travelDistance
				/ travelVelocity + " seconds");
		logFile.println("Max force for stopping grinding: " + maxForce);

		// /////////////////////////////////////////////

		// Move to part running
		// ///////////////////////////////////////////////////////////////////////////////////////
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(1);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		currentTCP.move(lin(atPart).setCartVelocity(5).setMode(mode));

		grindingProcessTimer.setTimerValue(0);	//timer set to 0
		grindingProcessTimer.timerStart();		//timer start

		// ////////////////////Grinding happens here////////////////////
		IMotionContainer positionHoldContainer;
		// positionHoldContainer = currentTCP.moveAsync(positionHold(spiralMode,
		// -1, TimeUnit.SECONDS));
		positionHoldContainer = currentTCP.moveAsync(linRel(0, 0,
				-travelDistance, currentTCP).setCartVelocity(travelVelocity)
				.setMode(spiralMode));
		System.out.println("Spiral running");
		boolean bConditionResult = false;

		bConditionResult = getObserverManager().waitFor(TCPforce,
				totalTimeSecs, TimeUnit.SECONDS);
		if (bConditionResult) {
			System.err.println("Max Force exceeded");
			this.setMaxForceExceeded(true);
		} else {
			System.out.println("Spiral finished");
		}
		positionHoldContainer.cancel();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//							HAND GUIDING FUNCTION
	///////////////////////////////////////////////////////////////////////////////////////
								
	public ArrayList<Frame> handGuideRecord(Frame zoneSafePos, boolean manualGrinding) {
		
		CartesianImpedanceControlMode handModeStiff = new CartesianImpedanceControlMode();
		CartesianImpedanceControlMode handModeLoose = new CartesianImpedanceControlMode();
		IMotionContainer positionHoldContainer = null;
		Frame handPos = null;
		double maxForce = 5;
		ArrayList<Frame> recPositions = new ArrayList<Frame>();
		boolean recPositionDone = false;
		
		//declare condition to grab handle and move robot from handModeStiff mode
		ForceComponentCondition standStillConditionX, standStillConditionY, standStillConditionZ;
		standStillConditionX = new ForceComponentCondition(currentTCP, CoordinateAxis.X,
				-maxForce, maxForce);
		standStillConditionY = new ForceComponentCondition(currentTCP, CoordinateAxis.Y,
				-maxForce, maxForce);
		standStillConditionZ = new ForceComponentCondition(currentTCP, CoordinateAxis.Z,
				-maxForce, maxForce);
		ICondition standStillCondition;
		standStillCondition = standStillConditionX.or(standStillConditionY).or(standStillConditionZ);
		
		//standStill mode
		handModeStiff.parametrize(CartDOF.TRANSL).setStiffness(3000).setDamping(1);
		handModeStiff.parametrize(CartDOF.A).setStiffness(300);
		handModeStiff.parametrize(CartDOF.B).setStiffness(300);
		handModeStiff.parametrize(CartDOF.C).setStiffness(300);
		
		//free hand guiding mode
		handModeLoose.parametrize(CartDOF.TRANSL).setStiffness(50).setDamping(0.1);
		handModeLoose.parametrize(CartDOF.A).setStiffness(5);
		handModeLoose.parametrize(CartDOF.B).setStiffness(40);
		handModeLoose.parametrize(CartDOF.C).setStiffness(40);
		
		//loop recoring positions
		while (!recPositionDone) {
			
			//set at standstill and wait for moving force
			positionHoldContainer = currentTCP.moveAsync(new PositionHold(handModeStiff, -10, TimeUnit.SECONDS));
			System.out.println("Grab the handle and start");
			getObserverManager().waitFor(standStillCondition);
			
			//set at hand guiding mode
			positionHoldContainer.cancel();
			System.out.println("Switching to hand motion...");
			
			positionHoldContainer = currentTCP.moveAsync(new PositionHold(handModeLoose, -10, TimeUnit.SECONDS));

			//PB press / release will record first position
			getObserverManager().waitFor(new BooleanIOCondition(pbFlangeTeach, true));
			getObserverManager().waitFor(new BooleanIOCondition(pbFlangeTeach, false));

			//record current position and cancel motions, move to recorded position 
			handPos = bot.getCurrentCartesianPosition(currentTCP, nullBase).copyWithRedundancy();
			positionHoldContainer.cancel();
			currentTCP.move(ptp(handPos));
			
			if (!manualGrinding) {				//no manual grinding with robot
				recPositions.add(handPos);		//add position to array
				System.out.println("Position " + recPositions.size() + " recorded");
				iiwaDataStream.login();
				iiwaDataStream.sendPosition(handPos, recPositions.size());
				
				//wait - delayTime value - for press on pushbutton to finish recording
				//motions are blocked for that time
				long delayTime = 2;
				boolean bConditionResult = getObserverManager().waitFor(new BooleanIOCondition(pbFlangeTeach, true), delayTime, TimeUnit.SECONDS);
				getObserverManager().waitFor(new BooleanIOCondition(pbFlangeTeach, false));

				if (bConditionResult) {		//got push button - done recording
					System.err.println("Done recording positions. Total recorded: " + recPositions.size());
					recPositionDone = true;
					iiwaDataStream.login();
					iiwaDataStream.write("EOT");
				}

			} else {
				System.out.println("Manual Grinding Done");
				StaticGlobals.grindManualReqKey = false;
				beckhoffIO.setEK1100_DO01_GrindingToolReq(false);
				getApplicationControl().halt();
				return null;
			}
		}
		return recPositions;
	}

	public void depthMeasure(Frame atPart) {
		// this is not really working as robot display TCP position using
		// robroot as reference
		// and it only measures X positive distance traveled
		double startZ = atPart.copy().getX();
		searchPart.recordPosition(ESearchDirection.NegZ, 20, 10, 2, 0,
				currentTCP, nullBase, bot);
		double stopZ = searchPart.getPosition().getX();
		// System.out.println("start Z: " + startZ + "  stop Z: " + stopZ);
		String grindingDepth = "Grinding depth = " + (stopZ - startZ) + "mm";
		System.out.println(grindingDepth);
		logFile.println(grindingDepth);
	}

	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(
				Math.toRadians(27),		//A1
				Math.toRadians(-5), 	//A2
				Math.toRadians(0), 		//A3
				Math.toRadians(-117),	//A4
				Math.toRadians(66), 	//A5
				Math.toRadians(-30), 	//A6
				Math.toRadians(-60));	//A7
		bot.setHomePosition(newHome);
	}

	private boolean referencingStateCheck(LBR bot) {
		String torqueReferencing = null;
		String positionReferencing = null;
		boolean bTorqueReferencing = true;
		boolean bPositionReferencing = true;

		if (!bot.getSafetyState().areAllAxesGMSReferenced()) {
			// check if all joints are torque referenced
			torqueReferencing = "Not all joint are torque referenced";
			bTorqueReferencing = false;
			System.err.println(torqueReferencing);
		}
		if (!bot.getSafetyState().areAllAxesPositionReferenced()) {
			// check if all joints are position referenced
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
	public void dispose() {
		try {
			eeTool.grindingStop();
			grindingProcessTimer.timerStopAndKill();
			System.out.println("Process timer: "
					+ (grindingProcessTimer.getTimerValue() / 1000) + "s");
			setESMState("2", bot);
		} catch (ThreadInterruptedException e2) {
			System.err.println("Grinding Timer Failure?" + e2);
		} catch (NullPointerException e1) {
			System.err.println("Grinding Timer Failure" + e1);
		}
		
//		try {
//			forceTimer.timerStopAndKill();
//		} catch (ThreadInterruptedException e) {
//			System.err.println("Force Timer Failure");
//			e.printStackTrace();
//		}
		try {
			System.out.println("Closing files");
			logFile.flush();
			logFile.close();
			System.out.println("Done");
		} catch (NullPointerException e) {
			System.err.println("Sacrebleu");
			e.printStackTrace();
		} finally {
			super.dispose();
		}
	}

	public boolean isMaxForceExceeded() {
		return maxForceExceeded;
	}

	public void setMaxForceExceeded(boolean maxForceExceeded) {
		this.maxForceExceeded = maxForceExceeded;
	}
	
	public void collectionStruggles() {
		final IPersistenceEngine engine = this.getContext().getEngine(IPersistenceEngine.class);
		final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
		System.out.println(defaultDataSource.getName());
		System.out.println(defaultDataSource.getDataFile());
		
		//Load all frames from default source as collection
		Collection<? extends ObjectFrame> test1Collection = defaultDataSource.loadAllFrames();
		//get iterator for given collection
		Iterator<? extends ObjectFrame> test1Iterator  = test1Collection.iterator();
		//loop over frames using iterator
		while (test1Iterator.hasNext()) {
			//load iterator value as ObjectFrame
			ObjectFrame aFrame = test1Iterator.next();
			//display frame name and XYZABC
			System.out.println(aFrame.getName());
			System.out.println(aFrame.copy());
			//create collection and iterator for redundancy info from given frame
			Collection<IRedundancyCollection> aFrameRedundancyInfo =  aFrame.getRedundancyInformation().values();
			Iterator<IRedundancyCollection> aFrameRedundancyIterator = aFrameRedundancyInfo.iterator();
			//if frame has redundancy info proceed with extraction
			if (aFrameRedundancyInfo.size() > 0) {
				System.out.println("Collection size: " + aFrameRedundancyInfo.size());
				//get all parameters from collection, should be always 3
				IParameterSet newSet = aFrameRedundancyIterator.next().getAllParameters();
				System.out.println("Parameter set size: " + newSet.paramCount());
				//get iterator for parameter set
				Iterator<IParameter<?>> paramSetIterator = newSet.iterator();
				int loopCounter = 0;
				//new map to store all redundancy info easy way -> Python Dictionary
				Map<String, Object> redundancyMap2 = new HashMap<String, Object>();
				redundancyMap2.clear();
				System.out.println("Map before: " + redundancyMap2.toString());
				while (paramSetIterator.hasNext()) {
					loopCounter++;
					IParameter<?> yaParam = paramSetIterator.next();
					switch (loopCounter) {
					case 1:
						redundancyMap2.put("StatusParameter", yaParam.value());
						break;
					case 2:
						redundancyMap2.put("TurnParameter", yaParam.value());
						break;
					case 3:
						redundancyMap2.put("E1Parameter", yaParam.value());
						break;
					default:
						//TODO throw custom exception here
						break;
					}
				}
				System.out.println("Map after: " + redundancyMap2.toString());
			}	
		}
		
		System.out.println("HOLA !");
//				Yet another way to iterate over collection	
//				 for(ObjectFrame item : test1Collection) {
//				 System.out.println(item.getName());  //nullFrame
//				 System.out.println(item.copy());	  //[X=0.00 Y=0.00 Z=0.00 A=0.00 B=0.00 C=0.00]
//			  }
	}
	public class MeRedundancy implements IRedundancyCollection {

		@Override
		public IRedundancyCollection copy() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IParameterSet getAllParameters() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}