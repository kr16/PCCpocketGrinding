package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>CognexProfiOUT</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Cognex describe how the In-Sight sensors ProfiNET capabilities are configured
 */
@Singleton
public class CognexProfiOUTIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'CognexProfiOUT'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'CognexProfiOUT'
	 */
	@Inject
	public CognexProfiOUTIOGroup(Controller controller)
	{
		super(controller, "CognexProfiOUT");

		addDigitalOutput("AcquisutionCtrlReg0", IOTypes.BOOLEAN, 1);
		addDigitalOutput("AcquisutionCtrlReg1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("AcquisutionCtrlReg7", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("InspectionCtrlReg0", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("InspectionCtrlReg1", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SetCurrentJobID", IOTypes.UNSIGNED_INTEGER, 8);
		addMockedDigitalOutput("SoftEventCtrlModule0", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule1", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule2", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule3", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule4", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule5", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule6", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("SoftEventCtrlModule7", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcquisutionCtrlReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AcquisutionCtrlReg0'
	 */
	public boolean getAcquisutionCtrlReg0()
	{
		return getBooleanIOValue("AcquisutionCtrlReg0", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcquisutionCtrlReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AcquisutionCtrlReg0'
	 */
	public void setAcquisutionCtrlReg0(java.lang.Boolean value)
	{
		setDigitalOutput("AcquisutionCtrlReg0", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcquisutionCtrlReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AcquisutionCtrlReg1'
	 */
	public boolean getAcquisutionCtrlReg1()
	{
		return getBooleanIOValue("AcquisutionCtrlReg1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcquisutionCtrlReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AcquisutionCtrlReg1'
	 */
	public void setAcquisutionCtrlReg1(java.lang.Boolean value)
	{
		setDigitalOutput("AcquisutionCtrlReg1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcquisutionCtrlReg7</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AcquisutionCtrlReg7'
	 */
	public boolean getAcquisutionCtrlReg7()
	{
		return getBooleanIOValue("AcquisutionCtrlReg7", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcquisutionCtrlReg7</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AcquisutionCtrlReg7'
	 */
	public void setAcquisutionCtrlReg7(java.lang.Boolean value)
	{
		setDigitalOutput("AcquisutionCtrlReg7", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>InspectionCtrlReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'InspectionCtrlReg0'
	* 
	 * @deprecated The output 'InspectionCtrlReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionCtrlReg0()
	{
		return getBooleanIOValue("InspectionCtrlReg0", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>InspectionCtrlReg0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'InspectionCtrlReg0'
	* 
	 * @deprecated The output 'InspectionCtrlReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setInspectionCtrlReg0(java.lang.Boolean value)
	{
		setDigitalOutput("InspectionCtrlReg0", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>InspectionCtrlReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'InspectionCtrlReg1'
	* 
	 * @deprecated The output 'InspectionCtrlReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getInspectionCtrlReg1()
	{
		return getBooleanIOValue("InspectionCtrlReg1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>InspectionCtrlReg1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'InspectionCtrlReg1'
	* 
	 * @deprecated The output 'InspectionCtrlReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setInspectionCtrlReg1(java.lang.Boolean value)
	{
		setDigitalOutput("InspectionCtrlReg1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SetCurrentJobID</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @return current value of the digital output 'SetCurrentJobID'
	* 
	 * @deprecated The output 'SetCurrentJobID' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public java.lang.Integer getSetCurrentJobID()
	{
		return getNumberIOValue("SetCurrentJobID", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>SetCurrentJobID</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 255]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SetCurrentJobID'
	* 
	 * @deprecated The output 'SetCurrentJobID' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSetCurrentJobID(java.lang.Integer value)
	{
		setDigitalOutput("SetCurrentJobID", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule0'
	* 
	 * @deprecated The output 'SoftEventCtrlModule0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule0()
	{
		return getBooleanIOValue("SoftEventCtrlModule0", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule0</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule0'
	* 
	 * @deprecated The output 'SoftEventCtrlModule0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule0(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule0", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule1'
	* 
	 * @deprecated The output 'SoftEventCtrlModule1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule1()
	{
		return getBooleanIOValue("SoftEventCtrlModule1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule1'
	* 
	 * @deprecated The output 'SoftEventCtrlModule1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule1(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule2'
	* 
	 * @deprecated The output 'SoftEventCtrlModule2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule2()
	{
		return getBooleanIOValue("SoftEventCtrlModule2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule2'
	* 
	 * @deprecated The output 'SoftEventCtrlModule2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule2(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule3'
	* 
	 * @deprecated The output 'SoftEventCtrlModule3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule3()
	{
		return getBooleanIOValue("SoftEventCtrlModule3", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule3'
	* 
	 * @deprecated The output 'SoftEventCtrlModule3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule3(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule3", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule4'
	* 
	 * @deprecated The output 'SoftEventCtrlModule4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule4()
	{
		return getBooleanIOValue("SoftEventCtrlModule4", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule4'
	* 
	 * @deprecated The output 'SoftEventCtrlModule4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule4(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule4", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule5</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule5'
	* 
	 * @deprecated The output 'SoftEventCtrlModule5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule5()
	{
		return getBooleanIOValue("SoftEventCtrlModule5", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule5</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule5'
	* 
	 * @deprecated The output 'SoftEventCtrlModule5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule5(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule5", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule6</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule6'
	* 
	 * @deprecated The output 'SoftEventCtrlModule6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule6()
	{
		return getBooleanIOValue("SoftEventCtrlModule6", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule6</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule6'
	* 
	 * @deprecated The output 'SoftEventCtrlModule6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule6(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule6", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SoftEventCtrlModule7</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SoftEventCtrlModule7'
	* 
	 * @deprecated The output 'SoftEventCtrlModule7' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getSoftEventCtrlModule7()
	{
		return getBooleanIOValue("SoftEventCtrlModule7", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SoftEventCtrlModule7</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SoftEventCtrlModule7'
	* 
	 * @deprecated The output 'SoftEventCtrlModule7' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setSoftEventCtrlModule7(java.lang.Boolean value)
	{
		setDigitalOutput("SoftEventCtrlModule7", value);
	}

}
