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

}
