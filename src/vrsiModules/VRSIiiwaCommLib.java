package vrsiModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuka.common.ThreadUtil;


//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.Multimap;

import vrsiModules.VRSIcommon.EVRSIhomeSlide;
import vrsiModules.VRSIcommon.EVRSIscanFastener;

/**
 * @author MPiotrowski AIT Corp. July 2017
 * 
 * Library providing methods to interface with VRSI scanner (specifically for Lockheed Hot Dot project)
 * Brief overview on how this works and how to use it (mostly for myself)
 * Class VRSIIiiwaCommLib is a main class that should be used in a robot program (only this one)
 * All other classes are helper classes. 
 * VRSIemptyFastener and VRSIfillFastener holds information provided by VRSI after scan. 
 * Data for normalization and reposition (VRSIemptyFastener) 
 * Data for diagnostics/results (VRSIfillFastener)
 * These should be modified if more detail information is needed/provided in the future.
 * VRSIscanSmptyFastener, VRSIscanFillFastener, VRSIsetSlideHome are all implementing Runnable as they are executed as Threads from this class.
 * Main reason for that is to have a Thread under timeout control. As we send a command to VRSI we expect result in a given time frame.
 * If results are not provided we time out (Thread gets canceled)
 * StreamDataCommLib is responsible for socket TCP/IP communication (login, send String, read VRSI response, disconnect)
 * User should only call 3 methods from this library to execute VRSI command.
 * public boolean setSlideHome(long timeout) - commands VRSI to move to home position (before each scan we need to make sure we are there)
 * public boolean scanEmptyFastener(String holeID, double pinDia, int pinType, long timeout) - commands VRSI to scan Empty Fastener (reposition data)
 * public boolean scanFillFastener(String holeID, double pinDia, int pinType, long timeout) - commands VRSI to scan Fill Fastener (diagnostics data)
 * 
 * Example chain of commands for scanFillFastener 
 * program calls  scanFillFastener("ABC001", 5.6, 1, 10000) //Scan Fastener with id ABC001, expected diameter, 5.6mm, pin type 1 (flat head) and timeout of 10000mm
 * Runnable thread (VRSIscanFillFastener) is executed. It is expected to run under 10 seconds and provide TRUE as a result (scan successful)
 * At this point user will call get methods (to be implemented yet) to pull the results which will be provide as VRSIemptyFastener or VRSIfillFastener class objects. 
 *
 * More description on detail Thread execution in VRSIscanFillFastener.java class and scanFillFastener method in this class
 *
 */
public class VRSIiiwaCommLib {
	//IIWA to VRSI
	private int modeStatus;			//mode status
	private int homeSlide;			//0 = Idle, 1 = Return All Slides to Home
	private int faultReset;			//When set to 1, Command Status will be set to 0, clearing any fault code
	private String holeID;			//FLU or Smartpoint name of hole
	private double pinDiamater;		//Diameter of the pin to be measured
	private int pinType;			//pin type to scan 
	private int vrsiCommand;		//vrsi command - PacketType
	private static final String vrsiPrefix = "VRSI";	//each message to and from VRSI has this prefix
	private static final String vrsiPostfix = "0Dh";	//each message to and from VRSI has this postfix
	//0dh is equivalent to 13 in decimal and to carriage return ('\r') in ASCII which moves the cursor to the beginning of the current row.
	private static final String delimiter = ";";
	private boolean debug;			//when enabled at constructor display additional DEBUG messages
	private StreamDataCommLib commPort;	//instance of communication library independent from custom VRSI stuff
	private String vrsiServerIP;		//IP address of VRSI server
	private int vrsiServerPort;			//Post address of VRSI server 

	private VRSIemptyFastener emptyFastenerData; 	//Successful scan will initialize empty fastener data for processing (evaluation, bot reposition) 
	private VRSIfillFastener fillFastenerData;		//Successful scan will initialize fill fastener data for processing (PLC?)

