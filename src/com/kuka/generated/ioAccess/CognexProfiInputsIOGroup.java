package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>CognexProfiInputs</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Cognex describe how the In-Sight sensors ProfiNET capabilities are configured
 */
@Singleton
public class CognexProfiInputsIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'CognexProfiInputs'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'CognexProfiInputs'
	 */
	@Inject
	public CognexProfiInputsIOGroup(Controller controller)
	{
		super(controller, "CognexProfiInputs");

		addInput("AcquisutionStatusReg0", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg1", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg2", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg3", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg4", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg5", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg6", IOTypes.BOOLEAN, 1);
		addInput("AcquisutionStatusReg7", IOTypes.BOOLEAN, 1);
		addMockedInput("InspectionStatusReg1", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionStatusReg2", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionStatusReg3", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionStatusReg4", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionStatusReg5", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionStatusReg6", IOTypes.BOOLEAN, 1, false);
		addMockedInput("InspectionCount", IOTypes.UNSIGNED_INTEGER, 16, 0);
		addMockedInput("InspectionStatusReg0", IOTypes.BOOLEAN, 1, false);
		addMockedInput("CurrentJobID", IOTypes.UNSIGNED_INTEGER, 8, 0);
		addMockedInput("ResultCode", IOTypes.INTEGER, 16, 0);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg0'
	 */
	public boolean getAcquisutionStatusReg0()
	{
		return getBooleanIOValue("AcquisutionStatusReg0", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg1'
	 */
	public boolean getAcquisutionStatusReg1()
	{
		return getBooleanIOValue("AcquisutionStatusReg1", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg2'
	 */
	public boolean getAcquisutionStatusReg2()
	{
		return getBooleanIOValue("AcquisutionStatusReg2", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg3'
	 */
	public boolean getAcquisutionStatusReg3()
	{
		return getBooleanIOValue("AcquisutionStatusReg3", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg4'
	 */
	public boolean getAcquisutionStatusReg4()
	{
		return getBooleanIOValue("AcquisutionStatusReg4", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg5</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg5'
	 */
	public boolean getAcquisutionStatusReg5()
	{
		return getBooleanIOValue("AcquisutionStatusReg5", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg6</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg6'
	 */
	public boolean getAcquisutionStatusReg6()
	{
		return getBooleanIOValue("AcquisutionStatusReg6", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcquisutionStatusReg7</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AcquisutionStatusReg7'
	 */
	public boolean getAcquisutionStatusReg7()
	{
		return getBooleanIOValue("AcquisutionStatusReg7", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg1'
	* 
	 * @deprecated The output 'InspectionStatusReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg1()
	{
		return getBooleanIOValue("InspectionStatusReg1", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg1'
	* 
	 * @deprecated The output 'InspectionStatusReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg1Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg1", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg2'
	* 
	 * @deprecated The output 'InspectionStatusReg2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg2()
	{
		return getBooleanIOValue("InspectionStatusReg2", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg2'
	* 
	 * @deprecated The output 'InspectionStatusReg2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg2Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg2", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg3'
	* 
	 * @deprecated The output 'InspectionStatusReg3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg3()
	{
		return getBooleanIOValue("InspectionStatusReg3", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg3'
	* 
	 * @deprecated The output 'InspectionStatusReg3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg3Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg3", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg4'
	* 
	 * @deprecated The output 'InspectionStatusReg4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg4()
	{
		return getBooleanIOValue("InspectionStatusReg4", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg4'
	* 
	 * @deprecated The output 'InspectionStatusReg4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg4Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg4", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg5</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg5'
	* 
	 * @deprecated The output 'InspectionStatusReg5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg5()
	{
		return getBooleanIOValue("InspectionStatusReg5", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg5</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg5'
	* 
	 * @deprecated The output 'InspectionStatusReg5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg5Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg5", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg6</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg6'
	* 
	 * @deprecated The output 'InspectionStatusReg6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg6()
	{
		return getBooleanIOValue("InspectionStatusReg6", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg6</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg6'
	* 
	 * @deprecated The output 'InspectionStatusReg6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg6Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg6", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionCount</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 65535]
	 *
	 * @return current value of the digital input 'InspectionCount'
	* 
	 * @deprecated The output 'InspectionCount' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public java.lang.Integer getInspectionCount()
	{
		return getNumberIOValue("InspectionCount", false).intValue();
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionCount</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 65535]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionCount'
	* 
	 * @deprecated The output 'InspectionCount' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionCountValue(java.lang.Integer value)
	{
		setMockedInput("InspectionCount", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InspectionStatusReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InspectionStatusReg0'
	* 
	 * @deprecated The output 'InspectionStatusReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionStatusReg0()
	{
		return getBooleanIOValue("InspectionStatusReg0", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>InspectionStatusReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'InspectionStatusReg0'
	* 
	 * @deprecated The output 'InspectionStatusReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedInspectionStatusReg0Value(java.lang.Boolean value)
	{
		setMockedInput("InspectionStatusReg0", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>CurrentJobID</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital input 'CurrentJobID'
	* 
	 * @deprecated The output 'CurrentJobID' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public java.lang.Integer getCurrentJobID()
	{
		return getNumberIOValue("CurrentJobID", false).intValue();
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>CurrentJobID</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'CurrentJobID'
	* 
	 * @deprecated The output 'CurrentJobID' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedCurrentJobIDValue(java.lang.Integer value)
	{
		setMockedInput("CurrentJobID", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>ResultCode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @return current value of the digital input 'ResultCode'
	* 
	 * @deprecated The output 'ResultCode' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public java.lang.Integer getResultCode()
	{
		return getNumberIOValue("ResultCode", false).intValue();
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>ResultCode</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @param value
	 *            the value, which has to be written to the mocked digital input 'ResultCode'
	* 
	 * @deprecated The output 'ResultCode' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedResultCodeValue(java.lang.Integer value)
	{
		setMockedInput("ResultCode", value);
	}

}
