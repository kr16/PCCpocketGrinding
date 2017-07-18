package vrsiModules;

import com.kuka.common.ThreadUtil;

import sun.awt.windows.ThemeReader;
import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;

public class VRSIscanFillFastener implements Runnable{

	private VRSIiiwaCommLib vrsiCommands;		//use this to call helper methods from main library
	private boolean bSuccess;					//monitor outcome result of this Thread
	private StreamDataCommLib commPorthandle; 	//handle to use for communication (TCP/IP socket to write to)
	private String holeID;						//current holeID
	private double pinDia;						//Expected pin diameter 
	private double pinDiaAct;					//VRSI measured diameter
	private int pinType;						//pin type to scan
	
	
	public void scanFastener() {				//this is executed under by run method in this Thread
		commPorthandle.login();					//login to VRSI
		ThreadUtil.milliSleep(100);				//needed delay before scan command (at least for my test) 
	    
		//Send request to scan to VRSI. 
		//vrsiCommands.scanFillREQ generates actual String to be send based on user data
		//commPorthandle .write sends generated String to the socket
		commPorthandle.write(vrsiCommands.scanFillREQ(holeID, pinDia, pinType));
		
		//we expect correct responds from VRSI before we send ACK signal
		//two responses are expected (ACK request , and process complete)
		//vrsiCommands.getScanFastenerResponse analyze the String from VRSI against:
		//VRSI ACK that command was received response
	    if (vrsiCommands.getScanFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanFillFastenerCmd)) {
	    	//VRSI process complete response
	    	if (vrsiCommands.getScanFastenerResponse(commPorthandle.getServerCommandResponseString(), EVRSIscanFastener.ScanFillFastenerComplete)) {
	    		//Once we got both responses we send ACK signal and set success bit
	    		commPorthandle.write(vrsiCommands.scanFillACK());
	    		setbSuccess(true);
	    	}
	    }
	}
	
	//called by higher method to set all needed params BEFORE we start this Thread
	public void setScanFastener(String holeID, double pinDia, int pinType, VRSIiiwaCommLib dataHandle) {
		this.holeID = holeID;
		this.pinDia = pinDia;
		this.pinType = pinType;
		this.vrsiCommands = dataHandle;
	}
	
	public void init() {
		setbSuccess(false);
	}
	
	//Main method called by Thread.start
	public void run() {
		init();
		scanFastener();
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

	

	

	
	
}