	private static final Map<Integer, String> pinTypeMap;
	static {
		Map<Integer, String> aMap = new HashMap<Integer, String>();
		aMap.put(0, "Scan_ACK");
		aMap.put(1, "FlatHead");
		aMap.put(2, "StarHead");
		aMap.put(3, "Blind");
		pinTypeMap = Collections.unmodifiableMap(aMap);
	}

	public static final String 
	POCKETTYPE = "1",
	CMDSTATUS = "2", 
	HOLEID = "3",
	DATASTART = "4";

	private String POCKETTYPE_TARGET, CMDSTATUS_TARGET;

	public VRSIiiwaCommLib() {
		this.debug = false;
		init();
	}

	public VRSIiiwaCommLib(boolean debug) {
		this.debug = debug;
		init();
	}

	public void init() {

		this.setVrsiServerIP("172.31.1.230");
		this.setVrsiServerPort(30001);
		commPort = new StreamDataCommLib(getVrsiServerIP(), getVrsiServerPort());
		fillFastenerData = new VRSIfillFastener();
		fillFastenerData = null;
		emptyFastenerData = new VRSIemptyFastener();
		emptyFastenerData = null;
	}

	/**
	 * Check response String from VRSI for scan fastener command (empty, fill)
	 * @param response 	- String response from VRSI
	 * @param cmd		- enum (EVRSIscanFastener) expected VRSI response 
	 * @return			- true if expected response is received and data evaluation was good 
	 */
	public boolean getScanFastenerResponse(String response, EVRSIscanFastener cmd) {
		boolean bResult = false;
		List<String> stringsList = new ArrayList<String>(Arrays.asList(response.split(";")));
		//check if our command is within the range 
		//0=idle,  1=processing, 2=Command Completed Sucessfully, 3+…=error codes
		if (Integer.parseInt(stringsList.get(Integer.parseInt(CMDSTATUS))) <= 2) {
			switch (cmd) {
			case ScanEmptyFastenerCmd:
				//VRSI received KRC command and response with VRSI;1;1;FLU123;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0Dh
				//Expect 1 for pocket type (ScanPin) 
				//Expect 1 for command status (Processing)
				//Expect hole ID to match what KRC send in request
				POCKETTYPE_TARGET = "1";
				CMDSTATUS_TARGET = "1";
				break;

			case ScanEmptyFastenerComplete:
				//VRSI received KRC command and response with VRSI;1;1;FLU123;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0Dh
				//Expect 1 for pocket type (ScanPin) 
				//Expect 2 for command status (Completed Successfully)
				//Expect hole ID to match what KRC send in request
				POCKETTYPE_TARGET = "1";
				CMDSTATUS_TARGET = "2";
				break;

			case ScanFillFastenerCmd:
				//VRSI received KRC command and response with VRSI;2;1;FLU123;0.000;0.000;0.000;0;0;0;0;0;0;0;0;0Dh
				//Expect 2 for pocket type (ScanFill) 
				//Expect 1 for command status (Processing)
				//Expect hole ID to match what KRC send in request
				POCKETTYPE_TARGET = "2";
				CMDSTATUS_TARGET = "1";
				break;

			case ScanFillFastenerComplete:
				//VRSI received KRC command and response with VRSI;1;1;FLU123;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0.000;0Dh
				//Expect 2 for pocket type (ScanFill) 
				//Expect 2 for command status (Completed Successfully)
				//Expect hole ID to match what KRC send in request
				POCKETTYPE_TARGET = "2";
				CMDSTATUS_TARGET = "2";
				break;

			default:
				System.err.println("Unknown command: " + cmd + " <VRSIiiwaCommLib>");
				break;
			}

			if (	(stringsList.get(Integer.parseInt(POCKETTYPE)).equals(POCKETTYPE_TARGET)) && 
					(stringsList.get(Integer.parseInt(CMDSTATUS)).equals(CMDSTATUS_TARGET)) && 
					(stringsList.get(Integer.parseInt(HOLEID)).equals(getHoleID()))) {
				//Everything seems to match , evaluate data 
				List<String> dataString = stringsList.subList(Integer.parseInt(DATASTART), stringsList.size()-1);
				if (processScanData(cmd, dataString)) bResult = true;

			} else {
				//Echo from VRSI did no match what we expect
				System.err.println("Command: " + cmd + " unexpected response");
				System.err.println("Command echo from VRSI: " + stringsList.get(Integer.parseInt(POCKETTYPE)) + " Expected: " + POCKETTYPE_TARGET);
				System.err.println("Command Status echo from VRSI: " + stringsList.get(Integer.parseInt(CMDSTATUS)) + " Expected: " + CMDSTATUS_TARGET);
				System.err.println("HoleID echo from VRSI: " + stringsList.get(Integer.parseInt(HOLEID)) + " Expected: " + getHoleID());
			}

		} else {

			// TO DO
			// catch error codes here

		}
		return bResult;
	}

