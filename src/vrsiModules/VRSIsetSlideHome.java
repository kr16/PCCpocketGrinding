package vrsiModules;

import vrsiModules.VRSIcommon.EVRSIhomeSlide;

public class VRSIsetSlideHome implements Runnable{

	private VRSIiiwaCommLib vrsiCommands = new VRSIiiwaCommLib(true);
	private boolean bSuccess;
	private StreamDataCommLib commPorthandle; 
	
	public void setSlideHome() {
		commPorthandle.login();
	    vrsiCommands.setSlideHomeREQ();
	    if (vrsiCommands.getSlideHomeResponse(commPorthandle.getServerCommandResponseString(), EVRSIhomeSlide.SlideHomeCmdReceived)) {
	    	if (vrsiCommands.getSlideHomeResponse(commPorthandle.getServerCommandResponseString(), EVRSIhomeSlide.SlideAtHome)) {
	    		commPorthandle.disconnect();
	    		setbSuccess(true);
	    	}
	    }
	}
	
	public void init() {
		setbSuccess(false);
	}
	
	public void run() {
		init();
		setSlideHome();
		commPorthandle.disconnect();
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
