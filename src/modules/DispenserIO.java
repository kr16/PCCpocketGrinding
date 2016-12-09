package modules;

import com.kuka.common.ThreadUtil;
import com.kuka.generated.ioAccess.SMC600_SPN1IOGroup;
import com.kuka.roboticsAPI.controllerModel.Controller;

public class DispenserIO implements Runnable {
	private SMC600_SPN1IOGroup dispenserIO;
	private boolean getHotDotdispense, killThread;
	
	public DispenserIO(Controller kuka_Sunrise_Cabinet_1) {
		dispenserIO = new SMC600_SPN1IOGroup(kuka_Sunrise_Cabinet_1);
		getHotDotdispense = false;
		killThread = false;
	}
	
	private void requestHotDot() {
		getHotDotdispense = false;
		if (!dispenserIO.getDI01_HotDotPresent_X101()) {
			if(!dispenserIO.getDI04_DispancerAtHome()) {
				this.cylinderReverse();
				while (!dispenserIO.getDI04_DispancerAtHome()) {
					//wait for piston to get home
				}
			}
			this.cylinderForward();
			System.out.println("Hot Dot requested");
			while (!(dispenserIO.getDI03_DispencerAtPlace() && dispenserIO.getDI01_HotDotPresent_X101())) {
				//wait for hot dot present
			}
			ThreadUtil.milliSleep(500);
			this.cylinderReverse();
		} else {
			System.out.println("Hot Dot already present");
		}
		
	}
	/**
	 * dispense new hot dot only if not yet present
	 */
	public void getHotDot() {
		getHotDotdispense = true;
	}
	public boolean getHotDotPresent() {
		return dispenserIO.getDI01_HotDotPresent_X101();
	}
	
	public boolean getDispenserLowOnHotDots() {
		return dispenserIO.getDI02_HotDotDispencerLow_X102();
	}
	
	public void cylinderForward() {
		dispenserIO.setDO11_1_PneumaticCylinder(true);
	}
	
	public void cylinderReverse() {
		dispenserIO.setDO11_1_PneumaticCylinder(false);
	}
	
	public void killThread() {
		killThread = true;
	}
	
	@Override
	public void run() {
		while (!killThread) {
			if (getHotDotdispense) {
				getHotDotdispense = false; 
				System.out.println("Hot Dot request received");
				requestHotDot(); 
			}
		}
	}
}