	/**
	 * Command VRSI to send slide to home position (REQ/ACK)
	 * @param timeout - milliseconds, time period for VRSI to successfully execute command
	 * 					set to negative value timeout is disabled
	 * @return boolean - 	true if VRSI is at home or gets back to home before timeout
	 * 						false otherwise
	 */
	public boolean setSlideHome(long timeout) {
		long timer = 0;
		long hertz = 100;
		VRSIsetSlideHome slideHomeRunnable = new VRSIsetSlideHome();
		slideHomeRunnable.setCommPorthandle(commPort);
		slideHomeRunnable.setVRSIcommHandle(this);
		Thread slideHomeThread = new Thread(slideHomeRunnable);
		slideHomeThread.setDaemon(true);
		slideHomeThread.start();
		while (!slideHomeRunnable.isbSuccess()) {
			if (timeout >= 0) {
				if (timer >= timeout) {
					System.err.println("Timeout!  requested: " + timeout + " actual: " + timer);
					break;
				}
			}
			ThreadUtil.milliSleep(hertz);
			timer +=hertz;
		}
		if (this.debug) {
			System.out.println("DEBUG: Process timer value: " + timer);
		}
		commPort.disconnect();
		return slideHomeRunnable.isbSuccess();
	}

	/**
	 * Command VRSI to scan Empty fastener (REQ/ACK)
	 * @param holeID	- String holeID
	 * @param pinDia	- double fastener diameter
	 * @param pinType	- int fastener type 1 = Flat Head, 2 = Star Head, 3 = Blind
	 * @param timeout	- milliseconds, time period for VRSI to successfully execute command
	 * @return			- true if VRSI completed scan successfully and data passed evaluation
	 * 					- false otherwise
	 */
	public boolean scanEmptyFastener(String holeID, double pinDia, int pinType, long timeout) {
		long timer = 0;
		long hertz = 100;
		VRSIscanEmptyFastener scanEmptyFastenerRunnable = new VRSIscanEmptyFastener();
		scanEmptyFastenerRunnable.setCommPorthandle(commPort);
		scanEmptyFastenerRunnable.setScanFastener(holeID, pinDia, pinType, this);
		Thread scanEmptyFastenerThread = new Thread(scanEmptyFastenerRunnable);
		scanEmptyFastenerThread.setDaemon(true);
		scanEmptyFastenerThread.start();
		while (!scanEmptyFastenerRunnable.isbSuccess()) {
			if (timeout >= 0) {
				if (timer >= timeout) {
					System.err.println("Timeout!  requested: " + timeout + " actual: " + timer);
					break;
				}
			}
			ThreadUtil.milliSleep(hertz);
			timer +=hertz;
		}
		if (this.debug) {
			System.out.println("DEBUG: Process timer value: " + timer);
		}
		commPort.disconnect();
		return scanEmptyFastenerRunnable.isbSuccess();
	}

