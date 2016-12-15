package tests;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.Common.searchDir;
import modules.TimerKCT;
import modules.TouchForceRecord;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;

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
public class SpiralMotionTest extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool HotDotTest;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
    private TimerKCT timer;
	@Override
	public void initialize() {
		// initialize your application here
		HotDotTest = getApplicationData().createFromTemplate("HotDotEE");
		nullBase = getApplicationData().getFrame("/nullBase");
		currentTCP = HotDotTest.getFrame("IronVer01");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
	}

	@Override
	public void run() {
		boolean bConditionResult;
		long totalTimeSec = 120;	//	s
		double frequency = 0.5;		//	Hz
		
		IMotionContainer positionHoldContainer;
		ForceComponentCondition TCPforce;
		CartesianSineImpedanceControlMode spiralMode;
		
		timer = new TimerKCT();
		Thread TimerThread;
		TimerThread = new Thread(timer);
		TimerThread.start();
		timer.setTimerValue(0);
		
		
		//bot home
		setNewHomePosition();
		HotDotTest.attachTo(bot.getFlange());
		System.out.println("Moving to Home/Start position");
		bot.move(ptpHome().setJointVelocityRel(0.3));
		
		currentTCP.move(lin(startPos).setCartVelocity(50));
		TouchForceRecord hitTable = new TouchForceRecord();
		hitTable.recordPosition(searchDir.PosX, 5, 30, 10, 0, currentTCP, nullBase, bot);
		
		spiralMode = CartesianSineImpedanceControlMode.createSpiralPattern(CartPlane.YZ,frequency, 30, 4500, totalTimeSec);
		spiralMode.parametrize(CartDOF.X).setBias(20).setStiffness(5000);
		spiralMode.setRiseTime(0);
		
		
		//currentTCP.move(linRel(100, 0, 0, nullBase).setCartVelocity(1).setMode(spiralMode));
		positionHoldContainer = currentTCP.moveAsync(positionHold(spiralMode, -1, TimeUnit.SECONDS));
		bConditionResult = false;
		TCPforce = new ForceComponentCondition(currentTCP,CoordinateAxis.X, -30, 30);
		
		bConditionResult = getObserverManager().waitFor(TCPforce, totalTimeSec,TimeUnit.SECONDS);
		if (bConditionResult) { 
			System.out.println("Out of range");
		} else {
			System.out.println("good, goood");
		}
		
		positionHoldContainer.cancel();
		timer.timerStopAndKill();
		currentTCP.move(lin(startPos).setCartVelocity(50));
	}
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(-90), Math.toRadians(56),
				0.0, Math.toRadians(-100), 0.0, Math.toRadians(-43), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
	
	@Override
    public void dispose()
    {
        try {
        	// Add your "clean up" code here e.g.
            timer.timerStopAndKill(); 
        } catch (NullPointerException e ) {
        	System.err.println("One or more threads were not initialized");
        }
        finally
        {
            super.dispose();
        }
    }
}