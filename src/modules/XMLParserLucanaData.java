package modules;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
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

public class XMLParserLucanaData {

	private int couponID;
	private SAXBuilder builder;
	private Document doc;
	private Element root;
	private Element coupon;
	private XMLOutputter xmlOutput;
	private String filename;
	
	//XML Elements and attribute names
	public static final String
	EXTERNALDATA = "ExternalDatr",
	ERRORCODE = "errorCode",
	HEARTBEAT = "heartbeat",
	DISTANCEX = "dx",
	DISTANCEY = "dy",
	DISTANCEZ = "dz",
	ROTATIONX = "rx",
	ROTATIONY = "ry",
	INNERDIAMETER = "id",
	OUTTERDIAMETER = "od",
	PROCESSTIME = "procTime",
	IMAGENUMBER = "imageNum",
	MATERIAL = "material",
	MEASURE = "measure",
	HOUR = "hour",
	MINUTE = "minute",
	SECOND = "second",
	MONTH = "month",
	DAY = "day",
	YEAR = "year";	
	
	public XMLParserLucanaData() {
		
	}
	
	public void parseXMLDataFromString(String xmlString) {
		builder = new SAXBuilder();
		try {
			doc = builder.build(new StringReader(xmlString));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 			 
			
		root = doc.getRootElement();
		System.out.println(root);
		List<Element> xmlElements = root.getChildren();
		if (xmlElements.size() > 0 ) {
			for (Element xmlElement : xmlElements) {
				System.out.println(xmlElement.getName() + " = " + xmlElement.getValue());
				
			}
		} else {
			System.err.println("Empty string passed?");
			System.out.println(xmlElements.size());
			System.out.println(xmlElements.toString());
		}
	}
	
	private void parseXMLDataFromFile(File filename) {
		boolean bSuccess = false;
		builder = new SAXBuilder();
			try {
				doc = builder.build(this.filename);
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
		System.out.println(root);
		List<Element> coupons = root.getChildren();
		if (coupons.size() > 0 ) {
			
		} else {
			
		}
		System.out.println(coupons.size());
		System.out.println(coupons.toString());
//		for(Element couponTemp : coupons) {
//			try {
//				if (couponTemp.getAttribute(ERRORCODE).getIntValue() == couponID) {
//					bSuccess = true;
//					this.coupon = couponTemp;
//				}
//			} catch (DataConversionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if (!bSuccess){
//			System.out.println("No coupon ID: " + couponID + " in file: " + filename);
//		}
	}
	
	/**
	 * Set status of process position
	 * @param row - row number on coupon
	 * @param column - column number on coupon
	 * @param value - true(processed), false(not processed)
	 * @throws DataConversionException
	 */
	
	
	/**
	 * Method attempts to modify actual file on storage device
	 * @throws IOException
	 */
	private void modifyXML() throws IOException {
		xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(filename));
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
	
	public void displayXMLdataInPrettyFormat (String xmlData) {
		//XML doc as pure string to console///////
		//Nice debug/overview 				//////
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		String xmlString = outputter.outputString(getDoc());
		System.out.println(xmlString);
	}
	
	private Document getDoc() {
		return this.doc;
	}
}
