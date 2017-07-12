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

public class VRSIiiwaCommLib {
	//IIWA to VRSI
	private int heartBeatIn;		//heartbeat signal from IIWA to VRSI
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
	private StreamDataCommLib commPort;
	private String vrsiServerIP;
	private int vrsiServerPort;
	
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
	}
	
	/**
	 * Check response String from VRSI for scan fastener command (empty, fill)
	 * @param response 	- String response from VRSI
	 * @param cmd		- enum (EVRSIscanFastener) expected VRSI response 
	 * @return			- true if expected response is received and data evaluation was good 
	 */
	public boolean getScanEmptyFastenerResponse(String response, EVRSIscanFastener cmd) {
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
	 * Command to send VRSI slide to home position
	 * @param timeout - milliseconds, time period for VRSI to successfully execute send home routine
	 * @return boolean - 	true if VRSI is at home or gets back to home before timout
	 * 						false otherwise
	 */
	public boolean setSlideHome(long timeout) {
		long timer = 0;
		long hertz = 100;
		VRSIsetSlideHome slideHomeRunnable = new VRSIsetSlideHome();
		slideHomeRunnable.setCommPorthandle(commPort);
		Thread slideHomeThread = new Thread(slideHomeRunnable);
		slideHomeThread.setDaemon(true);
		slideHomeThread.start();
		while (!slideHomeRunnable.isbSuccess()) {
			if (timer >= timeout) {
				break;
			}
			ThreadUtil.milliSleep(hertz);
			timer +=hertz;
		}
		System.out.println("Slide to Home Finished; timeout requested: " + timeout + " actual timer: " + timer);
		return slideHomeRunnable.isbSuccess();
	}
	
	public boolean scanEmptyFastener(String holeID, double pinDia, int pinType, long timeout) {
		long timer = 0;
		long hertz = 100;
		VRSIscanEmptyFastener scanEmptyFastenerRunnable = new VRSIscanEmptyFastener();
		scanEmptyFastenerRunnable.setCommPorthandle(commPort);
		scanEmptyFastenerRunnable.setScanFastener(holeID, pinDia, pinType);
		Thread scanEmptyFastener = new Thread(scanEmptyFastenerRunnable);
		scanEmptyFastener.setDaemon(true);
		scanEmptyFastener.start();
		while (!scanEmptyFastenerRunnable.isbSuccess()) {
			if (timer >= timeout) {
				break;
			}
			ThreadUtil.milliSleep(hertz);
			timer +=hertz;
		}
		System.out.println("Scan empty finish; timeout requested: " + timeout + " actual timer: " + timer);
		return scanEmptyFastenerRunnable.isbSuccess();
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
		//		setVRSIcommand("HomeSlideREQ");
		//		setHomeSlide(1);
		//		return vrsiPrefix + delimiter 
		//				+ getVrsiCommand() + delimiter 
		//				+ getHomeSlide() + delimiter
		//				+ vrsiPostfix;
	}

	/**
	 * Command to send vrsi slide home, acknowledge
	 */
	public String setSlideHomeACK() {
		if (debug) System.out.println("Sunrise --> DEBUG: HomeSlideACK");
		return vrsiPrefix + ";100;0;" + vrsiPostfix;
		//		setVRSIcommand("HomeSlideACK");
		//		setHomeSlide(0);
		//		return vrsiPrefix + delimiter 
		//				+ getVrsiCommand() + delimiter 
		//				+ getHomeSlide() + delimiter
		//				+ vrsiPostfix;
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
		//		setVRSIcommand("Scan_Pin");
		setHoleID(holeID);
		//		setPinDiamater(pinDia);
		//		setPinType(pinType);
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
		//setVRSIcommand("Scan_Pin");
		//setHoleID("");
		//setPinDiamater(0.000);		
		//setPinType(0);

		//		return vrsiPrefix + delimiter
		//				+ getVrsiCommand() + delimiter 
		//				+ getHoleID() + delimiter
		//				+ getPinDiamater() + delimiter
		//				+ getPinType() + delimiter
		//				+ vrsiPostfix;

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
		//		setVRSIcommand("Scan_Fill");
		//		setHoleID(holeID);
		//		setPinDiamater(pinDia);
		//		setPinType(pinType);

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
		//		setVRSIcommand("Scan_Fill");
		//		setHoleID("");
		//		setPinDiamater(0.000);		
		//		setPinType(0);
		//		
		//		return vrsiPrefix + delimiter
		//				+ getVrsiCommand() + delimiter 
		//				+ getHoleID() + delimiter
		//				+ getPinDiamater() + delimiter
		//				+ getPinType() + delimiter
		//				+ vrsiPostfix;

	}

	/**
	 * Once we receive correct feedback from VRSI we want to evaluate actual numbers (to avoid robot reposition crashes)
	 * @param cmd - command that was send to VRSI  
	 * @param dataString - data String received from VRSI
	 * @return
	 */
	public boolean processScanData(EVRSIscanFastener cmd, List<String> dataString) {
		boolean bResult = false;
		switch (cmd) {
		case ScanEmptyFastenerCmd: case ScanEmptyFastenerComplete:
			VRSIemptyFastener emptyFastener = new VRSIemptyFastener(	
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
			switch (cmd) {
			case ScanEmptyFastenerCmd:
				//ACK from VRSI should be all 0.000
				if (allZeros(cmd, dataString)) {
					emptyFastener.toString();
				}
				break;
			case ScanEmptyFastenerComplete:
				//Usefull data from VRSI

				//TO DO
				//Bot reposition data

				break;
			default:
				break;
			}

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
			} catch (Exception e) {
				System.err.println(e);
				return bResult;
			}
			switch (cmd) {
			case ScanFillFastenerCmd:
				//ACK from VRSI should be all 0.000
				if (allZeros(cmd, dataString)) {
					fillFastener.toString();
				}
				break;
			case ScanFillFastenerComplete:
				//Usefull data from VRSI

				//TO DO

				break;

			default:
				break;
			}
			break;

		default:
			break;
		}

		System.err.println("Data evaluation failure");
		return bResult;
	}

	private boolean allZeros(EVRSIscanFastener cmd, List<String> dataString) {
		boolean bNotZero = false;
		for(String vrsiData : dataString) {
			if (Double.parseDouble(vrsiData) != 0.000) bNotZero = true; 
		}
		if (bNotZero) {
			System.err.println("Command response " + cmd + " unexpected data");
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


	public int getHeartBeatIn() {
		return heartBeatIn;
	}
	public void setHeartBeatIn(int heartBeatIn) {
		this.heartBeatIn = heartBeatIn;
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

}
