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

public class CognexIIWA_Telnetlib {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private int bufferSize = 50; //how many bytes max we read
	
	//Object properties
	private ECognexTrigger currentTrigger;
	private ECognexCommand currentCommand;
	private int cognexCommandResponseValue;
	private String cognexSpreadSheetValue;
	private char spreadSheetColumn;
	private int spreadSheetRow;
	private double cognexSpreadSheetValueDouble;
	private String username;
	private String password;
    private String serverAddress;
    private int serverPort;
    
	public CognexIIWA_Telnetlib(String serverAddress, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(23);		//default , use other constructor to pass your port number
	}
	
	public CognexIIWA_Telnetlib(String serverAddress, int serverPort, String user, String password) {
		this.initialize();
		this.setUsername(user);
		this.setPassword(password);
		this.setServerAddress(serverAddress);
		this.setServerPort(serverPort);
		
	}
	private void initialize() {
		this.setCurrentTrigger(ECognexTrigger.NULL);
		this.setCurrentCommand(ECognexCommand.NULL);
		this.setCognexCommandResponseValue(0);
		this.setCognexSpreadSheetValue(null);
		this.setCognexSpreadSheetValueDouble(0);
	}
	
	public boolean login() {
		try {
			// Connect to the server
			telnet.connect(getServerAddress(), getServerPort());
			//telnet.connect(server, 10023);
		
			// Get input and output stream 
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			
			// Log the user 
			readUntil("User: ");
			//readResponse();
			write(getUsername());
			readUntil("Password: ");
			//readResponse();
			write(getPassword()); 
			this.readUntilCRLF();
			
			System.out.println("Sunrise --> Telnet connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
			return true;
		}
		catch (Exception e) {
			System.out.println("Sunrise --> FAILED: Telnet connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
			System.out.println("KUKA Roboter says: Check ethernet cable connections");
			System.out.println("Application HALT for now <CognexIIWA_TelnetLib>");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean loginLucana() {
		try {
			// Connect to the server
			telnet.connect(getServerAddress(), getServerPort());
			//telnet.connect(server, 10023);
		
			// Get input and output stream 
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			this.readUntilCRLF();
			System.out.println("Sunrise --> Telnet connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
			return true;
		}
		catch (Exception e) {
			System.out.println("Sunrise --> FAILED: Telnet connection to: " + getServerAddress() + " port: " + telnet.getRemotePort());
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
     * Universal attempt to read Cognex telnet responses.
     * Currently supports feedback from: SE8, SW8, GV commands.
     * Method return true only if there was CRLF in a buffer.
     * At that point user may use additional methods to read values from buffer.
     * @see
     * getCognexCommandResponseValue()
     * getCognexSpreadSheetValue()
     * 
     * @return boolean 
     */
    public boolean readCognexResponse() {
		this.setCognexCommandResponseValue(0);
		this.setCognexSpreadSheetValue(null);
		this.setCognexSpreadSheetValueDouble(0);
		final String CRLF = "1310";
		
    	byte[] buffer = new byte[bufferSize];
    	boolean finish = false;
    	boolean success = false;
    	
    	try {
    		while (!finish) {
    			int bufferSize = in.read(buffer);
    			String telnetInputString = displayBuffer(buffer, bufferSize);
    			String commandResponse;
    			String valueReceived;
    			System.out.println(">>>Response buffer length:" + bufferSize);
    			System.out.println(">>>Buffer values: " + displayBuffer(buffer, bufferSize));
    			System.out.println(">>>Ascii values: " + displayBufferAscii(buffer, bufferSize));
    			if (telnetInputString.contains(CRLF)) {
    				int locateCRLF = telnetInputString.indexOf(CRLF);
    				if (locateCRLF > 0) {
    					commandResponse = displayBufferAscii(buffer,locateCRLF-1);
    					this.setCognexCommandResponseValue(Integer.parseInt(commandResponse));
    					if (telnetInputString.substring(locateCRLF + CRLF.length(), telnetInputString.length()).endsWith(CRLF)) {
    						System.out.println("Bingo");
    					}
    					valueReceived = displayBufferAscii(buffer, locateCRLF, bufferSize);
    					
    					if (valueReceived.length() > 0) {
    						this.setCognexSpreadSheetValue(valueReceived);
    					}
    					System.out.println("Cognex --> Command response: " + this.getCognexCommandResponseValue());
    					System.out.println("Cognex --> Value received: " + this.getCognexSpreadSheetValue());
    					if (this.getCurrentCommand() == ECognexCommand.GV && valueReceived.length() > 0) {
    						try {
    							double currentCognexSpreadSheetValueDouble = Double.parseDouble(valueReceived);
    							this.setCognexSpreadSheetValueDouble(currentCognexSpreadSheetValueDouble);
    						}
    						catch (NumberFormatException e) {
    							System.err.println(" - value is not of double type ! <CognexIIWAlib>");
    						}
    					}
    				}
    				
    				System.out.println();
    				finish = true;
    				success = true;
    			} else {
    				System.err.println("Ausgebombt !!!");
    				finish = true;
    			}
	
			clearBuffer(buffer);
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return success;
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
	
	/**
	 * Method sends trigger command to Cognex camera to take a snapshot
	 * @param command
	 * Parameter can be SE8 or SW8 of ECognexTrigger enumerator type
	 * E.G. sendCognexTrigger(ECognexTrigger.SE8)
	 */
	public void sendCognexTrigger (ECognexTrigger command) {
		this.setCurrentTrigger(ECognexTrigger.NULL);
		switch (command) {
		case SE8:
		case SW8:
			this.setCurrentTrigger(command);
			break;

		default:
			throw new ArithmeticException("Unknown trigger: " + command + " <CognexIIWAlib>");
		}
		
		try {
			out.print(command+"\r\n");
			out.flush();
			System.out.println("Sunrise --> Command executed: " + command);
			this.readUntilCRLF();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send command to Cognex 
	 * Currently supports GV command
	 * {@code sendCognexCommand(ECognexCommand.GV, "B012") }
	 * Result in sending GVB012
	 * @param command GV enum type ECognexCommand
	 * @param location "A003" String where A is a column 003 is a row
	 */
	public void sendCognexCommand(ECognexCommand command, String location) {
		this.setCurrentCommand(ECognexCommand.NULL);
		this.initialize();
		switch (command) {
		case GV:
			this.setCurrentCommand(command);
			break;

		default:
			throw new ArithmeticException("Unknown command: " + command + " <CognexIIWAlib>");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(command.toString());
		sb.append(location);
		try {
			System.out.println("Sunrise --> " + sb);
			out.print(sb+"\r\n");
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send command to Cognex 
	 * Currently supports GV command
	 * {@code sendCognexCommand(ECognexCommand.GV, "B", 12) }
	 * Result in sending GVB012
	 * @param command GV (enum type ECognexCommand)
	 * @param column (String)
	 * @param row (int)
	 */
public void sendCognexCommand(ECognexCommand command, String column, int row) {
		this.setCurrentCommand(ECognexCommand.NULL);
		StringBuilder sb = new StringBuilder();
		//Get command
		switch (command) {
		case GV:
			this.setCurrentCommand(command);
			String stringRepOfInt = String.format("%03d", row);
			sb.append(command.toString());
			sb.append(column);
			sb.append(stringRepOfInt);
			break;

		default:
			throw new ArithmeticException("Unknown command: " + command + " <CognexIIWAlib>");
		}
		//Send command
		try {
			System.out.println("Sunrise --> " + sb);
			out.print(sb+"\r\n");
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

public void sendCognexCommand(ECognexCommand command, String column, int row, double value) {
	this.setCurrentCommand(ECognexCommand.NULL);
	StringBuilder sb = new StringBuilder();
	String stringRepOfInt;
	//Get command
	switch (command) {
	case SF:
		this.setCurrentCommand(command);
		stringRepOfInt = String.format("%03d", row);
		sb.append(command.toString());
		sb.append(column);
		sb.append(stringRepOfInt);
		sb.append(value);
		
		break;
	case GV:
		this.setCurrentCommand(command);
		stringRepOfInt = String.format("%03d", row);
		sb.append(command.toString());
		sb.append(column);
		sb.append(stringRepOfInt);
		break;

	default:
		throw new ArithmeticException("Unknown command: " + command + " <CognexIIWAlib>");
	}
	//Send command
	try {
		System.out.println("Sunrise --> Commnad executed: " + sb);
		out.print(sb+"\r\n");
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

	public ECognexTrigger getCurrentTrigger() {
		return currentTrigger;
	}

	public void setCurrentTrigger(ECognexTrigger currentTrigger) {
		this.currentTrigger = currentTrigger;
	}

	public ECognexCommand getCurrentCommand() {
		return currentCommand;
	}

	public void setCurrentCommand(ECognexCommand currentCommand) {
		this.currentCommand = currentCommand;
	}

	public int getCognexCommandResponseValue() {
		return cognexCommandResponseValue;
	}

	public void setCognexCommandResponseValue(int cognexCommandResponseValue) {
		this.cognexCommandResponseValue = cognexCommandResponseValue;
	}

	public String getCognexSpreadSheetValue() {
		return cognexSpreadSheetValue;
	}

	public void setCognexSpreadSheetValue(String cognexSpreadSheetValue) {
		this.cognexSpreadSheetValue = cognexSpreadSheetValue;
	}
	public double getCognexSpreadSheetValueDouble() {
		return cognexSpreadSheetValueDouble;
	}
	public void setCognexSpreadSheetValueDouble(double cognexSpreadSheetValueDouble) {
		this.cognexSpreadSheetValueDouble = cognexSpreadSheetValueDouble;
	}
	public char getSpreadSheetColumn() {
		return spreadSheetColumn;
	}
	public void setSpreadSheetColumn(char spreadSheetColumn) {
		this.spreadSheetColumn = spreadSheetColumn;
	}
	public int getSpreadSheetRow() {
		return spreadSheetRow;
	}
	public void setSpreadSheetRow(int spreadSheetRow) {
		this.spreadSheetRow = spreadSheetRow;
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
