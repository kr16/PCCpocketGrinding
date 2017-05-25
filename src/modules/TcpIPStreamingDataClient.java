package modules;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

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
public class TcpIPStreamingDataClient extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	private LucanaIIWA_CommLib lucanaCam;

	@Override
	public void initialize() {
		// initialize your application here
		lucanaCam = new LucanaIIWA_CommLib("172.31.1.230", 30008);
	}

	@Override
	public void run() {
		
		//Lucana lib tryout //////////
		
		lucanaCam.login();
		lucanaCam.write("MSG, HOLA i pozamiatane misiek 2 razy, ETX");
		lucanaCam.disconnect();
		getApplicationControl().halt();
		lucanaCam.login();
		lucanaCam.write("POS, 234.12, -23.4, 435.23, 0.2, 0.1, -179.2, ETX");
		lucanaCam.disconnect();
		getApplicationControl().halt();
		lucanaCam.login();
		lucanaCam.write("EOT");
		lucanaCam.disconnect();
		getApplicationControl().halt();
		
		Socket socket = null;
		try {
			socket = new Socket("172.31.1.230", 30008);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Create and connect the socket
		DataOutputStream dOut = null;
		try {
			dOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Send first message
		try {
			dOut.writeByte(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dOut.writeUTF("This is the first type of message.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Send off the data

//		// Send the second message
//		dOut.writeByte(2);
//		dOut.writeUTF("This is the second type of message.");
//		dOut.flush(); // Send off the data
//
//		// Send the third message
//		dOut.writeByte(3);
//		dOut.writeUTF("This is the third type of message (Part 1).");
//		dOut.writeUTF("This is the third type of message (Part 2).");
//		dOut.flush(); // Send off the data
//
//		// Send the exit message
//		dOut.writeByte(-1);
//		dOut.flush();

		try {
			dOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}