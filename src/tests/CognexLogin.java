package tests;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class CognexLogin {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;

	public CognexLogin(String server, String user, String password) {
		
		try {
			// Connect to the server
			telnet.connect(server, 23);
		
			// Get input and output stream 
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());
			
			// Log the user 
			readUntil("User: ");
			write(user);
			readUntil("Password: ");
			write(password);

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
    public void readResponse(int value) { 
    	char ch;
    	int asciiValue;
        boolean response = false;
        System.out.print("Cognex --> ");
    	try {
    		while (response == false) {
    			asciiValue = in.read();
    			if (asciiValue == 10 || asciiValue == 13) continue;
    			if (Character.getNumericValue(asciiValue) == 1) {
    				System.out.print((char) asciiValue);
    				System.out.println();
    				return;
    			}
    			if (asciiValue == -1) {
    				throw new ArithmeticException("Response value from Cognex = -1");
    			}	
    		}

		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
	public void write(String value) {
		try {
			System.out.println("Sunrise --> " + value);
			out.println(value);
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return command;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		try {
			telnet.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
