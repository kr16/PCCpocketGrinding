package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;
import com.kuka.roboticsAPI.ioModel.OutputReservedException;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>EK1100</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * 16 Ins and Outs
 */
@Singleton
public class EK1100IOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'EK1100'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'EK1100'
	 */
	@Inject
	public EK1100IOGroup(Controller controller)
	{
		super(controller, "EK1100");

		addInput("EK1100_DI01", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI02", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI04", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI05", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI06", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI08", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI03", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI07", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI09", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI10", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI11", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI12", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI13", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI14", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI15", IOTypes.BOOLEAN, 1);
		addInput("EK1100_DI16", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO01", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO02", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO03", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO04", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO05", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO06", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO07", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO08", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO09", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("EK1100_DO10", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("EK1100_DO11", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("EK1100_DO12", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("EK1100_DO13", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO14", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO15", IOTypes.BOOLEAN, 1);
		addDigitalOutput("EK1100_DO16", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI01</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI01'
	 */
	public boolean getEK1100_DI01()
	{
		return getBooleanIOValue("EK1100_DI01", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI02</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI02'
	 */
	public boolean getEK1100_DI02()
	{
		return getBooleanIOValue("EK1100_DI02", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI04</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI04'
	 */
	public boolean getEK1100_DI04()
	{
		return getBooleanIOValue("EK1100_DI04", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI05</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI05'
	 */
	public boolean getEK1100_DI05()
	{
		return getBooleanIOValue("EK1100_DI05", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI06</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI06'
	 */
	public boolean getEK1100_DI06()
	{
		return getBooleanIOValue("EK1100_DI06", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI08</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI08'
	 */
	public boolean getEK1100_DI08()
	{
		return getBooleanIOValue("EK1100_DI08", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI03</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI03'
	 */
	public boolean getEK1100_DI03()
	{
		return getBooleanIOValue("EK1100_DI03", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI07</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI07'
	 */
	public boolean getEK1100_DI07()
	{
		return getBooleanIOValue("EK1100_DI07", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI09</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI09'
	 */
	public boolean getEK1100_DI09()
	{
		return getBooleanIOValue("EK1100_DI09", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI10</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI10'
	 */
	public boolean getEK1100_DI10()
	{
		return getBooleanIOValue("EK1100_DI10", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI11</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI11'
	 */
	public boolean getEK1100_DI11()
	{
		return getBooleanIOValue("EK1100_DI11", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI12</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI12'
	 */
	public boolean getEK1100_DI12()
	{
		return getBooleanIOValue("EK1100_DI12", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI13</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI13'
	 */
	public boolean getEK1100_DI13()
	{
		return getBooleanIOValue("EK1100_DI13", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI14</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI14'
	 */
	public boolean getEK1100_DI14()
	{
		return getBooleanIOValue("EK1100_DI14", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI15</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI15'
	 */
	public boolean getEK1100_DI15()
	{
		return getBooleanIOValue("EK1100_DI15", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>EK1100_DI16</i>'</b>.<br>
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
	 * @return current value of the digital input 'EK1100_DI16'
	 */
	public boolean getEK1100_DI16()
	{
		return getBooleanIOValue("EK1100_DI16", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO01</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO01'
	 */
	public boolean getEK1100_DO01()
	{
		return getBooleanIOValue("EK1100_DO01", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO01</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO01'
	 */
	public void setEK1100_DO01(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO01", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO02</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO02'
	 */
	public boolean getEK1100_DO02()
	{
		return getBooleanIOValue("EK1100_DO02", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO02</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO02'
	 */
	public void setEK1100_DO02(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO02", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO03</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO03'
	 */
	public boolean getEK1100_DO03()
	{
		return getBooleanIOValue("EK1100_DO03", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO03</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO03'
	 */
	public void setEK1100_DO03(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO03", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO04</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO04'
	 */
	public boolean getEK1100_DO04()
	{
		return getBooleanIOValue("EK1100_DO04", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO04</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO04'
	 */
	public void setEK1100_DO04(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO04", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO05</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO05'
	 */
	public boolean getEK1100_DO05()
	{
		return getBooleanIOValue("EK1100_DO05", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO05</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO05'
	 */
	public void setEK1100_DO05(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO05", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO06</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO06'
	 */
	public boolean getEK1100_DO06()
	{
		return getBooleanIOValue("EK1100_DO06", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO06</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO06'
	 */
	public void setEK1100_DO06(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO06", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO07</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO07'
	 */
	public boolean getEK1100_DO07()
	{
		return getBooleanIOValue("EK1100_DO07", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO07</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO07'
	 */
	public void setEK1100_DO07(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO07", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO08</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO08'
	 */
	public boolean getEK1100_DO08()
	{
		return getBooleanIOValue("EK1100_DO08", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO08</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO08'
	 */
	public void setEK1100_DO08(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO08", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO09</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO09'
	 */
	public boolean getEK1100_DO09()
	{
		return getBooleanIOValue("EK1100_DO09", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO09</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO09'
	 */
	public void setEK1100_DO09(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO09", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO10</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO10'
	* 
	 * @deprecated The output 'EK1100_DO10' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getEK1100_DO10()
	{
		return getBooleanIOValue("EK1100_DO10", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>EK1100_DO10</i>'</b> is currently used as station state output in the Sunrise project properties.
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
	 *            the value, which has to be written to the digital output 'EK1100_DO10'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'EK1100_DO10' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setEK1100_DO10(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'EK1100_DO10' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO11</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO11'
	* 
	 * @deprecated The output 'EK1100_DO11' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getEK1100_DO11()
	{
		return getBooleanIOValue("EK1100_DO11", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>EK1100_DO11</i>'</b> is currently used as station state output in the Sunrise project properties.
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
	 *            the value, which has to be written to the digital output 'EK1100_DO11'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'EK1100_DO11' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setEK1100_DO11(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'EK1100_DO11' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO12</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO12'
	* 
	 * @deprecated The output 'EK1100_DO12' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getEK1100_DO12()
	{
		return getBooleanIOValue("EK1100_DO12", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>EK1100_DO12</i>'</b> is currently used as station state output in the Sunrise project properties.
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
	 *            the value, which has to be written to the digital output 'EK1100_DO12'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'EK1100_DO12' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setEK1100_DO12(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'EK1100_DO12' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO13</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO13'
	* 
	 * @deprecated The output 'EK1100_DO13' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getEK1100_DO13()
	{
		return getBooleanIOValue("EK1100_DO13", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>EK1100_DO13</i>'</b> is currently used as station state output in the Sunrise project properties.
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
	 *            the value, which has to be written to the digital output 'EK1100_DO13'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'EK1100_DO13' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setEK1100_DO13(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'EK1100_DO13' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO14</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO14'
	 */
	public boolean getEK1100_DO14()
	{
		return getBooleanIOValue("EK1100_DO14", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO14</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO14'
	 */
	public void setEK1100_DO14(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO14", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO15</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO15'
	 */
	public boolean getEK1100_DO15()
	{
		return getBooleanIOValue("EK1100_DO15", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO15</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO15'
	 */
	public void setEK1100_DO15(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO15", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>EK1100_DO16</i>'</b>.<br>
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
	 * @return current value of the digital output 'EK1100_DO16'
	 */
	public boolean getEK1100_DO16()
	{
		return getBooleanIOValue("EK1100_DO16", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>EK1100_DO16</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'EK1100_DO16'
	 */
	public void setEK1100_DO16(java.lang.Boolean value)
	{
		setDigitalOutput("EK1100_DO16", value);
	}

}
