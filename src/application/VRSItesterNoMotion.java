package application;


import javax.inject.Inject;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

import vrsiModules.StreamDataCommLib;
import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;
import vrsiModules.VRSIiiwaCommLib;

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
public class VRSItesterNoMotion extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	private StreamDataCommLib iiwaDataStream;
	private VRSIiiwaCommLib vrsiComm;
	private Thread dataServerThread;

	@Override
	public void initialize() {
		// initialize your application here
		iiwaDataStream = new StreamDataCommLib("172.31.1.230", 30001);
		vrsiComm = new VRSIiiwaCommLib(true);
	}

	@Override
	public void run() {
		// your application execution starts here
		//lBR_iiwa_14_R820_1.move(ptpHome());
		
		long delay = 5000;
		
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.login();
		
		ThreadUtil.milliSleep(5000);
		
		iiwaDataStream.write(vrsiComm.setSlideHomeREQ());
		vrsiComm.getSlideHomeResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIhomeSlide.SlideHomeCmdReceived);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.setSlideHomeACK());
		vrsiComm.getSlideHomeResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIhomeSlide.SlideAtHome);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.setSlideHomeREQ());
		vrsiComm.getSlideHomeResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIhomeSlide.SlideHomeCmdReceived);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.setSlideHomeACK());
		vrsiComm.getSlideHomeResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIhomeSlide.SlideAtHome);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.scanFastenerREQ("KDD001", 2.3, 0));
		vrsiComm.getScanEmptyFastenerResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIscanFastener.ScanEmptyFastenerCmd);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.scanFastenerREQ("KDD002", 2.3, 0));
		vrsiComm.getScanEmptyFastenerResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIscanFastener.ScanEmptyFastenerCmd);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write(vrsiComm.scanFastenerREQ("KDD003", 2.3, 0));
		vrsiComm.getScanEmptyFastenerResponse(iiwaDataStream.getServerCommandResponseString(), EVRSIscanFastener.ScanFillFastenerCmd);
		ThreadUtil.milliSleep(delay);
		
		iiwaDataStream.write("EOT");
		iiwaDataStream.disconnect();
		
	}
}