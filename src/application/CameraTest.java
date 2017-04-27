package application;


import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import modules.TimerKCT;

import com.kuka.generated.ioAccess.CognexProfinetIOGroup;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;
import javax.swing.JOptionPane;

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
public class CameraTest extends RoboticsAPIApplication {
	@Inject
	private LBR bot;
	private Tool HotDotTest;
	private ObjectFrame nullBase;
	private ObjectFrame currentTCP;
	private ObjectFrame startPos;
    private TimerKCT timer;
    private CognexProfinetIOGroup CognexIO;
    private Controller kuka_Sunrise_Cabinet_1;
    
	@Override
	public void initialize() {
		// initialize your application here
		HotDotTest = getApplicationData().createFromTemplate("KSAFNutRunnerEE");
		//currentTCP = HotDotTest.getFrame("Iron");
		nullBase = getApplicationData().getFrame("/nullBase");
		startPos = getApplicationData().getFrame("/SpiralTest/SpiralTestStart");
		kuka_Sunrise_Cabinet_1 = getController("KUKA_Sunrise_Cabinet_1");
		CognexIO = new CognexProfinetIOGroup(kuka_Sunrise_Cabinet_1);
	}

	@Override
	public void run() {
		// your application execution starts here
		//bot.move(ptpHome());
		// Set system property.
        // Call this BEFORE the toolkit has been initialized, that is,
        // before Toolkit.getDefaultToolkit() has been called.
        System.setProperty("java.awt.headless", "true");

        // This triggers creation of the toolkit.
        // Because java.awt.headless property is set to true, this 
        // will be an instance of headless toolkit.
        Toolkit tk = Toolkit.getDefaultToolkit();
        // Standard beep is available.
        tk.beep();

        // Check whether the application is
        // running in headless mode.
        GraphicsEnvironment ge = 
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("Headless mode: " + ge.isHeadless());

        // No top levels are allowed.
        boolean created = false;
        try
        {
            Frame f = new Frame("Frame");
            created = true;
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
            created = false;
        }
        System.err.println("Frame is created: " + created);

        // No other components except Canvas and Panel are allowed.
        created = false;
        try
        {
            Button b = new Button("Button");
            created = true;
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
            created = false;
        }
        System.err.println("Button is created: " + created);
        
        // Canvases can be created.
        final Canvas c = new Canvas()
        {
            public void paint(Graphics g)
            {
                Rectangle r = getBounds();
                g.drawLine(0, 0, r.width - 1, r.height - 1);
                // Colors work too.
                g.setColor(new Color(255, 127, 0));
                g.drawLine(0, r.height - 1, r.width - 1, 0);
                // And fonts
                g.setFont(new Font("Arial", Font.ITALIC, 12));
                g.drawString("Test", 32, 8);
            }
        };
        // And all the operations work correctly.
        c.setBounds(32, 32, 128, 128);

        // Images are available.
        Image i = null;
        try
        {
            File f = new File("grapefruit.jpg");
            i = ImageIO.read(f);
        }
        catch (Exception z)
        {
            z.printStackTrace(System.err);
        }
        final Image im = i;
        
        
		
		System.setProperty("java.awt.headless", "false");
		System.out.println(GraphicsEnvironment.isHeadless());
		JOptionPane.showMessageDialog(null, "i pozamiatane...", "Hole misiek!", JOptionPane.WARNING_MESSAGE);
		getApplicationControl().halt();
		CognexIO.setAcqCtrlReg0(false);
		CognexIO.setAcqCtrlReg1(false);
		CognexIO.setAcqCtrlReg7(false);
		System.out.println("reset completed: " + CognexIO.getAcqCtrlReg0() + " " + CognexIO.getAcqCtrlReg1() + " " + CognexIO.getAcqCtrlReg7());
		
	}
}