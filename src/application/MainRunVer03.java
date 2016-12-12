package application;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.Common;
import modules.Common.ECalcDirection;
import modules.Common.ECouponSectionName;
import modules.Common.EHotDotCouponStates;
import modules.Common.searchDir;
import modules.CouponCalc;
import modules.CouponProperties;
import modules.CouponXMLparser;
import modules.DispenserIO;
import modules.DispenserIOver02;
import modules.GlobalVars;
import modules.TimerKCT;
import modules.TouchForceRecord;
import modules.XMLParserCoupon;
import modules.XmlParserGlobalVarsRD;

import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.EK1100IOGroup;
import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.conditionModel.ForceCondition;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.sunrise.connectionLib.CommandType;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.World;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.HandGuidingMotion;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.PositionHold;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
import com.kuka.roboticsAPI.sensorModel.DataRecorder;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;

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
/**
 * @author mpiotrowski
 *
 */
public class MainRunVer03 extends RoboticsAPIApplication {
	@Inject
	private Controller kuka_Sunrise_Cabinet_1;
	private LBR bot;
	private ObjectFrame hotDotDispenser, hotDotCoupon;
	private ObjectFrame appHotDot, appCoupon, heatGunCleanUpPos;
	private ObjectFrame refPos01, refPos02, refPos03;
	private ObjectFrame dynamicTCP, currentTCP, vrsiTCP;
	private ObjectFrame skiveCleanUpApp, skiveCleanRefPos;
	private ObjectFrame refPos;
	private ObjectFrame nullBase;
	private Tool HotDotTest;
	private ServerSocket ssock = null;
	private XmlParserGlobalVarsRD globalVarFromPLC, globalVarFromKRC;
	private XMLParserCoupon coupon;
	private Transformation dynamicTCPoffset; 
	private TimerKCT hotDotHeatUpTimer;
	private DispenserIOver02 dispenser2;
	private Thread TimerThread;
	private String globalsFilePath;
	private String globalsFileNamePLC, globalsFileNameKRC;
	private int smudgeCounter;
	private boolean heatGunCleanUp;
	private DataRecorder recData; 
	private CouponProperties currentCoupon;
	private EK1100IOGroup ek1100IO;
	