	/**
	 * Command VRSI to scan Fill fastener (REQ/ACK)
	 * @param holeID	- String holeID
	 * @param pinDia	- double fastener diameter
	 * @param pinType	- int fastener type 1 = Flat Head, 2 = Star Head, 3 = Blind
	 * @param timeout	- milliseconds, time period for VRSI to successfully execute command
	 * @return			- true if VRSI completed scan successfully and data passed evaluation
	 * 					- false otherwise
	 */
	public boolean scanFillFastener(String holeID, double pinDia, int pinType, long timeout) {
		long timer = 0;			//timer will run from 0 up to timeout values 
		long hertz = 100;		//timer delay and update, every so many miliseconds we check if Thread was successful 
		VRSIscanFillFastener scanFillFastenerRunnable = new VRSIscanFillFastener();	//instance the runnable class 
		scanFillFastenerRunnable.setCommPorthandle(commPort);						//give it communication handle
		scanFillFastenerRunnable.setScanFastener(holeID, pinDia, pinType, this);	//sets its parameters
		Thread scanFillFastenerThread = new Thread(scanFillFastenerRunnable);		//assigns class object to the thread
		//scanFillFastenerThread.setDaemon(true);
		scanFillFastenerThread.start();												// START the thread
		while (!scanFillFastenerRunnable.isbSuccess()) {							//keep looping and checking if we were successful 
			if (timeout >= 0) {														//timer value below 0 disables timeout(yeah it will run forever)
				if (timer >= timeout) {												//timeout condition
					System.err.println("Timeout!  requested: " + timeout + " actual: " + timer);
					break;															//timeout, no success
				}
			}
			ThreadUtil.milliSleep(hertz);	//loop delay 
			timer +=hertz;					//timer runs up
		}
		if (this.debug) {													//debug message if enabled
			System.out.println("DEBUG: Process timer value: " + timer);
		}
		commPort.disconnect();												//disconnect from VRSI
		return scanFillFastenerRunnable.isbSuccess();						//return status
	}

	/**
	 * Check response String from VRSI for slide home command  
	 * @param response 	- String response from VRSI
	 * @param cmd		- enum (EVRSIhomeSlide) expected VRSI response 
	 * @return			- true if expected response is received 
	 */
	public boolean getSlideHomeResponse(String response, EVRSIhomeSlide cmd) {
		boolean bResult = false;
		switch (cmd) {
		case SlideHomeCmdReceived:
			//VRSI received KRC command and response with VRSI;100;0;0Dh
			if (response.equals("VRSI;100;0;0Dh")) bResult = true; 
			break;
		case SlideAtHome:
			//VRSI slide at home position; response VRSI;100;1;0Dh
			if (response.equals("VRSI;100;1;0Dh")) bResult = true;
			break;
		default:
			System.err.println("Unknown command: " + cmd + " <VRSIiiwaCommLib>");
			break;
		}
		if (!bResult) {
			System.err.println("Home slide command response: " + cmd + " no match");
		}
		return bResult;
	}


	/**
	 * Command to send vrsi slide home, request
	 */
	public String setSlideHomeREQ() {
		if (debug) System.out.println("Sunrise --> DEBUG: HomeSlideREQ");
		return vrsiPrefix + ";100;1;" + vrsiPostfix;
	}

	/**
	 * Command to send vrsi slide home, acknowledge
	 */
	public String setSlideHomeACK() {
		if (debug) System.out.println("Sunrise --> DEBUG: HomeSlideACK");
		return vrsiPrefix + ";100;0;" + vrsiPostfix;
	}


	/**
	 * VRSI command to scan empty fastener 
	 * @param holeID String - hole ID (user, PLC)
	 * @param pinDia double - expected diameter
	 * @param pinType int - 1 - Flat head, 2 - Star head, 3 - blind
	 * @return String - to be sent to VRSI server, e.g. VRSI;1;FLU123;0.190;1;0Dh
	 */
	public String scanFastenerREQ(String holeID, double pinDia, int pinType) {
		//VRSI;1;FLU123;0.190;1;0Dh
		setHoleID(holeID);
		setPinDiamater(pinDia);
		setPinType(pinType);
		if (debug) System.out.println("Sunrise --> DEBUG: Scan Empty Fastener REQ " + holeID + " " + pinTypeMap.get(pinType));
		return vrsiPrefix + delimiter
				+ 1 + delimiter 
				+ holeID + delimiter
				+ pinDia + delimiter
				+ pinType + delimiter
				+ vrsiPostfix;
	}

