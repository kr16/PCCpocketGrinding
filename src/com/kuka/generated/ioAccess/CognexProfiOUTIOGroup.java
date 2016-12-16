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

}
