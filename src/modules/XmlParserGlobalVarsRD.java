package modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modules.GlobalVars;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.omg.CORBA.INTERNAL;

public class XmlParserGlobalVarsRD {
//	String pathName = "d:/Transfer/UserXMLs/";
//	String pathName2 = "//172.31.1.168/d/Transfer/UserXMLs/";
//	String xmlName = "GlobalVarsHotDot.xml";
	String fileName;
	

	private File file; 			//File handler
	private SAXBuilder builder;						//JDOM uses SAX as under layer
	private Document doc;							//New document (must be from JDOM library)
	private Element root;							//root element of XML	
	private XMLOutputter xmlOutput;					//write file to disc

	//XML Elements and attribute names
	public enum EVarTypes {INTEGER, STRING, BOOLEAN, DOUBLE, LONG};
	public static final String 
		ITEM = "item", 
		NAME = "name",
		VALUE = "value";	
	
	
	public XmlParserGlobalVarsRD(String path, String filename) {
		fileName = path + filename; 
		this.XmlParserInit();
	}
	
	public String getVarString(String name){
		return getVarFromData(name).getValue();
	}
	public boolean getVarBoolean(String name) {
		return Boolean.parseBoolean(getVarFromData(name).getValue());
	}
	public int getVarInteger(String name) {
		 return Integer.parseInt(getVarFromData(name).getValue()); 
	}
	public double getVarDouble(String name) {
		return Double.parseDouble(getVarFromData(name).getValue());
	}
	public long getVarLong(String name) {
		return Long.parseLong(getVarFromData(name).getValue());
	}
	
	public void setVar(String name, String value) {
		GlobalVars var = getVarFromData(name);
		if(var != null) {
			var.setValue(value);
			setData(var);
		} else {
			throw new ArithmeticException("No variable: " + name);
		}
	}
	
	/**
	 * @param name name of new variable (string)
	 * @param value value of new variable (string)
	 * @param type type of new variable (enum EVarTypes)
	 * 
	 * Method puts new variable in XML file; in case variable already exists new one will be ignored
	 */
	public void addVar(String name, String value, EVarTypes type) {
		List<GlobalVars> data = new ArrayList<GlobalVars>();
		List<Element> allVars = root.getChildren();
		String varType = type.toString().toLowerCase();
		if (this.findVar(name)) {
			System.out.println("Variable :" + name + " already exists in file: " + fileName);
			return;
		}
		for (Element var : allVars){
			if(!(var.getName().equals(varType))) {
				continue;
			} else {
				Element item = new Element(ITEM).setAttribute(NAME, name).setAttribute(VALUE, value);
				var.addContent(item);
				try {
					this.modifyXML();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		throw new ArithmeticException("No type: " + varType + " in XML file");
	}
	
	public List<GlobalVars> getData(){
		List<GlobalVars> data = new ArrayList<GlobalVars>();
		List<Element> allVars = root.getChildren();
		for (Element var : allVars){
			for (Element item : var.getChildren(ITEM)) {
				GlobalVars newItem = new GlobalVars();
				newItem.setType(var.getName());
				newItem.setName(item.getAttributeValue(NAME));
				newItem.setValue(item.getAttributeValue(VALUE));
				data.add(newItem);
			}
		}
		return data;
	}
	
	private void setData(GlobalVars globalVar) {
		List<Element> allVars = root.getChildren();
		for (Element var : allVars){
			for (Element item : var.getChildren(ITEM)) {
				
				if(!(item.getAttributeValue(NAME).equals(globalVar.getName()))){
					continue;
				} else {
					if (var.getName().equals(globalVar.getType())) {
						item.getAttribute(VALUE).setValue(globalVar.getValue());
						try {
							this.modifyXML();
							return;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
					}
				}
				throw new ArithmeticException("No variable: " + globalVar.getName());
			}
		}
	}
	
	private GlobalVars getVarFromData(String name) {
		XmlParserGlobalVarsRD globals = new XmlParserGlobalVarsRD(fileName, "");
		List<GlobalVars> data = globals.getData();
		for (GlobalVars globalVar : data) {
			if(!(globalVar.getName().equals(name))){
				continue;
			} else {
				return globalVar;
			}		
		}
		throw new ArithmeticException("No variable name: " + name + " in file"  + fileName);
	}

	private void modifyXML() throws IOException {
		xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		xmlOutput.output(doc, new FileWriter(fileName));
	}
	
	private void XmlParserInit() {
		file = new File(fileName);
		if(! file.exists() ){
			System.err.println("File: " + fileName +" does not exist!, <XmlParserGlobalVars>");
			return;
		} else {
			if (! ( file.canWrite() && file.canRead())) {
				System.err.println("File: " + fileName +" can't read/write, <XmlParserGlobalVars>");
				return;
			}
		}
		
		builder = new SAXBuilder();
		try {
			doc = builder.build(file);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root = doc.getRootElement();
	}
	
	/**
	 * @param findme
	 * @return 	true if variable name was found and display its value;
	 * 			false if variable was not found;
	 * 			false and message if more than one variable of this name was found
	 */
	public boolean findVar(String findme) {
		boolean bResult = false;
		String findThisVar = "//item[@name='" + findme +"']";
		XPathFactory xfactory = XPathFactory.instance();
		XPathExpression<Element> exp = xfactory.compile(findThisVar, Filters.element());
		List<Element> foundElements = exp.evaluate(doc);
		if (foundElements.size() == 1) {
			Element element = foundElements.get(0);
			String name = element.getAttributeValue("name");
			String value = element.getAttributeValue("value");
			System.out.println("Found variable: " + name + " = " + value);
			bResult = true;
		}
		if (foundElements.size() > 1) {
			for(Element element : foundElements) {
				System.out.println(element.toString());
			}
			System.out.println("Found more than one element, this is probably not good " + ";<XmlParserGlobalVars>");
		}
		return bResult;
	}
}


