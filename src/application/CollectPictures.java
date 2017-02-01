package application;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import modules.Common.EHotDotCouponStates;
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
import com.kuka.roboticsAPI.geometricModel.Frame;
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
public class CollectPictures extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool HotDotTest;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
	private ObjectFrame referencePos;
    private TimerKCT timer;
	@Override
	public void initialize() {
		// initialize your application here
		HotDotTest = getApplicationData().createFromTemplate("HotDotEEnoVRSI");
		nullBase = getApplicationData().getFrame("/nullBase");
		currentTCP = HotDotTest.getFrame("Iron");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
		referencePos = getApplicationData().getFrame("/referencePos");
	}

	@Override
	public void run() {
		
		double rowOffset = 44;
		double columnOffset = 52;
		
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
		
		for (int row = 1; row <= 5; row++) {
			for (int column = 1; column <= 9; column++) {
				Frame TheoreticalPos = gridCalculation(referencePos.copy(), row,
						column, rowOffset, columnOffset,0);
				getLogger().info(
						"**********  Position: Row:  " + row + " Column: "
								+ column + "**********");
				
				getLogger().info("XYZ: " + TheoreticalPos);
				
				//   Move to process position
				currentTCP.move(lin(TheoreticalPos).setCartVelocity(50).setCartAcceleration(100));
			}
		}	
	}
	
	private void setNewHomePosition() {
		// Currently needed every run for this program
		// Otherwise robot goes to candle home
		JointPosition newHome = new JointPosition(Math.toRadians(-90), Math.toRadians(56),
				0.0, Math.toRadians(-100), 0.0, Math.toRadians(-43), Math.toRadians(0));
		bot.setHomePosition(newHome);
	}
	
	public Frame gridCalculation(Frame Origin, int rowNumber, int colNumber,
			double rowOffset, double colOffset, double ZOffset) {
		return Origin.copy().setX(Origin.getX() - (rowNumber - 1) * rowNumber)
				.setY(Origin.copy().getY() + (colNumber - 1) * colOffset)
				.setZ(Origin.copy().getZ() + ZOffset);
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