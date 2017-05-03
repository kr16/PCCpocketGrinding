package modules;

import org.apache.commons.net.telnet.TelnetClient;

import modules.Common.ECognexCommand;
import modules.Common.ECognexTrigger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.activation.MimeTypeParameterList;
import javax.swing.plaf.SliderUI;

public class LucanaIIWA_CommLib {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private byte[] lucanaBufferData;
	private int lucanaBufferDataSizeMax; 
	private int lucanaBufferDataSize;
	
	
	//Object properties
	private String username;
	private String password;
	private String serverAddress;
	private int serverPort;

	/**
	 * Constructor for Lucana camera on given server (String) and port (Integer)
	 * @param serverAddress
	 * @param serverPort
	 * e.g:
	 *  LucanaIIWA_CommLib myCamera = LucanaIIWA_CommLib("172.31.1.148", 9000);
	 */
	public LucanaIIWA_CommLib(String serverAddress, int serverPort) {
		this.initialize();
		this.setServerAddress(serverAddress);
		this.setServerPort(serverPort);		
	}
	
	public LucanaIIWA_CommLib(String serverAddress, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(23);		//default , use other constructor to pass your port number
	}

	public LucanaIIWA_CommLib(String serverAddress, int serverPort, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(serverPort);

	}
	private void initialize() {
		lucanaBufferDataSizeMax = 16 * 1024; //how many bytes max we read
		lucanaBufferData = new byte[lucanaBufferDataSizeMax];
	}

