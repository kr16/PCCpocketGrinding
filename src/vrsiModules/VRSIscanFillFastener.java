package vrsiModules;

import com.kuka.common.ThreadUtil;

import sun.awt.windows.ThemeReader;
import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;

public class VRSIscanFillFastener implements Runnable{

	private VRSIiiwaCommLib vrsiCommands;
	private boolean bSuccess;
	private boolean bRunnableDone;
	private StreamDataCommLib commPorthandle; 
	private String holeID;
	private double pinDia;
	private int pinType;
	
	
	public void scanFastener() {
		commPorthandle.login();
		ThreadUtil.milliSleep(100);
	    commPorthandle.write(vrsiCommands.scanFillREQ(holeID, pinDia, pinType));
	    if (vrsiCommands.getScanFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanFillFastenerCmd)) {
	    	if (vrsiCommands.getScanFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanFillFastenerComplete)) {
	    		commPorthandle.write(vrsiCommands.scanFillACK());
	    		setbSuccess(true);
	    	}
	    }
	}
	
	public void setScanFastener(String holeID, double pinDia, int pinType, VRSIiiwaCommLib dataHandle) {
		this.holeID = holeID;
		this.pinDia = pinDia;
		this.pinType = pinType;
		this.vrsiCommands = dataHandle;
	}
	
	public void init() {
		setbSuccess(false);
		setbRunnableDone(false);
	}
	
	public void run() {
		init();
		scanFastener();
		while (!isbRunnableDone());
		Thread.currentThread().interrupt();
		return;
	}

	public boolean isbSuccess() {
		return bSuccess;
	}

	public void setbSuccess(boolean bSuccess) {
		this.bSuccess = bSuccess;
	}

	public StreamDataCommLib getCommPorthandle() {
		return commPorthandle;
	}

	public void setCommPorthandle(StreamDataCommLib commPorthandle) {
		this.commPorthandle = commPorthandle;
	}

	public boolean isbRunnableDone() {
		return bRunnableDone;
	}

	public void setbRunnableDone(boolean bRunnableDone) {
		this.bRunnableDone = bRunnableDone;
	}

	

	
	
}
