package application;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
public class YAjavaDataServerRobotApp extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	// declare a server socket and a client socket for the server

	ServerSocket echoServer = null;
	Socket clientSocket = null;
	int port;


	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		/*
		Certain ports are enabled on the robot controller for communication with external
		devices via UDP or TCP/IP.
		The following port numbers (client or server socket) can be used in a robot application:
		30000 to 30010
		*/
		
		int port = 30000;
		YAjavaDataServerRobotApp server = new YAjavaDataServerRobotApp(port);
		server.startServer();
		
	}
	public YAjavaDataServerRobotApp( int port ) {
		this.port = port;
	}

	public void stopServer() {
		System.out.println( "Server cleaning up." );
		System.exit(0);
	}
	public void startServer() {
		// Try to open a server socket on the given port
		// Note that we can't choose a port less than 1024 if we are not
		// privileged users (root)

		try {
			echoServer = new ServerSocket(port);
		}
		catch (IOException e) {
			System.out.println(e);
		}   

		System.out.println( "Waiting for connections. Only one connection is allowed." );

		// Create a socket object from the ServerSocket to listen and accept connections.
		// Use Server1Connection to process the connection.

		while ( true ) {
			try {
				clientSocket = echoServer.accept();
				Server1Connection oneconnection = new Server1Connection(clientSocket, this);
				oneconnection.run();
			}   
			catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

class Server1Connection {
	BufferedReader is;
	PrintStream os;
	Socket clientSocket;
	YAjavaDataServerRobotApp server;

	public Server1Connection(Socket clientSocket, YAjavaDataServerRobotApp server) {
		this.clientSocket = clientSocket;
		this.server = server;
		System.out.println( "Connection established with: " + clientSocket );
		try {
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	public void run() {
		String line;
		try {
			boolean serverStop = false;

			while (true) {
				line = is.readLine();
				System.out.println( "Received " + line );
				int n = Integer.parseInt(line);
				if ( n == -1 ) {
					serverStop = true;
					break;
				}
				if ( n == 0 ) break;
				os.println("" + n*n ); 
			}

			System.out.println( "Connection closed." );
			is.close();
			os.close();
			clientSocket.close();

			if ( serverStop ) server.stopServer();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}