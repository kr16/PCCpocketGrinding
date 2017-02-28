package modules;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.positionHold;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.conditionModel.ForceComponentCondition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;

/**
 * @author mpiotrowski
 * The force characteristic is created by overlaying 2 sinusoidal force oscillations.
The oscillations are shifted in phase by pi/2 (90°). The amplitudes of the oscillations
rise constantly up to the defined value and then return to zero. This results
in a spiral pattern which extends up to the defined amplitude value and
then contracts again.
In the resulting robot motion, the TCP moves along this spiral. The Cartesian
extent of the spiral depends on the values defined for stiffness and amplitude
as well as any obstacles present.
The plane in which the spiral-shaped oscillation is to be overlaid is transferred
as a value of type CartPlane. The values defined for the parameters stiffness,
frequency and amplitude are identical for both degrees of freedom of the
plane.
In addition, a value is transferred for the total time of the force oscillation. The
time is divided evenly between the upward and downward motion of the oscillation:
Rise time = Total time / 2
Hold time = 0
Fall time = Total time / 2
 */
public class SpiralMotion extends RoboticsAPIApplication{
	@Inject
	private long riseTime;
	private long holdTime;
	private long fallTime;
	private double travelDistance;
	private double travelVelocity;
	private double biasForce;
	private CartDOF biasForcedirection;
	private double maxSpiralForce = 0.0;
	private LBR bot;
	
	
	public SpiralMotion(CartPlane cartPlane, 
						double frequency, 
						double amplitude, 
						double stifness,
						long totalTimeSecs, 
						ObjectFrame currentTCP,
						long holdTime)
						
	{
		//initialize
		this.initialize();
		this.setHoldTime(holdTime);
		totalTimeSecs = totalTimeSecs + getHoldTime();
		CartesianSineImpedanceControlMode spiralMode;
		spiralMode = CartesianSineImpedanceControlMode.createSpiralPattern(cartPlane,frequency, amplitude, stifness, totalTimeSecs);
		spiralMode.parametrize(CartDOF.X).setBias(20).setStiffness(4500);
		spiralMode.setHoldTime(this.getHoldTime());
		
		
		//stop spiral force
		ForceComponentCondition TCPforce;
		if (maxSpiralForce == 0) {
			maxSpiralForce = 30;
			System.err.println("Max spiral force not set! uding default value of " + maxSpiralForce);
		}
		TCPforce = new ForceComponentCondition(currentTCP,CoordinateAxis.X, -maxSpiralForce, maxSpiralForce);
		
		IMotionContainer positionHoldContainer;
		positionHoldContainer = currentTCP.moveAsync(positionHold(spiralMode, -1, TimeUnit.SECONDS));
		System.out.println("Spiral running");
		boolean bConditionResult = false;
		
		bConditionResult = getObserverManager().waitFor(TCPforce, totalTimeSecs,TimeUnit.SECONDS);
		if (bConditionResult) { 
			System.out.println("Max Force exceeded");
		} else {
			System.out.println("Spiral finished");
		}
		positionHoldContainer.cancel();
	}

	public double getTravelDistance() {
		return travelDistance;
	}

	public void setTravelDistance(double travelDistance) {
		this.travelDistance = travelDistance;
	}

	public double getTravelVelocity() {
		return travelVelocity;
	}

	public void setTravelVelocity(double travelVelocity) {
		this.travelVelocity = travelVelocity;
	}

	public double getBiasForce() {
		return biasForce;
	}

	public void setBiasForce(double biasForce) {
		this.biasForce = biasForce;
	}

	public CartDOF getBiasForcedirection() {
		return biasForcedirection;
	}

	public void setBiasForcedirection(CartDOF biasForcedirection) {
		this.biasForcedirection = biasForcedirection;
	}

	public long getRiseTime() {
		return riseTime;
	}

	public void setRiseTime(long riseTime) {
		this.riseTime = riseTime;
	}

	public long getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(long holdTime) {
		this.holdTime = holdTime;
	}

	public long getFallTime() {
		return fallTime;
	}

	public void setFallTime(long fallTime) {
		this.fallTime = fallTime;
	}

	public double getMaxSpiralForce() {
		return maxSpiralForce;
	}

	public void setMaxSpiralForce(double maxSpiralForce) {
		this.maxSpiralForce = maxSpiralForce;
	}
	
	@Override
	public void initialize() {
		// initialize your application here
	}
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
