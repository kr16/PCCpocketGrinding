package application;


import javax.inject.Inject;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

import vrsiModules.StreamDataCommLib;
import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;
import vrsiModules.VRSIfillFastener;
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
	private VRSIiiwaCommLib vrsiComm;
	private Thread dataServerThread;

	@Override
	public void initialize() {
		// initialize your application here
		vrsiComm = new VRSIiiwaCommLib(true);
	}

	@Override
	public void run() {
		// your application execution starts here
		//lBR_iiwa_14_R820_1.move(ptpHome());
		
		long delay = 5000;
		
		//vrsiComm.setSlideHome(5000);
		//vrsiComm.scanEmptyFastener("MIC001", 0.666, 1, 10000);
		
		if (vrsiComm.scanFillFastener("KDD001", 0.666, 1, -1)) {
			System.out.println("VRSI data: " + vrsiComm.getFillFastenerData());
		}
		
		getApplicationControl().halt();
		
	}
	@Override
    public void dispose()
    {
        try {
        	// Add your "clean up" code here e.g.
        	// ssock.close();
            vrsiComm.getCommPort().disconnect();
        } catch (Exception e ) {
        	System.out.println(e);
        }
        finally
        {
            super.dispose();
        }
    }
}