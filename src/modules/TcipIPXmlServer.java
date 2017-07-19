package modules;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
public class TcipIPXmlServer extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
//		*********  XML server test  *******		
				try {
					xmlServerTest();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				getApplicationControl().halt();
//		************************************
		}
	
private void xmlServerTest() throws IOException {
		
	int counter = 0;
	String receivedfileName;
	
	InputStream in = null;
	OutputStream out = null;

	System.out.println("Server starts");
	ServerSocket serverSocket = new ServerSocket(30008); // open socket
	Socket sock = new Socket();
	while (counter < 10) {
		sock = serverSocket.accept(); 	// program waits(and accepts) for new connection here
		in = sock.getInputStream();		// Receive from socket
		//in.read();
		receivedfileName = "d:/Transfer/UserXMLs/testxmldump" + counter + ".xml";
		out = new FileOutputStream(receivedfileName);
		byte[] bytes = new byte[16 * 1024];
		int count;
		System.out.println("Receiving data...");
		while ((count = in.read(bytes)) > 0) {
			//out.write(bytes, 0, count);
			System.out.println(in.read());
		}
		System.out.println("File " + receivedfileName + " dumped ");
		out.close();
		in.close();
		counter++;
	}
	sock.close();
	serverSocket.close();
	System.out.println("Done");

}
}