	/**
	 * VRSI command acknowledge scan pin data complete
	 * @return String - VRSI;1;;0.000;0;0Dh
	 */
	public String scanFastenerACK() {
		if (debug) System.out.println("Sunrise --> DEBUG: Scan Fastener ACK");
		return vrsiPrefix + ";1;;0.000;0;" + vrsiPostfix;
	}


	/**VRSI command to scan fill fastener 
	 * @param holeID
	 * @param pinDia
	 * @param pinType
	 * @return String - to be sent to VRSI server, e.g. VRSI;2;FLU123;0.190;1;0Dh
	 */
	public String scanFillREQ(String holeID, double pinDia, int pinType) {
		if (debug) System.out.println("Sunrise --> DEBUG: Scan Fill Fastener REQ " + holeID + " " + pinTypeMap.get(pinType));
		//VRSI;2;FLU123;0.190;1;0Dh
		setHoleID(holeID);
		setPinDiamater(pinDia);
		setPinType(pinType);

		return vrsiPrefix + delimiter
				+ 2 + delimiter 
				+ holeID + delimiter
				+ pinDia + delimiter
				+ pinType + delimiter
				+ vrsiPostfix;
	}

	/**
	 * VRSI command acknowledge scan fill data complete
	 * @return String - VRSI;2;0;;0.000;0;0Dh
	 */
	public String scanFillACK() {
		if (debug) System.out.println("Sunrise --> DEBUG: Scan Fill Fastener ACK");
		return vrsiPrefix + ";2;0;;0.000;0;" + vrsiPostfix;
	}

