package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>CognexProfinet</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Cognex describe how the In-Sight sensors ProfiNET capabilities are configured
 */
@Singleton
public class CognexProfinetIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'CognexProfinet'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'CognexProfinet'
	 */
	@Inject
	public CognexProfinetIOGroup(Controller controller)
	{
		super(controller, "CognexProfinet");

		addDigitalOutput("AcqCtrlReg0", IOTypes.BOOLEAN, 1);
		addDigitalOutput("AcqCtrlReg1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("AcqCtrlReg7", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg0", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg1", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg2", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg3", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg4", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg5", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg6", IOTypes.BOOLEAN, 1);
		addInput("AcqStatusReg7", IOTypes.BOOLEAN, 1);
		addInput("AcqCount", IOTypes.UNSIGNED_INTEGER, 16);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcqCtrlReg0</i>'</b>.<br>
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
	 * @return current value of the digital output 'AcqCtrlReg0'
	 */
	public boolean getAcqCtrlReg0()
	{
		return getBooleanIOValue("AcqCtrlReg0", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcqCtrlReg0</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'AcqCtrlReg0'
	 */
	public void setAcqCtrlReg0(java.lang.Boolean value)
	{
		setDigitalOutput("AcqCtrlReg0", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcqCtrlReg1</i>'</b>.<br>
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
	 * @return current value of the digital output 'AcqCtrlReg1'
	 */
	public boolean getAcqCtrlReg1()
	{
		return getBooleanIOValue("AcqCtrlReg1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcqCtrlReg1</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'AcqCtrlReg1'
	 */
	public void setAcqCtrlReg1(java.lang.Boolean value)
	{
		setDigitalOutput("AcqCtrlReg1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AcqCtrlReg7</i>'</b>.<br>
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
	 * @return current value of the digital output 'AcqCtrlReg7'
	 */
	public boolean getAcqCtrlReg7()
	{
		return getBooleanIOValue("AcqCtrlReg7", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>AcqCtrlReg7</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'AcqCtrlReg7'
	 */
	public void setAcqCtrlReg7(java.lang.Boolean value)
	{
		setDigitalOutput("AcqCtrlReg7", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg0</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg0'
	 */
	public boolean getAcqStatusReg0()
	{
		return getBooleanIOValue("AcqStatusReg0", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg1</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg1'
	 */
	public boolean getAcqStatusReg1()
	{
		return getBooleanIOValue("AcqStatusReg1", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg2</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg2'
	 */
	public boolean getAcqStatusReg2()
	{
		return getBooleanIOValue("AcqStatusReg2", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg3</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg3'
	 */
	public boolean getAcqStatusReg3()
	{
		return getBooleanIOValue("AcqStatusReg3", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg4</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg4'
	 */
	public boolean getAcqStatusReg4()
	{
		return getBooleanIOValue("AcqStatusReg4", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg5</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg5'
	 */
	public boolean getAcqStatusReg5()
	{
		return getBooleanIOValue("AcqStatusReg5", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg6</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg6'
	 */
	public boolean getAcqStatusReg6()
	{
		return getBooleanIOValue("AcqStatusReg6", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqStatusReg7</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqStatusReg7'
	 */
	public boolean getAcqStatusReg7()
	{
		return getBooleanIOValue("AcqStatusReg7", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AcqCount</i>'</b>.<br>
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
	 * @return current value of the digital input 'AcqCount'
	 */
	public java.lang.Integer getAcqCount()
	{
		return getNumberIOValue("AcqCount", false).intValue();
	}

}
