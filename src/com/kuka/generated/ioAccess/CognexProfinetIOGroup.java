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

}
