package modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modules.Common.EHotDotCouponStates;
import modules.XMLObjects.HotDotCouponItem;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLParserCoupon {

	private int couponID;
	private File file;
	private SAXBuilder builder;
	private Document doc;
	private Element root;
	private Element coupon;
	private XMLOutputter xmlOutput;
	private String filename;
	
	//XML Elements and attribute names
	public static final String
	COUPON = "coupon",
	COUPONIDATTR = "id",
	COUPONNAME = "couponName",
	ROWNUMBERATTR = "number",
	ROWSTATUS = "status",
	ROW = "row";	

	/**
	 * @param couponID - integer number from XML formated correctly <coupon id="1">
	 * @param pathAndfilename - path and file name where file resides
	 * @see example: coupon1 = new CouponXMLparser(1, "d:/Transfer/UserXMLs/CouponHotDot01.xml");
	 */
	@SuppressWarnings("null")
	public XMLParserCoupon(int couponID, String pathAndfilename) {
		filename = pathAndfilename;
		this.setCouponID(couponID);
		this.CouponXMLparserInit();
		List<HotDotCouponItem> locations = new ArrayList<XMLObjects.HotDotCouponItem>();
		for (int i = 1; i <= getRowCount(); i++) {
			for (int j = 1; j <= getColumnCount(); j++) {
			  HotDotCouponItem location = new XMLObjects().new HotDotCouponItem();
			  HashMap<String, Integer> position = new HashMap<String, Integer>();
			  position.put("row", i);
			  position.put("column", j);
			  location.setPosition(position);
			  HashMap<EHotDotCouponStates, Boolean> state = new HashMap<Common.EHotDotCouponStates, Boolean>();
			  state.put(getRowColumnValue(i, j), true);
			  location.setStatus(state);
			  locations.add(location);
			}
		}
//		for (HotDotCouponItem hotDotCouponItem : locations) {
//			System.out.println(hotDotCouponItem);
//		}
	}
	
	public void CouponXMLparserInit() {
		boolean bSuccess = false;
		file = new File(filename);
		builder = new SAXBuilder();
		
			try {
				doc = builder.build(file);
			} catch (JDOMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ArithmeticException("JDOME exception, XMLParserCoupon");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ArithmeticException("THERE IS NO FILE LIKE THAT: " + filename);
			}			 
		
			
		root = doc.getRootElement();
		List<Element> coupons = root.getChildren(COUPON);
		for(Element couponTemp : coupons) {
			try {
				if (couponTemp.getAttribute(COUPONIDATTR).getIntValue() == couponID) {
					bSuccess = true;
					this.coupon = couponTemp;
				}
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!bSuccess){
			System.out.println("No coupon ID: " + couponID + " in file: " + filename);
		}
	}
	
	/**
	 * Set status of process position
	 * @param row - row number on coupon
	 * @param column - column number on coupon
	 * @param value - true(processed), false(not processed)
	 * @throws DataConversionException
	 */
	public void setRowColumnValue(int row, int column, EHotDotCouponStates state) {
		int value = this.hotDotCouponStateToInt(state);
		this.setRowColumnValue(row, column, value);
	}

	private void setRowColumnValue(int row, int column, int value) {
		Element status = getRowElement(row); 
		StringBuilder newColumn = new StringBuilder(getColumnValues(row, column));
		newColumn.setCharAt((column-1), Character.forDigit(value, 10));	//Java counts from 0 and no conversion from booleans
		status.setText(newColumn.toString());		
		try {
			modifyXML();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param row
	 * @param column
	 * @return status of coupon processed position
	 */
	private int getRowColumnValueInt(int row, int column) {
		String newColumn = getColumnValues(row, column);
		return Character.getNumericValue(newColumn.charAt(column-1));
	}
	
	public EHotDotCouponStates getRowColumnValue(int row, int column) {
		int value = this.getRowColumnValueInt(row, column);
		return intToHotDotCouponState(value);
	}
	
	/**
	 * Reset entire coupon to not processed
	 */
	public void resetCoupon() {
		for (int i = 1; i <= getRowCount(); i++) {
			for (int j = 1; j <= getColumnCount(); j++) {
				setRowColumnValue(i, j, 0);
			}
		}
	}
	
	public void setCouponName(String couponName) throws DataConversionException {
		boolean bSuccess = false;
		List<Element> coupons = root.getChildren(COUPON);
		for(Element coupon : coupons) {
			if (coupon.getAttribute("id").getIntValue() == couponID) {
				bSuccess = true;
				coupon.getChild(COUPONNAME).setText(couponName);
				try {
					this.modifyXML();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (!bSuccess){
			System.out.println("No coupon ID: " + couponID + " in file: " + filename);
		}
	}
	
	/**
	 * Display entire coupon in human readable format
	 */
	public void displayValues() {
		int status;
		for (int i = 1; i <= getRowCount(); i++) {
			for (int j = 1; j <= getColumnCount(); j++) {
				status = getRowColumnValueInt(i, j);
				System.out.print(status);
				if(j == getColumnCount()) {
					System.out.println();
				} 
			}
		}
	}
	/**
	 * Return "row, column" position of first not processed state (EHotDotCouponStates)
	 * or null if there is none 
	 * @param state
	 * @return Map object with two keys(String): row, column
	 */
	public Map getFirstNotProcessed (EHotDotCouponStates state) {
		Map position = new HashMap();
		for (int i = 1; i <= getRowCount(); i++) {
			for (int j = 1; j <= getColumnCount(); j++) {	
				if (getRowColumnValue(i, j).equals(state)) {
					position.put("row", new Integer(i));
					position.put("column", new Integer(j));
					return position;
				}
			}
		}
		System.err.println("No state value: " + state + " in filename: " + filename + " coupon id: " + couponID);
		return null;
	}
	
	/**
	 * Method attempts to modify actual file on storage device
	 * @throws IOException
	 */
	private void modifyXML() throws IOException {
		xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(filename));
	}
	
	/**
	 * @param row
	 * @return based on row number function returns <status> element
	 * @throws DataConversionException
	 */
	private Element getRowElement(int row)  {
		Element element = new Element(ROWSTATUS);
		if (!((row > 0) && (row <= this.coupon.getChildren(ROW).size()))) {
			throw new ArithmeticException("Wrong row number: " + row);
		}
		for (Element rowXML : this.coupon.getChildren(ROW)) {
			try {
				if (rowXML.getAttribute(ROWNUMBERATTR).getIntValue() == row){
						element = rowXML.getChild(ROWSTATUS);
				}
			} catch (DataConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return element;
	}
	
	private String getColumnValues(int row, int column) {
		Element status = getRowElement(row); 
		if (! (column > 0 && column <= status.getText().length() )){
			throw new ArithmeticException("Wrong column number: " + column);
		}
		return status.getText();		
	}
	
	private char boolToChar(boolean value){
		char character = ' ';
		if(value) character = '1';
		if(!value) character = '0';
		return character;
	}
	
	private boolean charToBoolean(char value){
		if (value == '1') return true;
		if (value == '0') return false;
		throw new ArithmeticException("Wrong character value, only 0 and 1 allowed");
	}
	
	public Common.EHotDotCouponStates intToHotDotCouponState(int value) {
		Common.EHotDotCouponStates state;
		switch (value) {
		case 0:
			state = EHotDotCouponStates.Empty;
			break;
		case 1:
			state = EHotDotCouponStates.Smudged;
			break;
		case 2:
			state = EHotDotCouponStates.Skived;
			break;
		case 3:
			state = EHotDotCouponStates.Scaned;
			break;
		case 4:
			state = EHotDotCouponStates.Skip;
			break;
		case 5:
			state = EHotDotCouponStates.Error;
			break;
		default:
			System.err.println("No state for value: " + value);
			throw new ArithmeticException(); 
		}
		return state;
	}
	
	public int hotDotCouponStateToInt (EHotDotCouponStates state) {
		int value;
		switch (state) {
		case Empty:
			value = 0;
			break;
		case Smudged:
			value = 1;
			break;
		case Skived:
			value = 2;
			break;
		case Scaned:
			value = 3;
			break;
		case Skip:
			value = 4;
			break;
		case Error:
			value = 5;
		default:
			System.err.println("No value for state: " + state);
			throw new ArithmeticException(); 
			
		}
		return value;
	}
	
	public int getRowCount() {
		return this.coupon.getChildren(ROW).size();
	}
	
	public int getColumnCount () {
		return this.coupon.getChild(ROW).getChild(ROWSTATUS).getText().length();
	}

	public int getCouponID() {
		return couponID;
	}

	public void setCouponID(int couponID) {
		this.couponID = couponID;
	}
	
	//XML doc as pure string to console///////
	//Nice debug/overview 				//////
	//XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	//String xmlString = outputter.outputString(doc);
	//System.out.println(xmlString);
	/////////////////////////////////////////
	
	 	//must be a JDOM Element!!! this gives us root element
	

}
