package modules;

/**
 * 
 * Timer implementation as Runnable thread
 *
 */
public class TimerKCT implements Runnable {
	private double dInitTime;		//init timer - start counting from this value
	private double dCurrentTime;	//current timer value
	private double dUserValue;		//user setTimer value
	private boolean bSwitch;		//Starts/Stops timer
	private boolean bReset;			//Start fresh
	private boolean bFinishThread;	//Finish the while loop - end thread
	private boolean bSetValue;		//User value setter
	private boolean bNegative;		//Flag checker
	private boolean bTimerFlag;		//Timer flag
	
	public TimerKCT(){ 
		dCurrentTime = 0;
		dUserValue = 0;
		bSwitch = false;
		bReset = false;
		bFinishThread = false;
		bSetValue = false;
		bNegative = false;
		bTimerFlag = false;
	}
	
	/**
	 * Starts the timer
	 */
	public void timerStart() {
		bSwitch = true;
	}
	/**
	 * Stops the timer and retain value until reset by user
	 */
	public void timerStop() {
		bSwitch = false;
	}
	
	/**
	 * Stops the timer and exit the thread.
	 * Use it when done with this timer object
	 */
	public void timerStopAndKill() {
		bFinishThread = true;
	}
	
	/**
	 * @param dValue
	 * Sets timer to initial value in milliseconds.
	 * Timer can be started from that value (also negative)
	 */
	public void setTimerValue(double dValue) {
		bSetValue = true;
		dUserValue = dValue;
	}
	
	/**
	 * @param bValue
	 * Allow user to set/reset the flag
	 */
	public void setTimerFlag(boolean bValue) {
		bTimerFlag = bValue;
	}
	
	/**
	 * @return Returns current timer value
	 */
	public double getTimerValue() {
		return dCurrentTime;
	}
	
	/**
	 * @return Returns current flag value for this timer. 
	 * Flag goes TRUE when timer passes through 0.
	 */
	public boolean getTimerFlag() {
		return bTimerFlag;
	}
	
	
	/**
	 * @return Returns true if timer is currently running
	 */
	public boolean isTimerRunning() {
		return (bSwitch && bReset);
	}
	
	@Override
	public void run() {	
		while (!bFinishThread) {
			// SetValue
			if (bSetValue) {
				dCurrentTime = 0;
				dInitTime = System.currentTimeMillis() - dUserValue;
			}
			// Timer Stop
			if (!bSwitch && bReset) {
				bReset = !bReset;
			}
			// Timer running
			if (bSwitch && bReset ) {
				if (bSetValue) bSetValue = !bSetValue;
				dCurrentTime = System.currentTimeMillis() - dInitTime;
				//Flag logic
				if (dCurrentTime < 0) bNegative = true;
				if (bNegative && dCurrentTime >= 0) {
					bNegative = !bNegative;
					bTimerFlag = true;
				}
				continue;
			}
			// Timer Start
			if (bSwitch && !bReset) {
				bReset = !bReset;
				dInitTime = System.currentTimeMillis() - dCurrentTime;
			}
		}
	}
}		


