package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>CognexProfinetIN</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Cognex describe how the In-Sight sensors ProfiNET capabilities are configured
 */
@Singleton
public class CognexProfinetINIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'CognexProfinetIN'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'CognexProfinetIN'
	 */
	@Inject
	public CognexProfinetINIOGroup(Controller controller)
	{
		super(controller, "CognexProfinetIN");

		addMockedInput("AcquisutionStatusReg0", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg1", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg2", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg3", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg4", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg5", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg6", IOTypes.BOOLEAN, 1, false);
		addMockedInput("AcquisutionStatusReg7", IOTypes.BOOLEAN, 1, false);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg0()
	{
		return getBooleanIOValue("AcquisutionStatusReg0", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg0</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg0'
	* 
	 * @deprecated The output 'AcquisutionStatusReg0' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg0Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg0", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg1()
	{
		return getBooleanIOValue("AcquisutionStatusReg1", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg1</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg1'
	* 
	 * @deprecated The output 'AcquisutionStatusReg1' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg1Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg1", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg2()
	{
		return getBooleanIOValue("AcquisutionStatusReg2", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg2</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg2'
	* 
	 * @deprecated The output 'AcquisutionStatusReg2' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg2Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg2", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg3()
	{
		return getBooleanIOValue("AcquisutionStatusReg3", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg3</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg3'
	* 
	 * @deprecated The output 'AcquisutionStatusReg3' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg3Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg3", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg4()
	{
		return getBooleanIOValue("AcquisutionStatusReg4", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg4</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg4'
	* 
	 * @deprecated The output 'AcquisutionStatusReg4' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg4Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg4", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg5()
	{
		return getBooleanIOValue("AcquisutionStatusReg5", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg5</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg5'
	* 
	 * @deprecated The output 'AcquisutionStatusReg5' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg5Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg5", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg6()
	{
		return getBooleanIOValue("AcquisutionStatusReg6", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg6</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg6'
	* 
	 * @deprecated The output 'AcquisutionStatusReg6' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg6Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg6", value);
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
	* 
	 * @deprecated The output 'AcquisutionStatusReg7' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public boolean getAcquisutionStatusReg7()
	{
		return getBooleanIOValue("AcquisutionStatusReg7", false);
	}

	/**
	 * Sets the value of the <b>mocked digital input '<i>AcquisutionStatusReg7</i>'</b>.<br>
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
	 *            the value, which has to be written to the mocked digital input 'AcquisutionStatusReg7'
	* 
	 * @deprecated The output 'AcquisutionStatusReg7' has not been assigned to a field bus address - thus this operation will be <b>simulated</b> only.
	 */
	@Deprecated
	public void setMockedAcquisutionStatusReg7Value(java.lang.Boolean value)
	{
		setMockedInput("AcquisutionStatusReg7", value);
	}

}
