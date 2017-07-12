package vrsiModules;

import com.kuka.common.ThreadUtil;

import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;

public class VRSIscanEmptyFastener implements Runnable{

	private VRSIiiwaCommLib vrsiCommands = new VRSIiiwaCommLib(true);
	private boolean bSuccess;
	private StreamDataCommLib commPorthandle; 
	private String holeID;
	private double pinDia;
	private int pinType;
	
	public void scanFastener() {
		commPorthandle.login();
		ThreadUtil.milliSleep(100);
	    commPorthandle.write(vrsiCommands.scanFastenerREQ(holeID, pinDia, pinType));
	    if (vrsiCommands.getScanEmptyFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanEmptyFastenerCmd)) {
	    	if (vrsiCommands.getScanEmptyFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanEmptyFastenerComplete)) {
	    		commPorthandle.write(vrsiCommands.scanFastenerACK());
	    		setbSuccess(true);
	    	}
	    }
	}
	
	public void setScanFastener(String holeID, double pinDia, int pinType) {
		this.holeID = holeID;
		this.pinDia = pinDia;
		this.pinType = pinType;
	}
	
	public void init() {
		setbSuccess(false);
	}
	
	public void run() {
		init();
		scanFastener();
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

	

	
	
}
