package application;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.CognexIIWA_FTPlib;
import modules.CognexIIWA_Telnetlib;
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
public class CollectPictures extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool KSAF_EE;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
	private ObjectFrame referencePos;
    private TimerKCT timer;
    CognexIIWA_Telnetlib telnet;
    CognexIIWA_FTPlib ftp;
    
	@Override
	public void initialize() {
		// initialize your application here
		KSAF_EE = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		currentTCP = KSAF_EE.getFrame("NutRunner_HL70_06");
		nullBase = getApplicationData().getFrame("/nullBase");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
		referencePos = getApplicationData().getFrame("/nullBase/referencePosHL10");
		telnet = new CognexIIWA_Telnetlib("172.31.1.69","admin","");
		ftp = new CognexIIWA_FTPlib("172.31.1.69","admin","");
	}

	@Override
	public void run() {
		
		double rowOffset = 44;
		double columnOffset = 57;
		double currentExposureTime = 5.0;
		ftp.setFtpLocalFileName(" HL70_10" + " Exposure " + currentExposureTime + ".bmp");
		ftp.setFtpLocalDownloadPath("d:/Transfer/CognexPics/");
		ftp.setFtpRemoteFileName("Image.bmp");
		timer = new TimerKCT();
		Thread TimerThread;
		TimerThread = new Thread(timer);
		
		
		//bot home
		setNewHomePosition();
		KSAF_EE.attachTo(bot.getFlange());
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		//currentTCP.move(lin(startPos).setCartVelocity(50));
		telnet.login();
		telnet.sendCognexCommand(ECognexCommand.SF, "F", 13, currentExposureTime);
		for (int row = 1; row <= 5; row++) {
			for (int column = 1; column <= 5; column++) {
				Frame TheoreticalPos = gridCalculation(referencePos.copy(), row,
						column, rowOffset, columnOffset,0);
				getLogger().info(
						"**********  Position: Row:  " + row + " Column: "
								+ column + "**********");
				
				getLogger().info("XYZ: " + TheoreticalPos);
				
				//   Move to process position
				currentTCP.move(lin(TheoreticalPos).setCartVelocity(50).setCartAcceleration(100));
				telnet.sendCognexTrigger(ECognexTrigger.SE8);
				ThreadUtil.milliSleep(500);
				downloadImage();
				//getApplicationControl().halt();
			}
		}	
		telnet.disconnect();
		bot.move(ptpHome().setJointVelocityRel(0.3));
	}
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(-127), Math.toRadians(88),
				Math.toRadians(76), Math.toRadians(-86), Math.toRadians(-8), Math.toRadians(-46), Math.toRadians(-5));
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