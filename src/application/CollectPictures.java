package application;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.CognexIIWA_FTPlib;
import modules.CognexIIWA_Telnetlib;
import modules.CouponXMLparser;
import modules.XMLParserCoupon;
import modules.XmlParserGlobalVarsRD;
import modules.Common.ECognexCommand;
import modules.Common.ECognexTrigger;
import modules.Common.EHotDotCouponStates;
import modules.Common.searchDir;
import modules.TimerKCT;
import modules.TouchForceRecord;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;

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
public class CollectPictures extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool KSAF_EE;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
	private ObjectFrame referencePos;
    private TimerKCT timer;
    private String globalsFilePath;
	private String globalsFileNamePLC, globalsFileNameKRC;
    private XmlParserGlobalVarsRD globalVarFromPLC, globalVarFromKRC;
    private CognexIIWA_Telnetlib telnet;
    private CognexIIWA_FTPlib ftp;
    private XMLParserCoupon coupon1;
    
	@Override
	public void initialize() {
		// initialize your application here
		KSAF_EE = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		currentTCP = KSAF_EE.getFrame("NutRunner_HL70_06");
		nullBase = getApplicationData().getFrame("/nullBase");
		startPos = getApplicationData().getFrame("/CouponBase/couponBaseApp");
		referencePos = getApplicationData().getFrame("/CouponBase/referencePosHL08");
		telnet = new CognexIIWA_Telnetlib("172.31.1.69","admin","");
		ftp = new CognexIIWA_FTPlib("172.31.1.69","admin","");
		globalsFilePath = "d:/Transfer/UserXMLs/";
		globalsFileNamePLC = "GlobalVarsCognexPLC.xml";
		globalsFileNameKRC = "GlobalVarsCognexKRC.xml";
		globalVarFromPLC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNamePLC);
		globalVarFromKRC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNameKRC);
		coupon1 = new XMLParserCoupon(1, "d:/Transfer/UserXMLs/CognexIIWACouponHL08.xml");
	}

	@Override
	public void run() {
		
		
		
		double rowOffset = 25.1;
		double columnOffset = 25.1;
		double currentExposureTime; 
		currentExposureTime = globalVarFromPLC.getVarDouble("exposureTime");
		ftp.setFtpLocalFileName(" HL70_08" + " Exposure " + currentExposureTime + ".jpg");
		ftp.setFtpLocalDownloadPath("d:/Transfer/CognexPics/");
		ftp.setFtpRemoteFileName("Image.jpg");
		timer = new TimerKCT();
		Thread TimerThread;
		TimerThread = new Thread(timer);
		
		
		//bot home
		setNewHomePosition();
		KSAF_EE.attachTo(bot.getFlange());
		System.out.println(coupon1.getFirstNotProcessed(EHotDotCouponStates.Empty));
		
		while (true) {

			System.out.println("Moving to Home/Start position");
			bot.move(ptpHome().setJointVelocityRel(0.3));

			currentTCP.move(ptp(startPos).setJointVelocityRel(0.3));
			
			telnetLogin();
			telnet.disconnect();
			
			System.err.println("Coupon RESET");
			coupon1.resetCoupon();
			
			Map processPosition = new HashMap();
			while (coupon1.getFirstNotProcessed(EHotDotCouponStates.Empty) != null) {
				processPosition = coupon1.getFirstNotProcessed(EHotDotCouponStates.Empty);
				int row = (Integer) processPosition.get("row");
				int column = (Integer) processPosition.get("column");
				Frame TheoreticalPos = gridCalculation(referencePos.copy(), row,
						column, rowOffset, columnOffset,0);
				getLogger().info(
						"**********  Position: Row:  " + row + " Column: "
								+ column + "**********");

				getLogger().info("XYZ: " + TheoreticalPos);
				
				//Transformation tcpToVisionShift = Transformation.ofTranslation(18.0, -30, 0.0);
				//TheoreticalPos.transform(tcpToVisionShift);
				
				//   Move to process position
				currentTCP.move(lin(TheoreticalPos).setCartVelocity(50).setCartAcceleration(100));
				telnetLogin();
				currentExposureTime = globalVarFromPLC.getVarDouble("exposureTime");
				telnet.sendCognexCommand(ECognexCommand.SF, "A", 21, currentExposureTime);
				telnet.sendCognexTrigger(ECognexTrigger.SE8);
				telnet.disconnect();
				ThreadUtil.milliSleep(500);
				downloadImage();
				coupon1.setRowColumnValue(row, column, EHotDotCouponStates.Scaned);
			}
				
				
			bot.move(ptpHome().setJointVelocityRel(0.3));
		}
	}

	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(
				Math.toRadians(15),		//A1
				Math.toRadians(-5), 	//A2
				Math.toRadians(0), 		//A3
				Math.toRadians(-117),	//A4
				Math.toRadians(-80), 	//A5
				Math.toRadians(-75), 	//A6
				Math.toRadians(45));	//A7
		bot.setHomePosition(newHome);
	}


	public Frame gridCalculation(Frame Origin, int rowNumber, int colNumber,
			double rowOffset, double colOffset, double ZOffset) {
		return Origin.copy().setX(Origin.getX() - (colNumber - 1) * colOffset)
				.setY(Origin.copy().getY() + (rowNumber - 1) * rowOffset)
				.setZ(Origin.copy().getZ() + ZOffset);
	}
	
	private void downloadImage() {
		try {
			ftp.downloadFile();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void telnetLogin() {
		if (!telnet.login()) {
			getApplicationControl().halt();
		}
	}
	@Override
    public void dispose()
    {
        try {
        	// Add your "clean up" code here e.g.
            timer.timerStopAndKill(); 
        } catch (NullPointerException e ) {
        	System.err.println("One or more threads were not initialized");
        }
        finally
        {
            super.dispose();
        }
    }
}