	/**
	 * Open connection to Lucana camera
	 * e.g:
	 * 	myCamera.login();
	 * @return
	 * 	true if connection successful 
	 *  false if not 
	 */
	public boolean login() {
		System.out.printf("Sunrise --> Opening connection to Lucana at: " + getServerAddress() + " port: " + getServerPort() + "...");
		try {
			// Connect to the server
			telnet.connect(getServerAddress(), getServerPort());
			//telnet.connect(server, 10023);

			// Get input and output stream 
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			//LUCANA does not respond with anything 
			
			System.out.printf("Sunrise --> ...established");
			return true;
		}
		catch (Exception e) {
			System.out.println("Sunrise --> FAILED: Connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
			System.out.println("KUKA Roboter says: Check ethernet cable connections");
			System.out.println("Application HALT for now <LucanaIIWA_CommLib>");
			//e.printStackTrace();
			return false;
		}
	}


	public void disconnect() {
		if (telnet.isConnected()) {
			try {
				telnet.disconnect();
				System.out.println("Sunrise --> Lucana disconnected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Sunrise --> No Lucana connection");
		}
	}


	/**
	 * Attempt to read Lucana responses.
	 * 
	 * @return boolean 
	 */
	public boolean readLucanaResponse(boolean displayRawBytesValues) {
		final String CRLF = "1310";
		boolean success = false;
		byte[] localDataBuffer = new byte[16 * 1024];
		
		try {		
				int BufferSize = in.read(localDataBuffer);
				this.setLucanaBufferDataSize(BufferSize);
				this.lucanaBufferData = new byte[getLucanaBufferDataSize()];
				
				for (int i = 0; i < localDataBuffer.length; i++) {
					this.lucanaBufferData[i] = localDataBuffer[i];
				}
				
				String dataInputString = displayBuffer(getLucanaBufferData());
				
				System.out.println(">>>Response buffer length:" + getLucanaBufferDataSize());
				if (displayRawBytesValues)
					System.out.println(">>>Buffer values: " + displayBuffer(getLucanaBufferData()));
				System.out.println(">>>Ascii values response:\n " + displayBufferAscii(getLucanaBufferData()));
				if (!dataInputString.contains(CRLF)) {
					System.out.println("No CLRF !!!");
				}
				success = true;
			
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	public byte[] readLucanaResponseDumpBytes(boolean displayRawBytesValues) {
		final String CRLF = "1310";

		byte[] buffer = new byte[16 * 1024];
		boolean finish = false;
		boolean success = false;

		OutputStream out = null;
		try {
			out = new FileOutputStream("D:/LucanaDump.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			while (!finish) {
				int bufferSize = in.read(buffer);
				out.write(buffer, 0, bufferSize);
				out.close();
				in.close();
				String telnetInputString = displayBuffer(buffer, bufferSize);
				System.out.println(">>>Response buffer length:" + bufferSize);
				if (displayRawBytesValues)
					System.out.println(">>>Buffer values: " + displayBuffer(buffer, bufferSize));
				if (!telnetInputString.contains(CRLF)) {
					System.out.println("No CLRF !!!");
				}
				System.out.println();
				finish = true;
				success = true;

				clearBuffer(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	
	public void write(String value) {
		try {
			//System.out.println("Sunrise --> " + value);
			out.println(value);
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String readUntil(String pattern) {
		int asciiValue; 
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			asciiValue = in.read();
			char ch = (char) asciiValue;
			while (true) {
				//System.out.print(asciiValue + " ");
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				asciiValue = in.read();
				ch = (char) asciiValue;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void readUntilCRLF() {
		boolean response = false;
		byte[] buffer = new byte[lucanaBufferDataSizeMax];
		try {
			while (!response) {
				int len = in.read(buffer);
				if ((buffer[len - 1] == 10) && (buffer[len - 2] == 13)) {
					System.out.println(displayBufferAscii(buffer, len));
					System.out.println("Buffer size: " + len + " Got CRLF: " + displayBuffer(buffer, len));
					response = true;
				} else {
					System.err.println("Buffer size: " + len + " no CRLF !: " + displayBuffer(buffer));
				}
				clearBuffer(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readResponse(int value) { 
		int intByteValue;
		int asciiValue;
		boolean response = false;
		try {
			while (response == false) {
				intByteValue = in.read();
				asciiValue = Character.getNumericValue(intByteValue);
				if (asciiValue == -1) {
					continue;
				} else {
					response = true;
					if (asciiValue == value) {
						System.out.println("Cognex --> " + asciiValue);
					} else {
						System.err.println("Cognex --> " + asciiValue + " Byte integer: " + intByteValue);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readRawResponse() {
		int asciiValue;
		boolean finishLoop = false;
		try {
			while (!finishLoop) {
				asciiValue = in.read();
				System.out.print("<byte: " + asciiValue + " " + " ascii: " + Character.getNumericValue(asciiValue) + ">");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readResponse() {
		byte[] buffer = new byte[lucanaBufferDataSizeMax];
		try {
			while (true) {
				int asciiValue = in.read(buffer);
				System.out.println("Response buffer length:" + asciiValue);
				System.out.println("Buffer values: " + displayBuffer(buffer));
				System.out.println("Ascii values: " + displayBufferAscii(buffer));
				clearBuffer(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String displayBuffer (byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {
			sb.append(buffer[i]);
		}
		return sb.toString();
	}

	private String displayBuffer (byte[] buffer, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(buffer[i]);
		}
		return sb.toString();
	}

	private String displayBufferAscii (byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] == 10 || buffer[i] == 13) continue; //ignore carriage return and new lines on display
			sb.append(Character.toString((char) buffer[i]));
		}
		return sb.toString();
	}

	private String displayBufferAscii (byte[] buffer, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (buffer[i] == 10 || buffer[i] == 13) continue; //ignore carriage return and new lines on display
			sb.append(Character.toString((char) buffer[i]));
		}
		return sb.toString();
	}

	private String displayBufferAscii (byte[] buffer, int start, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < len; i++) {
			if (buffer[i] == 10 || buffer[i] == 13) continue; //ignore carriage return and new lines on display
			sb.append(Character.toString((char) buffer[i]));
		}
		return sb.toString();
	}

	private void clearBuffer (byte[] buffer) {
		for (int i = 0; i < lucanaBufferDataSizeMax; i++) {
			buffer[i] = 0;
		}
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public byte[] getLucanaBufferData() {
		return lucanaBufferData;
	}

	public void setLucanaBufferData(byte[] lucanaBufferData) {
		this.lucanaBufferData = lucanaBufferData;
	}

	public int getLucanaBufferDataSize() {
		return lucanaBufferDataSize;
	}

	public void setLucanaBufferDataSize(int lucanaBufferDataSize) {
		this.lucanaBufferDataSize = lucanaBufferDataSize;
	}

}
