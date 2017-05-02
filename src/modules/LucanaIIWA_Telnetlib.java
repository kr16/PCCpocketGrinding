package modules;

import org.apache.commons.net.telnet.TelnetClient;

import modules.Common.ECognexCommand;
import modules.Common.ECognexTrigger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.activation.MimeTypeParameterList;
import javax.swing.plaf.SliderUI;

public class LucanaIIWA_Telnetlib {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private int bufferSize = 50; //how many bytes max we read

	//Object properties
	private String username;
	private String password;
	private String serverAddress;
	private int serverPort;

	public LucanaIIWA_Telnetlib(String serverAddress, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(23);		//default , use other constructor to pass your port number
	}

	public LucanaIIWA_Telnetlib(String serverAddress, int serverPort, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(serverPort);

	}
	private void initialize() {
		
	}

	public boolean login() {
		System.out.printf("Sunrise --> Opening connection to: " + getServerAddress() + " port: " + getServerPort() + "...");
		try {
			// Connect to the server
			telnet.connect(getServerAddress(), getServerPort());
			//telnet.connect(server, 10023);

			// Get input and output stream 
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			//LUCANA does not respond with anything 
			
			System.out.println("Sunrise --> ...established");
			return true;
		}
		catch (Exception e) {
			System.out.println("/nSunrise --> FAILED: Telnet connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
			System.out.println("KUKA Roboter says: Check ethernet cable connections");
			System.out.println("Application HALT for now <CognexIIWA_TelnetLib>");
			e.printStackTrace();
			return false;
		}
	}


	public void disconnect() {
		if (telnet.isConnected()) {
			try {
				telnet.disconnect();
				System.out.println("Sunrise --> Telnet disconnected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Sunrise --> No Telnet connection");
		}
	}


	/**
	 * Attempt to read Lucana responses.
	 * 
	 * @return boolean 
	 */
	public boolean readLucanaResponse(boolean displayRawBytesValues) {
		final String CRLF = "1310";

		byte[] buffer = new byte[1000];
		boolean finish = false;
		boolean success = false;

		try {
			while (!finish) {
				int bufferSize = in.read(buffer);
				String telnetInputString = displayBuffer(buffer, bufferSize);
				String commandResponse;
				String valueReceived;
				System.out.println(">>>Response buffer length:" + bufferSize);
				if (displayRawBytesValues)
					System.out.println(">>>Buffer values: " + displayBuffer(buffer, bufferSize));
				System.out.println(">>>Ascii values response:\n " + displayBufferAscii(buffer, bufferSize));
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
		return success;
	}

	public byte[] readLucanaResponseDumpBytes(boolean displayRawBytesValues) {
		final String CRLF = "1310";

		byte[] buffer = new byte[1000];
		boolean finish = false;
		boolean success = false;

		try {
			while (!finish) {
				int bufferSize = in.read(buffer);
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
		if (bufferSize > 0) 
			return buffer;
		else
			return null;
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
		byte[] buffer = new byte[bufferSize];
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
		byte[] buffer = new byte[bufferSize];
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
		for (int i = 0; i < bufferSize; i++) {
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

}
