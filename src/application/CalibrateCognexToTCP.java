package application;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.CognexIIWA_FTPlib;
import modules.CognexIIWA_Telnetlib;
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
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;

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
public class CalibrateCognexToTCP extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool KSAF_EE;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos, centerPos;
	private ObjectFrame referencePos;
    private TimerKCT timer;
    private String globalsFilePath;
	private String globalsFileNamePLC, globalsFileNameKRC;
    private XmlParserGlobalVarsRD globalVarFromPLC, globalVarFromKRC;
    private CognexIIWA_Telnetlib telnet;
    private CognexIIWA_FTPlib ftp;
    
	@Override
	public void initialize() {
		// initialize your application here
		KSAF_EE = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		currentTCP = KSAF_EE.getFrame("NutRunner_HL70_06");
		nullBase = getApplicationData().getFrame("/nullBase");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
		referencePos = getApplicationData().getFrame("/nullBase/referencePosHL12");
		centerPos = getApplicationData().getFrame("/nullBase/CognexCalibration");
		telnet = new CognexIIWA_Telnetlib("172.31.1.69","admin","");
		ftp = new CognexIIWA_FTPlib("172.31.1.69","admin","");
		globalsFilePath = "d:/Transfer/UserXMLs/";
		globalsFileNamePLC = "GlobalVarsCognexPLC.xml";
		globalsFileNameKRC = "GlobalVarsCognexKRC.xml";
		globalVarFromPLC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNamePLC);
		globalVarFromKRC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNameKRC);
	}

	@Override
	public void run() {
		
		PrintWriter logFile = null;
		// Log to file section
		SimpleDateFormat prefixDateFormat;
		prefixDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		String filePathRoot="d:/Transfer/UserXMLs/";
		try {
			logFile = new PrintWriter(filePathRoot + prefixDateFormat.format(Calendar.getInstance().getTime()) + "_" + "CognexCalibration.csv", "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//////////////////////
		
		double rowOffset, columnOffset;
		int row, column;		
		double xMove, yMove;
		double currentExposureTime; 
		currentExposureTime = globalVarFromPLC.getVarDouble("exposureTime");
		ftp.setFtpLocalFileName(" HL70_12" + " Exposure " + currentExposureTime + ".jpg");
		ftp.setFtpLocalDownloadPath("d:/Transfer/CognexPics/");
		ftp.setFtpRemoteFileName("Image.jpg");
		timer = new TimerKCT();
		Thread TimerThread;
		TimerThread = new Thread(timer);
		
		
		//bot home
		setNewHomePosition();
		KSAF_EE.attachTo(bot.getFlange());
		

			System.out.println("Moving to Home/Start position");
			bot.move(ptpHome().setJointVelocityRel(0.3));
			System.out.println("Moving to Center on pin position, HALT, press start to continue");
			currentTCP.move(lin(centerPos).setCartVelocity(30));
			logFile.println("Fastener center position: " + bot.getCurrentCartesianPosition(currentTCP, nullBase));
			logFile.println("BlobX\t,BlobY\t,BestCircX\t,BestCircY\t,LargestCircX\t,LargestCircY\t, Position");
			getApplicationControl().halt();
			System.out.println("Moving to Start calibration grid position");
			currentTCP.move(linRel(13, 14, 0));
			
			double BlobX, BlobY;
			double BestCircX, BestCircY;
			double LargestCircX, LargestCircY;
			xMove = yMove = 0;
			row = column = 0;
			rowOffset = 16; // motions in X direction of a tool which is Y for World
			columnOffset = 23; // motion in Y direction of a tool which is X for World
			while (row <= 16) {
				while (column <= 23) {
					getLogger().info(
							"**********  Position: Row:  " + row + " Column: "
									+ column + "**********");

					//   Move to process position
					
					currentTCP.move(linRel(xMove, yMove, 0).setCartVelocity(30).setCartAcceleration(50));
					telnetLogin();
					//currentExposureTime = globalVarFromPLC.getVarDouble("exposureTime");
					//telnet.sendCognexCommand(ECognexCommand.SF, "A", 21, currentExposureTime);
					telnet.sendCognexTrigger(ECognexTrigger.SE8);
					ThreadUtil.milliSleep(500);
					//downloadImage();
					telnet.sendCognexCommand(ECognexCommand.GV, "N", 7);
					telnet.readCognexResponse();
					BlobX = telnet.getCognexSpreadSheetValueDouble();
					telnet.sendCognexCommand(ECognexCommand.GV, "O", 7);
					telnet.readCognexResponse();
					BlobY = telnet.getCognexSpreadSheetValueDouble();
					telnet.sendCognexCommand(ECognexCommand.GV, "N", 8);
					telnet.readCognexResponse();
					BestCircX = telnet.getCognexSpreadSheetValueDouble();
					telnet.sendCognexCommand(ECognexCommand.GV, "O", 8);
					telnet.readCognexResponse();
					BestCircY = telnet.getCognexSpreadSheetValueDouble();
					telnet.sendCognexCommand(ECognexCommand.GV, "N", 9);
					telnet.readCognexResponse();
					LargestCircX = telnet.getCognexSpreadSheetValueDouble();
					telnet.sendCognexCommand(ECognexCommand.GV, "O", 9);
					telnet.readCognexResponse();
					LargestCircY = telnet.getCognexSpreadSheetValueDouble();
					telnet.disconnect();
					getLogger().info("XYZ: " + bot.getCurrentCartesianPosition(currentTCP, nullBase));
					System.out.println("BlobX: " + BlobX + " BlobY: " + BlobY);
					System.out.println("BestCircX: " + BestCircX + " BestCircY: " + BestCircY);
					System.out.println("LargestCircX: " + LargestCircX + " LargestCircY: " + LargestCircY);
					System.out.println("***********************************************");
					
					logFile.println(BlobX + "," + BlobY + "," + BestCircX + "," + BestCircY + ","
									+ LargestCircX + "," + LargestCircY + "," 
									+ "XYZ: " + bot.getCurrentCartesianPosition(currentTCP, nullBase));
					logFile.flush();
					
					//getApplicationControl().halt();
					
					xMove = 0;
					yMove = -1;
					column++;
				}
				xMove = 1;
				yMove = columnOffset;
				column = 0;
				row++;
			}	
			
			bot.move(ptpHome().setJointVelocityRel(0.3));
	}
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(-84), Math.toRadians(59),
				Math.toRadians(-2), Math.toRadians(-109), Math.toRadians(5), Math.toRadians(-78), Math.toRadians(67));
		bot.setHomePosition(newHome);
	}
	
	public Frame gridCalculation(Frame Origin, int rowNumber, int colNumber,
			double rowOffset, double colOffset, double ZOffset) {
		return Origin.copy().setX(Origin.getX() - (rowNumber - 1) * rowOffset)
				.setY(Origin.copy().getY() + (colNumber - 1) * colOffset)
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