	@Override
	public void initialize() {
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		bot = (LBR) getDevice(kuka_Sunrise_Cabinet_1,
				"LBR_iiwa_14_R820_1");
		HotDotTest = getApplicationData().createFromTemplate("HotDotEE");
		nullBase = getApplicationData().getFrame("/nullBase");
		currentTCP = HotDotTest.getFrame("IronVer01");
		dynamicTCP = HotDotTest.getFrame("dynamicTCP");
		vrsiTCP = HotDotTest.getFrame("VRSIfocalPoint");
		hotDotDispenser = getApplicationData().getFrame("/HotDotDispencer");
		hotDotCoupon = getApplicationData().getFrame("/HotDotCoupon");
		appHotDot = getApplicationData().getFrame("/HotDotDispencer/AppHotDot");
		appCoupon = getApplicationData().getFrame("/HotDotCoupon/AppCoupon");
		refPos01 = getApplicationData().getFrame("/HotDotCoupon/ReferencePos01");
		refPos02 = getApplicationData().getFrame("/HotDotCoupon/ReferencePos02");
		refPos03 = getApplicationData().getFrame("/HotDotCoupon/ReferencePos03");
		skiveCleanUpApp = getApplicationData().getFrame("/HotDotCoupon/AppSkiveCleanUp");
		skiveCleanRefPos = getApplicationData().getFrame("/HotDotCoupon/ReferencePosSkiveCleanUp");
		
		
		heatGunCleanUpPos = getApplicationData().getFrame("/HotDotDispencer/HeatGunCleanUp");
		
		hotDotHeatUpTimer = new TimerKCT();
		dispenser2 = new DispenserIOver02(kuka_Sunrise_Cabinet_1);
		ek1100IO = new EK1100IOGroup(kuka_Sunrise_Cabinet_1);
		
		globalsFilePath = "d:/Transfer/UserXMLs/";
		globalsFileNamePLC = "GlobalVarsHotDotPLC.xml";
		globalsFileNameKRC = "GlobalVarsHotDotKRC.xml";
		globalVarFromPLC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNamePLC);
		globalVarFromKRC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNameKRC);
		smudgeCounter = 1;
		heatGunCleanUp = true;
		
		currentCoupon = new CouponProperties(ECouponSectionName.Coupon14);
		refPos = refPos01;
	}
	
	@Override
	public void run() {
		
		setNewHomePosition();
		HotDotTest.attachTo(bot.getFlange());
		
		//bot home
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		//Reset coupon? if yes we set everything as not processed
		setResetCouponStatus();
		
		while (ek1100IO.getEK1100_DI01()) {
			int nUserPressedButton = getApplicationUI().displayModalDialog(
					ApplicationDialogType.QUESTION, "Pick cycle",
					"ApplyHotDot", "Skive", "VRSI Scan");

			switch (nUserPressedButton) {
			case 0:				//*************** SMUDGE 	***************
				smudgeCycle();
				break;
				
			case 1:				//*************** SKIVE 	***************
				skiveCycle();
				break;
				
			case 2:				//*************** SCAN 		***************
				scanCycle();
				break;

			default:
				break;
			}
		}
		
		//bot home
		bot.move(ptpHome().setJointVelocityRel(0.1));
	}
	public void scanCycle() {
		Map<String, Integer> position;
		EHotDotCouponStates state = null;
		int nUserPressedButton = getApplicationUI().displayModalDialog(
				ApplicationDialogType.QUESTION, "Pick VRSI Scan Type",
				"Empty Pin", "Skived Pin");
		switch (nUserPressedButton) {
		case 0:
			state = EHotDotCouponStates.Empty;
			break;
		case 1:
			state = EHotDotCouponStates.Skived;
			break;
		default:
			break;
		}
		while ((coupon.getFirstNotProcessed(state) != null)){
			position = coupon.getFirstNotProcessed(state);
			int row = position.get("row");
			int column = position.get("column");
			emptyScanCycle(row, column);
			coupon.setRowColumnValue(row, column, EHotDotCouponStates.Scaned);
		}	
	}
	
	
	public void smudgeCycle() {
		Map<String, Integer> position;
		while ((coupon.getFirstNotProcessed(EHotDotCouponStates.Empty) != null) || (coupon.getFirstNotProcessed(EHotDotCouponStates.Scaned) != null)) {
			if (coupon.getFirstNotProcessed(EHotDotCouponStates.Empty) != null) {
				position = coupon.getFirstNotProcessed(EHotDotCouponStates.Empty);
			} else {
				position = coupon.getFirstNotProcessed(EHotDotCouponStates.Scaned);
			}
			int row = position.get("row");
			int column = position.get("column");
			if (row == 5 || row == 6) {
				currentCoupon = new CouponProperties(ECouponSectionName.Coupon56);
			}
			if(globalVarFromPLC.getVarBoolean("heatGunCleanUp") && heatGunCleanUp) {
				heatGunCleanUp();
				heatGunCleanUp = false;
			}
			heatGunCleanUp = true;
			pickHotDot();
			applyHotDot(row,column);
			if (globalVarFromPLC.getVarBoolean("smudgeMulti") && (smudgeCounter < globalVarFromPLC.getVarInteger("smudgeCounter"))) {
				smudgeCounter++;
			} else {
				smudgeCounter=1;
				coupon.setRowColumnValue(row, column, EHotDotCouponStates.Smudged);
			}
		}
		System.out.println("No empty slots to apply hot dot");
	}
	public void skiveCycle() {
		Map<String, Integer> position;
		while (coupon.getFirstNotProcessed(EHotDotCouponStates.Smudged) != null) {
			position = coupon.getFirstNotProcessed(EHotDotCouponStates.Smudged);
			int row = position.get("row");
			int column = position.get("column");
			skiveHotDotMotion(row, column);
			coupon.setRowColumnValue(row, column, EHotDotCouponStates.Skived);
			
			int nUserPressedButton = getApplicationUI().displayModalDialog(
					ApplicationDialogType.QUESTION, "Continue or VRSI Scan?",
					"Continue", "VRSI Scan");
			switch (nUserPressedButton) {
			case 0:
				break;
				
			case 1:
				emptyScanCycle(row, column);
				break;
					
			default:
				break;
			}
		}
		System.out.println("No slots to skive");
	}
	public void applyHotDot(int row, int column) {
		CouponCalc coupon = new CouponCalc();
		Frame applyHotDot, appHotDot;
		double rowOffset = currentCoupon.getRowsOffset();				// mm
		double columnOffset = currentCoupon.getColumnsOffset();			// mm
		System.out.println("ColumnOffset: " + columnOffset);
		//approach coupon  
		currentTCP.move(ptp(appCoupon).setJointVelocityRel(0.3).setBlendingCart(20));
		applyHotDot = coupon.calculateXYpos(row, column, refPos.copy(), rowOffset, columnOffset, ECalcDirection.XisRow);
		appHotDot = applyHotDot.copy();
		appHotDot.setZ(appHotDot.getZ() + 50);
		System.out.println("Approaching position row: " + row + " column: " + column);
		currentTCP.moveAsync(ptp(appHotDot).setJointVelocityRel(0.3).setBlendingCart(10));
		smudgeProcess(applyHotDot);
		currentTCP.moveAsync(lin(appHotDot).setCartVelocity(40).setBlendingCart(30));
		currentTCP.move(ptp(appCoupon).setJointVelocityRel(0.3).setBlendingCart(20));
		
	}
	
	private void smudgeProcess(Frame FIsmudgeStartPos) {
		
		double approachDistance = globalVarFromPLC.getVarDouble("smudgeCouponAppDist");	// mm
		double velocity = globalVarFromPLC.getVarDouble("smudgeVelocityPass1");			// mm/s
		double smudgeAngle = globalVarFromPLC.getVarDouble("smudgeAnglePass1");		 	// degrees
		double length = globalVarFromPLC.getVarDouble("smudgeLengthPass1");				// mm
		double startOffset = globalVarFromPLC.getVarDouble("smudgeStartOffsetPass1");		// mm
		double smudgeXforce = globalVarFromPLC.getVarDouble("smudgeXForcePass1");			// mm
		double smudgeStiffnessX = globalVarFromPLC.getVarDouble("smudgeStiffnessXPass1");
		double smudgeStiffnessZ = globalVarFromPLC.getVarDouble("smudgeStiffnessZPass1");
		double smudgeOffsetDD = globalVarFromPLC.getVarDouble("smudgeOffsetDD");
		double betaTCProtation;
		
		//X positive is row !!!
		Frame smudgeBeginPos1; 
		Frame smudgeBeginPos2 = null;
		smudgeBeginPos1 = FIsmudgeStartPos.copy();
		//smudgeBeginPos1.setBetaRad(smudgeBeginPos1.getBetaRad() + Math.toRadians(smudgeAngle));
		smudgeBeginPos1.setX(smudgeBeginPos1.getX() - startOffset);
		smudgeBeginPos1.setZ(smudgeBeginPos1.getZ() + approachDistance);
		
			if (smudgeCounter > 1 ) {
				smudgeBeginPos1.setY(smudgeBeginPos1.getY() + smudgeOffsetDD);	
			}		
		
		betaTCProtation = currentTCP.getBetaRad();
		betaTCProtation = betaTCProtation + Math.toRadians(smudgeAngle);
		dynamicTCPoffset = Transformation.ofRad(	
				 currentTCP.getX()
				,currentTCP.getY()
				,currentTCP.getZ()
				,currentTCP.getAlphaRad()
				,betaTCProtation
				,currentTCP.getGammaRad());
		HotDotTest.changeFramePosition(dynamicTCP, dynamicTCPoffset);
		
		TouchForceRecord findSurface = new TouchForceRecord(); 
		
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		CartesianImpedanceControlMode smudgeMode = new CartesianImpedanceControlMode();
		mode.parametrize(CartDOF.TRANSL).setStiffness(3000);
		mode.parametrize(CartDOF.X).setStiffness(smudgeStiffnessX);
		mode.parametrize(CartDOF.Z).setStiffness(smudgeStiffnessZ);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		mode.parametrize(CartDOF.ALL).setDamping(.7);
		
		//smuge first pass
		dynamicTCP.move(ptp(smudgeBeginPos1).setJointVelocityRel(0.3));
		
		if (hotDotHeatUpTimer.getTimerValue() < globalVarFromPLC.getVarLong("hotDotHeatUpTime")) {
			System.out.println("Waiting for heat up timer, current value: " + hotDotHeatUpTimer.getTimerValue());
			while (hotDotHeatUpTimer.getTimerValue() < globalVarFromPLC.getVarLong("hotDotHeatUpTime")) {}
		}
		hotDotHeatUpTimer.timerStopAndKill();
		
		findSurface.recordPosition(searchDir.PosX, 5, 10, 5, 0, dynamicTCP, hotDotCoupon, bot);
		if (findSurface.getResult()) {
			smudgeBeginPos2 = findSurface.getPosition();
		} else {
			System.err.println("Surface not found");
			getApplicationControl().halt();
		}
		smudgeMode = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.X, smudgeXforce, 5000);
		dynamicTCP.move(linRel(0,0,-length).setCartVelocity(velocity).setMode(smudgeMode));
		dynamicTCP.move(linRel(-approachDistance,0,0).setCartVelocity(velocity*4));

		//smuge second pass
		if(!globalVarFromPLC.getVarBoolean("secondSmudgePass")) {
			System.err.println("Second smudge pass disabled!");
			return;
		}
		velocity = globalVarFromPLC.getVarDouble("smudgeVelocityPass2");			// mm/s
		smudgeAngle = globalVarFromPLC.getVarDouble("smudgeAnglePass2");		 	// degrees
		length = globalVarFromPLC.getVarDouble("smudgeLengthPass2");				// mm
		startOffset = globalVarFromPLC.getVarDouble("smudgeStartOffsetPass2");		// mm
		smudgeXforce = globalVarFromPLC.getVarDouble("smudgeXForcePass2");			// mm
		smudgeStiffnessX = globalVarFromPLC.getVarDouble("smudgeStiffnessXPass2");
		smudgeStiffnessZ = globalVarFromPLC.getVarDouble("smudgeStiffnessZPass2");
		
		mode.parametrize(CartDOF.TRANSL).setStiffness(3000);
		mode.parametrize(CartDOF.X).setStiffness(smudgeStiffnessX);
		mode.parametrize(CartDOF.Z).setStiffness(smudgeStiffnessZ);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		
		smudgeBeginPos1 = FIsmudgeStartPos.copy();
		smudgeBeginPos1.setX(smudgeBeginPos1.getX() - startOffset);
		smudgeBeginPos1.setZ(smudgeBeginPos1.getZ() + approachDistance);
		
		betaTCProtation = currentTCP.getBetaRad();
		betaTCProtation = betaTCProtation + Math.toRadians(smudgeAngle);
		dynamicTCPoffset = Transformation.ofRad(	
				 currentTCP.getX()
				,currentTCP.getY()
				,currentTCP.getZ()
				,currentTCP.getAlphaRad()
				,betaTCProtation
				,currentTCP.getGammaRad());
		HotDotTest.changeFramePosition(dynamicTCP, dynamicTCPoffset);
		
		dynamicTCP.move(lin(smudgeBeginPos1).setCartVelocity(velocity*4));
		findSurface.recordPosition(searchDir.PosX, 5, 10, 5, 0, dynamicTCP, hotDotCoupon, bot);
		if (!findSurface.getResult()) {
			System.err.println("Surface not found");
			getApplicationControl().halt();
		}
		
		smudgeMode = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.X, smudgeXforce, 5000);
		dynamicTCP.move(linRel(0,0,-length).setCartVelocity(velocity).setMode(smudgeMode));
		
	}
	
	
	
	private void skiveHotDotMotion(int row, int column) {
		
		// <Data recorder setup> 
		recData = new DataRecorder();
		Calendar myDate = Calendar.getInstance(); // date and time
		SimpleDateFormat mySdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss"); // formated to your likings
		recData.setFileName("HotDotR" + row + "C" + column + "_"
				+ mySdf.format(myDate.getTime()) + "_log.csv"); // file name to write to
		recData.setTimeout(-1, TimeUnit.SECONDS); // no timeout
		recData.setSampleInterval(globalVarFromPLC.getVarInteger("dataRecorderFrequency")); // record every  mills
		//myData.addExternalJointTorque(bot); // record external joint torques
		// <Data recorder setup/> 
		
		CouponCalc coupon = new CouponCalc();
		Frame skiveHotDot, appSkiveHotDot;
		double rowOffset = currentCoupon.getRowsOffset();				// mm
		double columnOffset = currentCoupon.getColumnsOffset();			// mm
		double approachDistance = globalVarFromPLC.getVarDouble("skiveCouponAppDist");	// mm
		double startOffset = globalVarFromPLC.getVarDouble("skiveStartOffset");		// mm
	    
		currentTCP.moveAsync(ptp(appCoupon).setJointVelocityRel(0.3).setBlendingCart(20));
		skiveHotDot = coupon.calculateXYpos(row, column, refPos.copy(), rowOffset, columnOffset, ECalcDirection.XisRow);
		appSkiveHotDot = skiveHotDot.copy();
		appSkiveHotDot.setZ(appSkiveHotDot.getZ() + 50);
		skiveHotDot.setZ(skiveHotDot.getZ() + approachDistance);
		skiveHotDot.setX(skiveHotDot.getX() + startOffset);
		System.out.println("Approaching Skive position row: " + row + " column: " + column);
		currentTCP.moveAsync(ptp(appSkiveHotDot).setJointVelocityRel(0.3).setBlendingCart(10));
		currentTCP.moveAsync(lin(skiveHotDot).setCartVelocity(40).setBlendingCart(10));
		skiveProcess(skiveHotDot);
		currentTCP.moveAsync(ptp(appCoupon).setJointVelocityRel(0.3).setBlendingCart(20));
		skiveCleanUp();
	}
	
	private void skiveProcess(Frame FIskiveStartPos ) {
		
		double approachDistance = globalVarFromPLC.getVarDouble("skiveCouponAppDist");	//mm
		double velocity = globalVarFromPLC.getVarDouble("skiveVelocity");			// mm/s
		double skiveAngle = globalVarFromPLC.getVarDouble("skiveAngle");		 	// degrees
		double length = globalVarFromPLC.getVarDouble("skiveLength");				// mm
		double startOffset = globalVarFromPLC.getVarDouble("skiveStartOffset");	// mm
		double skiveXforce = globalVarFromPLC.getVarDouble("skiveXforce");	// N
		double skiveZforce = globalVarFromPLC.getVarDouble("skiveZforce");	// N
		double skiveStiffnessX = globalVarFromPLC.getVarDouble("skiveStiffnessX");
		double skiveStiffnessZ = globalVarFromPLC.getVarDouble("skiveStiffnessZ");
		
		//Frame smudgeStartPos = bot.getCurrentCartesianPosition(currentTCP, hotDotCoupon);
		Frame skiveUpPos, skiveBeginPos, skiveEndPos;
		skiveBeginPos = FIskiveStartPos.copy();
		System.out.println("SkiveAngle: " + Math.toDegrees(FIskiveStartPos.getBetaRad()));
		skiveBeginPos.setZ(skiveBeginPos.getZ() - approachDistance);
		skiveEndPos = skiveBeginPos.copy();
		skiveEndPos.setX(skiveEndPos.getX() - length);
		skiveUpPos = skiveEndPos.copy();
		skiveUpPos.setZ(skiveUpPos.getZ() + approachDistance);
		
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		CartesianImpedanceControlMode skiveMode = new CartesianImpedanceControlMode();
		
		mode.parametrize(CartDOF.TRANSL).setStiffness(3000);
		mode.parametrize(CartDOF.X).setStiffness(skiveStiffnessX);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		
		//calc of new tool
		//point is to make tool square to base (coupon) based on current angle of skiving
		//we want to keep it square so force applied in Z direction of tool is perpendicular
		//with coupon, there has to be better way to calculate this 
		double TCPskiveBeta = currentTCP.getBetaRad();
		TCPskiveBeta = TCPskiveBeta + Math.toRadians(skiveAngle);
		dynamicTCPoffset = Transformation.ofRad(	currentTCP.getX()
				,currentTCP.getY()
				,currentTCP.getZ()
				,currentTCP.getAlphaRad()
				,TCPskiveBeta
				,currentTCP.getGammaRad());
		HotDotTest.changeFramePosition(dynamicTCP, dynamicTCPoffset);
		
		TouchForceRecord findSurface = new TouchForceRecord(); 
		
		recData.addCartesianForce(dynamicTCP, null); //record external
		recData.addCurrentCartesianPositionXYZ(dynamicTCP, hotDotCoupon);
		recData.enable(); // enable Data recorder
		
		//skive first pass
		dynamicTCP.move(lin(skiveBeginPos).setCartVelocity(velocity*2).setMode(mode));
		findSurface.recordPosition(searchDir.PosX, 20, 10, 5, 0, dynamicTCP, hotDotCoupon, bot);
		skiveMode = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.X, skiveXforce, skiveStiffnessX);
		//skiveMode = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.Z, skiveZforce, skiveStiffnessZ);
		skiveMode.parametrize(CartDOF.Y).setStiffness(5000);
		skiveMode.parametrize(CartDOF.Z).setStiffness(skiveStiffnessZ);
		skiveMode.parametrize(CartDOF.ROT).setStiffness(300);
		skiveMode.parametrize(CartDOF.A).setStiffness(300);
		
		recData.startRecording();
		//Skive motion - this removes layer of extra goo
		dynamicTCP.move(linRel(0,0,length).setCartVelocity(velocity).setMode(skiveMode));
		//SKive past motion to keep goo on an iron
		Frame skiveSmudgePos = bot.getCurrentCartesianPosition(dynamicTCP, hotDotCoupon);
		dynamicTCP.move(linRel(-5,0,10,0, Math.toRadians(-10),0,nullBase).setCartVelocity(velocity*4));
		recData.stopRecording(); 
		
		//if skive smudging if requested
		if (globalVarFromPLC.getVarBoolean("reverseSkive")) {
			//ThreadUtil.milliSleep(3000);
			skiveMode = CartesianSineImpedanceControlMode.createDesiredForce(CartDOF.X, 5, 4000);
			skiveMode.parametrize(CartDOF.Y).setStiffness(5000);
			skiveMode.parametrize(CartDOF.Z).setStiffness(5000);
			skiveMode.parametrize(CartDOF.ROT).setStiffness(300);
			dynamicTCP.move(lin(skiveSmudgePos).setCartVelocity(15).setMode(skiveMode));
			dynamicTCP.move(linRel(0,0,-length).setCartVelocity(15).setMode(skiveMode));
		}
		dynamicTCP.move(linRel(-approachDistance,0,0).setCartVelocity(velocity*4));
	}
	
	public void heatGunCleanUp() {
		CartesianSineImpedanceControlMode mode;
		double heatGunCleanUpPush = globalVarFromPLC.getVarDouble("heatGunCleanUpPush");
		double heatGunCleanUpStartOffset = globalVarFromPLC.getVarDouble("heatGunCleanUpStartOffset");
		
	    Frame AppCleanUpPos = calculateCleanUpPos(heatGunCleanUpPos.copy());
	    
//		while (AppCleanUpPos == null) {
//			System.out.println("ScotchBrite swapped?!");
//			AppCleanUpPos = calculateCleanUpPos(heatGunCleanUpPos.copy());
//		}
		//Frame AppCleanUpPos = heatGunCleanUpPos.copy();
		Frame CleanUpPos = AppCleanUpPos.copy();
		
		AppCleanUpPos.setZ(AppCleanUpPos.getZ() + 50);
		
		CleanUpPos.setZ(CleanUpPos.getZ() + heatGunCleanUpStartOffset);
		
		currentTCP.moveAsync(ptp(AppCleanUpPos).setJointVelocityRel(0.3).setBlendingCart(10));
		currentTCP.moveAsync(lin(CleanUpPos).setBlendingCart(10).setCartVelocity(50));
		currentTCP.moveAsync(linRel(0,0,-heatGunCleanUpStartOffset + heatGunCleanUpPush, hotDotDispenser).setBlendingCart(10).setCartVelocity(40));
		currentTCP.moveAsync(linRel(80, 0,0,hotDotDispenser ).setBlendingCart(10).setCartVelocity(75));
		currentTCP.move(linRel(0, 0, +heatGunCleanUpStartOffset - heatGunCleanUpPush, hotDotDispenser).setCartVelocity(40));
		currentTCP.moveAsync(linRel(0, 0, -heatGunCleanUpStartOffset + heatGunCleanUpPush, hotDotDispenser).setBlendingCart(10).setCartVelocity(40));
		currentTCP.moveAsync(linRel(-55, 0, 0, hotDotDispenser).setBlendingCart(10).setCartVelocity(75));
		currentTCP.moveAsync(lin(CleanUpPos).setBlendingCart(10).setCartVelocity(40));
		currentTCP.moveAsync(ptp(AppCleanUpPos).setJointVelocityRel(0.3).setBlendingCart(10));
		
		//mode = CartesianSineImpedanceControlMode.createSinePattern(CartDOF.Y, 5, 15, 100);
		//mode.parametrize(CartDOF.X,CartDOF.Z).setStiffness(5000);
		
	}
	public Frame calculateCleanUpPos(Frame initialPos) {
		XMLParserCoupon scotchBrite = new XMLParserCoupon(1, "d:/Transfer/UserXMLs/CouponScotchBrite.xml");
		Frame cleanUpPos = initialPos.copy();
		double xOffset = 20;
		double yOffset = 10;
		int xSquares = 3;
		int ySquares = 12;
		
		for (int i = 1; i <= xSquares; i++) {
			for (int j = 1; j <= ySquares; j++) {
				if(scotchBrite.getRowColumnValue(i, j) == EHotDotCouponStates.Smudged) {
					continue;
				} else if (scotchBrite.getRowColumnValue(i, j) == EHotDotCouponStates.Empty) {
					System.out.println("ScotchBrite row: " + i + " column:" + j);
					cleanUpPos.setX(cleanUpPos.getX() + (i-1)*xOffset);
					cleanUpPos.setY(cleanUpPos.getY() + (j-1)*yOffset);
					scotchBrite.setRowColumnValue(i, j, EHotDotCouponStates.Smudged);
					return cleanUpPos;
				} 
			}
		}
		int nUserPressedButton = getApplicationUI().displayModalDialog(
				ApplicationDialogType.QUESTION, "Scotch Brite needs replacing",
				"OK");
		switch (nUserPressedButton) {
			
		default:
			scotchBrite.resetCoupon();
			break;
		}
		scotchBrite.resetCoupon();
		return cleanUpPos;
	}
	/**
	 * @param searchDelay - time in milliseconds, delay iron at pickup position
	 */
	public void pickHotDot() {
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		
		TimerThread = new Thread(hotDotHeatUpTimer);
		
		double searchDistance = 20; // mm
		double touchForce;		 	// N
		double pickOffset;			// mm
		long searchDelay = globalVarFromPLC.getVarLong("pickDelay"); 	// ms
		
		if (globalVarFromKRC.getVarBoolean("dispenserLowOnHotDots")) {
			int nUserPressedButton = getApplicationUI().displayModalDialog(
					ApplicationDialogType.QUESTION, "Load More Hot Dots !!!",
					"OK");
			switch (nUserPressedButton) {
				
			default:
				break;
			}
		}
		
		//trigger dispenser and wait for feedback
		if (dispenser2.getDispenserLowOnHotDots()) {
			globalVarFromKRC.setVar("dispenserLowOnHotDots", "true");
		} else {
			globalVarFromKRC.setVar("dispenserLowOnHotDots", "false");
		}
			
		System.out.println("Hot dot ready to pick");
		touchForce = globalVarFromPLC.getVarDouble("pickForce");
		pickOffset = globalVarFromPLC.getVarDouble("pickOffset");
		
		
		Frame Start = appHotDot.copy();
		Frame BeforePick02 = appHotDot.copy();
		Frame Pick = appHotDot.copy();
		Start.setZ(Start.getZ() + 70);
		Start.setX(Start.getX() + 30);
		
		BeforePick02.setZ(BeforePick02.getZ() + pickOffset );
		Pick.setZ(Pick.getZ() - pickOffset - searchDistance);
		currentTCP.moveAsync(ptp(Start).setJointVelocityRel(0.3));
		if (!globalVarFromPLC.getVarBoolean("stopDispencer")) {
			while (!dispenser2.getHotDotPresent()) {
				// wait for hot dot
			}
		}
		currentTCP.move(ptp(BeforePick02).setJointVelocityRel(0.3));

		mode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		mode.parametrize(CartDOF.X).setStiffness(5000);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		mode.parametrize(CartDOF.B).setStiffness(250);
		
		ForceCondition force2 = ForceCondition.createNormalForceCondition(currentTCP,CoordinateAxis.X, touchForce);
		IMotionContainer mc = currentTCP.move(linRel((pickOffset + searchDistance),0,0).setMode(mode).breakWhen(force2).setCartVelocity(10));
		if (mc.hasFired(force2)) {
			getLogger().info("Picking Hot Dot...");
			ThreadUtil.milliSleep(searchDelay);
			TimerThread.start();
			hotDotHeatUpTimer.setTimerValue(0);
			hotDotHeatUpTimer.timerStart();	

		} else {
			System.err.println("Hot Dot not found!!!");
		}
		currentTCP.move(linRel(-0.5,0,0,0,Math.toRadians(3),0,currentTCP).setCartVelocity(50));
		currentTCP.move(lin(Start).setCartVelocity(50).setBlendingCart(20));
		
		if (globalVarFromPLC.getVarBoolean("doublePick")) {
			while (!dispenser2.getHotDotPresent()) {
				// wait for hot dot
			}
			BeforePick02.setZ(BeforePick02.getZ() + 1);
			currentTCP.move(ptp(BeforePick02).setJointVelocityRel(0.3));
			
			mc = currentTCP.move(linRel((pickOffset + searchDistance),0,0).setMode(mode).breakWhen(force2).setCartVelocity(10));
			if (mc.hasFired(force2)) {
				getLogger().info("Picking Hot Dot2...");
				ThreadUtil.milliSleep(searchDelay);	
			} else {
				System.err.println("Hot Dot not found!!!");
			}
			
			currentTCP.moveAsync(lin(Start).setCartVelocity(50).setBlendingCart(20));
		}
	}
	private void skiveCleanUp () {
		currentTCP.moveAsync(ptp(skiveCleanUpApp).setJointVelocityRel(0.3).setBlendingCart(30));
		Frame skiveCleanUpStartPos = calculateSkiveCleanUpPos(skiveCleanRefPos);
		currentTCP.move(lin(skiveCleanUpStartPos).setCartVelocity(50));
		
		double skiveCleanUpForce 	= globalVarFromPLC.getVarDouble("skiveCleanUpForce");
		double skiveCleanUpVel 		= globalVarFromPLC.getVarDouble("skiveCleanUpVel");
		double skiveCleanUpDistance = globalVarFromPLC.getVarDouble("skiveCleanUpDistance");
		
		CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		ForceCondition forceCond = ForceCondition.createNormalForceCondition(currentTCP, CoordinateAxis.Z, 5);
		
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000);
		mode.parametrize(CartDOF.ROT).setStiffness(300);
		IMotionContainer mc = currentTCP.move(linRel(0, 0, 50, nullBase)
				.setMode(mode).setCartVelocity(30).breakWhen(forceCond));
		
		if (mc.hasFired(forceCond)) {
			System.out.println("Reference position found");
			mode.parametrize(CartDOF.Z).setStiffness(3500).setAdditionalControlForce(skiveCleanUpForce);
			currentTCP.moveAsync(linRel((-1)*skiveCleanUpDistance,0,0,0,Math.toRadians(-10),0, nullBase)
					.setMode(mode)
					.setCartVelocity(skiveCleanUpVel)
					.setBlendingCart(20));
		} else {
			//nothing was found
			System.err.println("Reference position not found");
			currentTCP.move(lin(skiveCleanRefPos).setCartVelocity(50));
		}
		currentTCP.moveAsync(ptp(skiveCleanUpApp).setJointVelocityRel(0.3).setBlendingCart(30));
	}
	
	private Frame calculateSkiveCleanUpPos(ObjectFrame skiveCleanRefPos) {
			Frame skiveCleanUpStartPos = skiveCleanRefPos.copy();
			XMLParserCoupon scotchBrite = new XMLParserCoupon(2, "d:/Transfer/UserXMLs/CouponScotchBrite.xml");
			int ySquares = 12;
			double yDistance = 15;
			for (int column = 1; column < ySquares; column++) {
				if (scotchBrite.getRowColumnValue(1, column) == EHotDotCouponStates.Smudged) {
					continue;
				} else {
					if (scotchBrite.getRowColumnValue(1, column) == EHotDotCouponStates.Empty) {
						scotchBrite.setRowColumnValue(1, column, EHotDotCouponStates.Smudged);
						skiveCleanUpStartPos.setY(skiveCleanUpStartPos.getY() + (column-1)*yDistance);
						return skiveCleanUpStartPos;
					}
				}
			}
			int nUserPressedButton = getApplicationUI().displayModalDialog(
					ApplicationDialogType.QUESTION, "Move Scotch Brite!!!",
					"OK");
			switch (nUserPressedButton) {
				
			default:
				scotchBrite.resetCoupon();
				break;
			}
			return skiveCleanUpStartPos;
	}
	
	/**
	 * @param row
	 * @param column
	 */
	public void emptyScanCycle(int row, int column) {
		int holeNumber = ((row-1)*10 + column); 
		CouponCalc coupon = new CouponCalc();
		Frame vrsiScanPos, vrsiScanAppPos;
		double rowOffset = currentCoupon.getRowsOffset();					// mm
		double columnOffset = currentCoupon.getColumnsOffset();				// mm
		double fastener01offset = currentCoupon.getFastenerOffset();	 	// mm
		int nUserPressedButton;
		
		//approach coupon  
		//vrsiTCP.moveAsync(ptp(appCoupon).setJointVelocityRel(0.3).setBlendingCart(20));
		vrsiScanPos = coupon.calculateXYpos(row, column, refPos.copy(), rowOffset, columnOffset, ECalcDirection.XisRow);
		vrsiScanPos.setX(vrsiScanPos.getX() + fastener01offset);
		vrsiScanAppPos = vrsiScanPos.copy();
		vrsiScanAppPos.setZ(vrsiScanAppPos.getZ() + 10);
		System.out.println("Approaching Scan position row: " + row + " column: " + column + " hole number: " + holeNumber);
		vrsiTCP.move(ptp(vrsiScanAppPos).setJointVelocityRel(0.3));
		vrsiTCP.move(lin(vrsiScanPos).setCartVelocity(40));
		nUserPressedButton = getApplicationUI().displayModalDialog(
				ApplicationDialogType.QUESTION, "Hole number: " + holeNumber + " Scan Completed?",
				"Yes");
		switch (nUserPressedButton) {
		case 0:
			break;

		default:
			break;
		}
		//vrsiTCP.moveAsync(lin(vrsiScanAppPos).setCartVelocity(40).setBlendingCart(30));
		
	}
	private void xmlServerTest() throws IOException {
		//		*********  XML server test  *******		
		//		try {
		//			xmlServerTest();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		
		//		getApplicationControl().halt();
		//		************************************

		InputStream in = null;
		OutputStream out = null;

		System.out.println("server starts");
		ssock = new ServerSocket(30008); // open socket
		Socket sock = ssock.accept(); // accept connection
		in = sock.getInputStream();// Receive from socket
		out = new FileOutputStream("d:/Transfer/UserXMLs/testxmldump.xml");
		byte[] bytes = new byte[16 * 1024];
		int count;
		System.out.println("Listening");
		while ((count = in.read(bytes)) > 0) {
			out.write(bytes, 0, count);
			System.out.println(bytes);
		}

		out.close();
		in.close();
		sock.close();
		ssock.close();
		System.out.println("Done");

	}

    public void handGuideRecord() {
    	CartesianImpedanceControlMode mode = new CartesianImpedanceControlMode();
		TouchForceRecord scaleTouch = new TouchForceRecord();
		EK1100IOGroup UserIO = new EK1100IOGroup(kuka_Sunrise_Cabinet_1);
		IMotionContainer positionHoldContainer = null;
		List<Frame> positions = new ArrayList<Frame>();
		
		HotDotTest.attachTo(bot.getFlange());
		Frame Start = appHotDot.copy();
		Start.setZ(Start.getZ() + 100);
		currentTCP.move(ptp(Start).setJointVelocityRel(0.05));
		currentTCP.move(ptp(appHotDot).setJointVelocityRel(0.05));
		//scaleTouch.recordPosition(searchDir.PosZ, 5, 30, 5, 0, currentTCP, currentBase, bot);
		
		mode.parametrize(CartDOF.TRANSL).setStiffness(5).setDamping(0.1);
		mode.parametrize(CartDOF.ROT).setStiffness(5);
		positionHoldContainer = currentTCP.moveAsync(new PositionHold(mode, -10, TimeUnit.SECONDS));
		System.out.println("Recording");
		
		while (!UserIO.getEK1100_DI01()) {
			positions.add(bot.getCurrentCartesianPosition(currentTCP));
			ThreadUtil.milliSleep(250);
		}
		System.err.println("Recording Canceled");
		positionHoldContainer.cancel();
		mode.parametrize(CartDOF.TRANSL).setStiffness(5000).setDamping(0.7);
		mode.parametrize(CartDOF.ROT).setStiffness(250);
		currentTCP.move(ptp(bot.getCurrentCartesianPosition(currentTCP)));
		
		System.out.println(positions.size());
		System.out.println("Ready for playback?");
		while (!UserIO.getEK1100_DI02()) {
			//just looping
		}
		currentTCP.move(ptp(appHotDot).setJointVelocityRel(0.05));
		
		for ( Frame point : positions) {
				currentTCP.moveAsync(lin(point).setCartVelocity(10).setOrientationVelocity(Math.toRadians(7)).setBlendingCart(5));	
				
		}
		
		currentTCP.move(ptp(Start).setJointVelocityRel(0.05));
    }
    
    private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home , WTF roboter?
		JointPosition newHome = new JointPosition(Math.toRadians(0), Math.toRadians(50),
				0.0, Math.toRadians(-110), 0.0, Math.toRadians(-116), Math.toRadians(0));
		bot.setHomePosition(newHome);

	}
    
    public void setResetCouponStatus() {
    	int nUserPressedButton;
    	nUserPressedButton = getApplicationUI().displayModalDialog(
				ApplicationDialogType.QUESTION, "Which Coupon: Regular or Torx???",
				"Regular", "Torq");
    	switch (nUserPressedButton) {
		case 0:
			coupon = new XMLParserCoupon(2, "d:/Transfer/UserXMLs/CouponHotDot02torx.xml");
			break;
		case 1:
			coupon = new XMLParserCoupon(1, "d:/Transfer/UserXMLs/CouponHotDot02torx.xml");
			break;

		default:
			break;
    	}	
    	
		coupon.displayValues();
		nUserPressedButton = getApplicationUI().displayModalDialog(
				ApplicationDialogType.QUESTION, "Reset Coupon Status???",
				"Yes", "No");
		
		switch (nUserPressedButton) {
		case 0:
			coupon.resetCoupon();
			break;

		default:
			break;
		}
		
	}
    
//    @Override
//	public void dispose() {
//
//		try {
//			// Add your "clean up" code here e.g.
//			ssock.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
    @Override
    public void dispose()
    {
        try {
        	// Add your "clean up" code here e.g.
        	// ssock.close();
            hotDotHeatUpTimer.timerStopAndKill(); 
            dispenser2.killThread();
        } catch (NullPointerException e ) {
        	System.err.println("One or more threads were not initialized");
        }
        finally
        {
            super.dispose();
        }
    }

	/**
	 * Auto-generated method stub. Do not modify the contents of this method.
	 */
	public static void main(String[] args) {
		MainRunVer03 app = new MainRunVer03();
		app.runApplication();
	}
}
