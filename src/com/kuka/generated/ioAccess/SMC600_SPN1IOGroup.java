package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>SMC600_SPN1</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class SMC600_SPN1IOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'SMC600_SPN1'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'SMC600_SPN1'
	 */
	@Inject
	public SMC600_SPN1IOGroup(Controller controller)
	{
		super(controller, "SMC600_SPN1");

		addDigitalOutput("DO01_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO01_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO02_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO03_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO03_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO04_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO04_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO05_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO05_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO06_1", IOTypes.BOOLEAN, 1);
		addInput("DI01_HotDotPresent_X101", IOTypes.BOOLEAN, 1);
		addInput("DI02_HotDotDispencerLow_X102", IOTypes.BOOLEAN, 1);
		addInput("DI03_DispencerAtPlace", IOTypes.BOOLEAN, 1);
		addInput("DI04_DispancerAtHome", IOTypes.BOOLEAN, 1);
		addInput("DI05", IOTypes.BOOLEAN, 1);
		addInput("DI06", IOTypes.BOOLEAN, 1);
		addInput("DI07", IOTypes.BOOLEAN, 1);
		addInput("DI08", IOTypes.BOOLEAN, 1);
		addInput("DI09", IOTypes.BOOLEAN, 1);
		addInput("DI10", IOTypes.BOOLEAN, 1);
		addInput("DI11", IOTypes.BOOLEAN, 1);
		addInput("DI12", IOTypes.BOOLEAN, 1);
		addInput("DI13", IOTypes.BOOLEAN, 1);
		addInput("DI14", IOTypes.BOOLEAN, 1);
		addInput("DI15", IOTypes.BOOLEAN, 1);
		addInput("DI16", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO06_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO07_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO02_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO07_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO08_1_GrinderAir1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO08_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO09_1_GrinderAir2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO09_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO10_1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO10_2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO11_1_PneumaticCylinder", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO11_2", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO01_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO01_1'
	 */
	public boolean getDO01_1()
	{
		return getBooleanIOValue("DO01_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO01_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO01_1'
	 */
	public void setDO01_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO01_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO01_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO01_2'
	 */
	public boolean getDO01_2()
	{
		return getBooleanIOValue("DO01_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO01_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO01_2'
	 */
	public void setDO01_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO01_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO02_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO02_2'
	 */
	public boolean getDO02_2()
	{
		return getBooleanIOValue("DO02_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO02_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO02_2'
	 */
	public void setDO02_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO02_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO03_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO03_1'
	 */
	public boolean getDO03_1()
	{
		return getBooleanIOValue("DO03_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO03_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO03_1'
	 */
	public void setDO03_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO03_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO03_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO03_2'
	 */
	public boolean getDO03_2()
	{
		return getBooleanIOValue("DO03_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO03_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO03_2'
	 */
	public void setDO03_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO03_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO04_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO04_1'
	 */
	public boolean getDO04_1()
	{
		return getBooleanIOValue("DO04_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO04_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO04_1'
	 */
	public void setDO04_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO04_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO04_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO04_2'
	 */
	public boolean getDO04_2()
	{
		return getBooleanIOValue("DO04_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO04_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO04_2'
	 */
	public void setDO04_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO04_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO05_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO05_1'
	 */
	public boolean getDO05_1()
	{
		return getBooleanIOValue("DO05_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO05_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO05_1'
	 */
	public void setDO05_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO05_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO05_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO05_2'
	 */
	public boolean getDO05_2()
	{
		return getBooleanIOValue("DO05_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO05_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO05_2'
	 */
	public void setDO05_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO05_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO06_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO06_1'
	 */
	public boolean getDO06_1()
	{
		return getBooleanIOValue("DO06_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO06_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO06_1'
	 */
	public void setDO06_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO06_1", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI01_HotDotPresent_X101</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Banner sensor
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI01_HotDotPresent_X101'
	 */
	public boolean getDI01_HotDotPresent_X101()
	{
		return getBooleanIOValue("DI01_HotDotPresent_X101", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI02_HotDotDispencerLow_X102</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI02_HotDotDispencerLow_X102'
	 */
	public boolean getDI02_HotDotDispencerLow_X102()
	{
		return getBooleanIOValue("DI02_HotDotDispencerLow_X102", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI03_DispencerAtPlace</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI03_DispencerAtPlace'
	 */
	public boolean getDI03_DispencerAtPlace()
	{
		return getBooleanIOValue("DI03_DispencerAtPlace", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI04_DispancerAtHome</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI04_DispancerAtHome'
	 */
	public boolean getDI04_DispancerAtHome()
	{
		return getBooleanIOValue("DI04_DispancerAtHome", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI05</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI05'
	 */
	public boolean getDI05()
	{
		return getBooleanIOValue("DI05", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI06</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI06'
	 */
	public boolean getDI06()
	{
		return getBooleanIOValue("DI06", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI07</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI07'
	 */
	public boolean getDI07()
	{
		return getBooleanIOValue("DI07", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI08</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI08'
	 */
	public boolean getDI08()
	{
		return getBooleanIOValue("DI08", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI09</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI09'
	 */
	public boolean getDI09()
	{
		return getBooleanIOValue("DI09", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI10</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI10'
	 */
	public boolean getDI10()
	{
		return getBooleanIOValue("DI10", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI11</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI11'
	 */
	public boolean getDI11()
	{
		return getBooleanIOValue("DI11", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI12</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI12'
	 */
	public boolean getDI12()
	{
		return getBooleanIOValue("DI12", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI13</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI13'
	 */
	public boolean getDI13()
	{
		return getBooleanIOValue("DI13", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI14</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI14'
	 */
	public boolean getDI14()
	{
		return getBooleanIOValue("DI14", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI15</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI15'
	 */
	public boolean getDI15()
	{
		return getBooleanIOValue("DI15", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>DI16</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Digital Input
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'DI16'
	 */
	public boolean getDI16()
	{
		return getBooleanIOValue("DI16", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO06_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO06_2'
	 */
	public boolean getDO06_2()
	{
		return getBooleanIOValue("DO06_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO06_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO06_2'
	 */
	public void setDO06_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO06_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO07_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO07_1'
	 */
	public boolean getDO07_1()
	{
		return getBooleanIOValue("DO07_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO07_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO07_1'
	 */
	public void setDO07_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO07_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO02_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO02_1'
	 */
	public boolean getDO02_1()
	{
		return getBooleanIOValue("DO02_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO02_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO02_1'
	 */
	public void setDO02_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO02_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO07_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO07_2'
	 */
	public boolean getDO07_2()
	{
		return getBooleanIOValue("DO07_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO07_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO07_2'
	 */
	public void setDO07_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO07_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO08_1_GrinderAir1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO08_1_GrinderAir1'
	 */
	public boolean getDO08_1_GrinderAir1()
	{
		return getBooleanIOValue("DO08_1_GrinderAir1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO08_1_GrinderAir1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO08_1_GrinderAir1'
	 */
	public void setDO08_1_GrinderAir1(java.lang.Boolean value)
	{
		setDigitalOutput("DO08_1_GrinderAir1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO08_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO08_2'
	 */
	public boolean getDO08_2()
	{
		return getBooleanIOValue("DO08_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO08_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO08_2'
	 */
	public void setDO08_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO08_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO09_1_GrinderAir2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO09_1_GrinderAir2'
	 */
	public boolean getDO09_1_GrinderAir2()
	{
		return getBooleanIOValue("DO09_1_GrinderAir2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO09_1_GrinderAir2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO09_1_GrinderAir2'
	 */
	public void setDO09_1_GrinderAir2(java.lang.Boolean value)
	{
		setDigitalOutput("DO09_1_GrinderAir2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO09_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO09_2'
	 */
	public boolean getDO09_2()
	{
		return getBooleanIOValue("DO09_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO09_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO09_2'
	 */
	public void setDO09_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO09_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO10_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO10_1'
	 */
	public boolean getDO10_1()
	{
		return getBooleanIOValue("DO10_1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO10_1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO10_1'
	 */
	public void setDO10_1(java.lang.Boolean value)
	{
		setDigitalOutput("DO10_1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO10_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO10_2'
	 */
	public boolean getDO10_2()
	{
		return getBooleanIOValue("DO10_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO10_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO10_2'
	 */
	public void setDO10_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO10_2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO11_1_PneumaticCylinder</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO11_1_PneumaticCylinder'
	 */
	public boolean getDO11_1_PneumaticCylinder()
	{
		return getBooleanIOValue("DO11_1_PneumaticCylinder", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO11_1_PneumaticCylinder</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO11_1_PneumaticCylinder'
	 */
	public void setDO11_1_PneumaticCylinder(java.lang.Boolean value)
	{
		setDigitalOutput("DO11_1_PneumaticCylinder", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO11_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DO11_2'
	 */
	public boolean getDO11_2()
	{
		return getBooleanIOValue("DO11_2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO11_2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Air Valve
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DO11_2'
	 */
	public void setDO11_2(java.lang.Boolean value)
	{
		setDigitalOutput("DO11_2", value);
	}

}
