package vrsiModules;

import com.kuka.common.ThreadUtil;

import vrsiModules.VRSIcommon.EVRSIscanFastener;

public class VRSIscanEmptyFastener implements Runnable{

	private VRSIiiwaCommLib vrsiCommands;
	private boolean bSuccess;
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