	/**
	 * Once we receive correct feedback from VRSI we want to evaluate actual numbers and set data objects for further processing
	 * @param cmd - command that was send to VRSI  
	 * @param dataString - data String received from VRSI
	 * @return
	 */
	public boolean processScanData(EVRSIscanFastener cmd, List<String> dataString) {
		boolean bResult = false;
		switch (cmd) {
		case ScanEmptyFastenerCmd: case ScanEmptyFastenerComplete:
			VRSIemptyFastener emptyFastener;
			try {
				emptyFastener = new VRSIemptyFastener(	
						Double.parseDouble(dataString.get(0)), 
						Double.parseDouble(dataString.get(1)), 
						Double.parseDouble(dataString.get(2)), 
						Double.parseDouble(dataString.get(3)), 
						Double.parseDouble(dataString.get(4)), 
						Double.parseDouble(dataString.get(5)), 
						Double.parseDouble(dataString.get(6)), 
						Double.parseDouble(dataString.get(7)),
						Double.parseDouble(dataString.get(8)), 
						Double.parseDouble(dataString.get(9)), 
						Double.parseDouble(dataString.get(10)));
			} catch (NumberFormatException e) {
				System.err.println(e);
				return bResult;
			}
			switch (cmd) {
			case ScanEmptyFastenerCmd:
				//ACK from VRSI should be all 0.000
				if (allZeros(cmd, dataString)) {
					System.err.println("Command response " + cmd + " unexpected data");
					System.out.println(emptyFastener.toString());
				} else {
					bResult = true;
				}
				break;
			case ScanEmptyFastenerComplete:
				//Useful data from VRSI
				emptyFastener.setHoleID(getHoleID());
				emptyFastenerData = new VRSIemptyFastener(emptyFastener);
				bResult = true; 
				break;
			default:
				break;
			}
			break;

		case ScanFillFastenerCmd: case ScanFillFastenerComplete:
			VRSIfillFastener fillFastener;
			try {
				fillFastener = new VRSIfillFastener(
						Double.parseDouble(dataString.get(0)), 
						Double.parseDouble(dataString.get(1)), 
						Double.parseDouble(dataString.get(2)), 
						Integer.parseInt(dataString.get(3)), 
						Integer.parseInt(dataString.get(4)), 
						Integer.parseInt(dataString.get(5)), 
						Integer.parseInt(dataString.get(6)), 
						Integer.parseInt(dataString.get(7)),
						Integer.parseInt(dataString.get(8)), 
						Integer.parseInt(dataString.get(9)), 
						Integer.parseInt(dataString.get(10)));

			} catch (NumberFormatException e) {
				System.err.println(e);
				return bResult;
			}
			switch (cmd) {
			case ScanFillFastenerCmd:
				//ACK from VRSI should be all 0.000
				if (allZeros(cmd, dataString)) {
					System.err.println("Command response " + cmd + " unexpected data");
					//System.out.println(fillFastener.toString());
				} else {
					bResult = true;
				}
				break;
			case ScanFillFastenerComplete:
				//Usefull data from VRSI
				fillFastener.setHoleID(getHoleID());
				fillFastenerData = new VRSIfillFastener(fillFastener);
				bResult = true; 
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}

		if (!bResult) System.err.println("Data evaluation failure");
		return bResult;
	}

	/**
	 * Check REQ response from VRSI
	 * Since there is no data yet we are expecting 0.0 for all numbers
	 * @param cmd			- command (ScanEmptyFastenerCmd, ScanFillFastenerCmd)
	 * @param dataString	- actual data
	 * @return	- true if data is incorrect (not 0.0)
	 * 			- false otherwise
	 */
	private boolean allZeros(EVRSIscanFastener cmd, List<String> dataString) {
		boolean bNotZero = false;
		switch (cmd) {
		case ScanEmptyFastenerCmd:
			for(String vrsiData : dataString) {
				if (Double.parseDouble(vrsiData) != 0.000) bNotZero = true; 
			}
			break;
		case ScanFillFastenerCmd:
			for(String vrsiData : dataString) {
				try {
					if (Integer.parseInt(vrsiData) != 0) bNotZero = true;
				} catch (NumberFormatException e) {
					if (Double.parseDouble(vrsiData) != 0.000) bNotZero = true;
				}
			}
			break;
		default:
			break;
		}
		return bNotZero;
	}

	public void setPinType(int type) {
		if (pinTypeMap.containsKey(type)) {
			this.pinType = type;
			System.out.println("Sunrise --> DEBUG: Pin type: " + pinTypeMap.get(type) + " - " + type + " set");
		} else {
			System.err.println("No such pin type:" + type + "<setPinType(int type)>");
		}	
	}


	public int getModeStatus() {
		return modeStatus;
	}
	public void setModeStatus(int modeStatus) {
		this.modeStatus = modeStatus;
	}

	public int getFaultReset() {
		return faultReset;
	}
	public void setFaultReset(int faultReset) {
		this.faultReset = faultReset;
	}
	public String getHoleID() {
		return holeID;
	}
	public void setHoleID(String holeID) {
		this.holeID = holeID;
	}
	public double getPinDiamater() {
		return pinDiamater;
	}
	public void setPinDiamater(double pinDiamater) {
		this.pinDiamater = pinDiamater;
	}

	public int getVrsiCommand() {
		return vrsiCommand;
	}

	public void setVrsiCommand(int vrsiCommand) {
		this.vrsiCommand = vrsiCommand;
	}

	public int getPinType() {
		return pinType;
	}

	public int getHomeSlide() {
		return homeSlide;
	}

	public void setHomeSlide(int homeSlide) {
		this.homeSlide = homeSlide;
	}

	public String getVrsiServerIP() {
		return vrsiServerIP;
	}

	public void setVrsiServerIP(String vrsiServerIP) {
		this.vrsiServerIP = vrsiServerIP;
	}

	public int getVrsiServerPort() {
		return vrsiServerPort;
	}

	public void setVrsiServerPort(int vrsiServerPort) {
		this.vrsiServerPort = vrsiServerPort;
	}

	public StreamDataCommLib getCommPort() {
		return commPort;
	}

	public void setCommPort(StreamDataCommLib commPort) {
		this.commPort = commPort;
	}
	
	/**
	 * @return
	 * If we did get correct data from VRSI this should return reference to object that holds all diagnostic data
	 */
	public VRSIfillFastener getFillFastenerData() {
		return fillFastenerData;
	}
	/**
	 * @return
	 * If we did get correct data from VRSI this should return reference to object that holds all normalization/position data
	 */
	public VRSIemptyFastener getEmptyFastenerData() {
		return emptyFastenerData;
	}

}
