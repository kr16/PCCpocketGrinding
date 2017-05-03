package application;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.CognexIIWA_FTPlib;
import modules.CognexIIWA_Telnetlib;
import modules.CouponXMLparser;
import modules.LucanaIIWA_CommLib;
import modules.XMLParserCoupon;
import modules.XMLParserLucanaData;
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
public class LucanaCameraTest extends RoboticsAPIApplication {
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
    private LucanaIIWA_CommLib lucanaCam;
    private CognexIIWA_FTPlib ftp;
    private XMLParserCoupon coupon1;
    private XMLParserLucanaData lucanaData;
    
	@Override
	public void initialize() {
		// initialize your application here
		KSAF_EE = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		currentTCP = KSAF_EE.getFrame("NutRunner_HL70_06");
		nullBase = getApplicationData().getFrame("/nullBase");
		startPos = getApplicationData().getFrame("/CouponBase/couponBaseApp");
		referencePos = getApplicationData().getFrame("/CouponBase/referencePosHL08");
		lucanaCam = new LucanaIIWA_CommLib("172.31.1.148", 9000);
		
		globalsFilePath = "d:/Transfer/UserXMLs/";
		globalsFileNamePLC = "GlobalVarsCognexPLC.xml";
		globalsFileNameKRC = "GlobalVarsCognexKRC.xml";
		globalVarFromPLC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNamePLC);
		globalVarFromKRC = new XmlParserGlobalVarsRD(globalsFilePath, globalsFileNameKRC);
		coupon1 = new XMLParserCoupon(1, "d:/Transfer/UserXMLs/CognexIIWACouponHL08.xml");
		lucanaData = new XMLParserLucanaData();
	}

	@Override
	public void run() {
		
		lucanaCam.login();
		lucanaCam.write("hAuto"+"\n");
		lucanaCam.getLucanaCommandResponse();
		lucanaCam.disconnect();
		lucanaCam.displayLucanaDataAscii();
		//lucanaCam.displayLucanaDataRaw();
		
		
		//bot new home
		setNewHomePosition();
		KSAF_EE.attachTo(bot.getFlange());
		
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
	
	@Override
    public void dispose()
    {
        try {
        	 
        } catch (NullPointerException e ) {
        	
        }
        finally
        {
            super.dispose();
        }
    }
}