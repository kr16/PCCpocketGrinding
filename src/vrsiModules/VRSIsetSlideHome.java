package vrsiModules;

import com.kuka.common.ThreadUtil;

import vrsiModules.VRSIcommon.EVRSIhomeSlide;

public class VRSIsetSlideHome implements Runnable{

	private VRSIiiwaCommLib vrsiCommands;
	private boolean bSuccess;
	private StreamDataCommLib commPorthandle; 
	
	public void setSlideHome() {
		commPorthandle.login(3);
		ThreadUtil.milliSleep(100);
	    commPorthandle.write(vrsiCommands.setSlideHomeREQ());
	    if (vrsiCommands.getSlideHomeResponse(commPorthandle.getServerCommandResponseString(), EVRSIhomeSlide.SlideHomeCmdReceived)) {
	    	if (vrsiCommands.getSlideHomeResponse(commPorthandle.getServerCommandResponseString(), EVRSIhomeSlide.SlideAtHome)) {
	    		commPorthandle.write(vrsiCommands.setSlideHomeACK());
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
	}
	
	public void setVRSIcommHandle(VRSIiiwaCommLib dataHandle) {
		this.vrsiCommands = dataHandle;
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
