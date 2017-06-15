package application;


import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

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
public class UDPmotionRoutine extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool KSAF_EE;
	ObjectFrame currentTCP;

	@Override
	public void initialize() {
		KSAF_EE = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		currentTCP = KSAF_EE.getFrame("NutRunner_HL70_06");
	}

	@Override
	public void run() {
		KSAF_EE.attachTo(bot.getFlange());
		
		while (true) {
		currentTCP.move(ptp(getApplicationData().getFrame("/UDPmotionRoutine/posOutside")).setJointVelocityRel(0.1));
		
		currentTCP.move(ptp(getApplicationData().getFrame("/UDPmotionRoutine/posInside")).setJointVelocityRel(0.1));
		}
	}
}