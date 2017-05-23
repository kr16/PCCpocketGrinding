package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>SMC600_SPN1_4valvesonly</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * SMC600 with 8 digital inputs and 4 "big" valves outputs
 */
@Singleton
public class SMC600_SPN1_4valvesonlyIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'SMC600_SPN1_4valvesonly'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'SMC600_SPN1_4valvesonly'
	 */
	@Inject
	public SMC600_SPN1_4valvesonlyIOGroup(Controller controller)
	{
		super(controller, "SMC600_SPN1_4valvesonly");

		addInput("SMC_DI01", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI02", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI03", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI04", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI05", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI06", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI07", IOTypes.BOOLEAN, 1);
		addInput("SMC_DI08", IOTypes.BOOLEAN, 1);
		addDigitalOutput("SMC_DO01A_GrinderValve", IOTypes.BOOLEAN, 1);
		addDigitalOutput("SMC_DO01B", IOTypes.BOOLEAN, 1);
		addDigitalOutput("SMC_DO02B", IOTypes.BOOLEAN, 1);
		addDigitalOutput("SMC_DO02A", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI01</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI01'
	 */
	public boolean getSMC_DI01()
	{
		return getBooleanIOValue("SMC_DI01", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI02</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI02'
	 */
	public boolean getSMC_DI02()
	{
		return getBooleanIOValue("SMC_DI02", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI03</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI03'
	 */
	public boolean getSMC_DI03()
	{
		return getBooleanIOValue("SMC_DI03", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI04</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI04'
	 */
	public boolean getSMC_DI04()
	{
		return getBooleanIOValue("SMC_DI04", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI05</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI05'
	 */
	public boolean getSMC_DI05()
	{
		return getBooleanIOValue("SMC_DI05", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI06</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI06'
	 */
	public boolean getSMC_DI06()
	{
		return getBooleanIOValue("SMC_DI06", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI07</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI07'
	 */
	public boolean getSMC_DI07()
	{
		return getBooleanIOValue("SMC_DI07", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>SMC_DI08</i>'</b>.<br>
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
	 * @return current value of the digital input 'SMC_DI08'
	 */
	public boolean getSMC_DI08()
	{
		return getBooleanIOValue("SMC_DI08", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SMC_DO01A_GrinderValve</i>'</b>.<br>
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
	 * @return current value of the digital output 'SMC_DO01A_GrinderValve'
	 */
	public boolean getSMC_DO01A_GrinderValve()
	{
		return getBooleanIOValue("SMC_DO01A_GrinderValve", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SMC_DO01A_GrinderValve</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'SMC_DO01A_GrinderValve'
	 */
	public void setSMC_DO01A_GrinderValve(java.lang.Boolean value)
	{
		setDigitalOutput("SMC_DO01A_GrinderValve", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SMC_DO01B</i>'</b>.<br>
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
	 * @return current value of the digital output 'SMC_DO01B'
	 */
	public boolean getSMC_DO01B()
	{
		return getBooleanIOValue("SMC_DO01B", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SMC_DO01B</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'SMC_DO01B'
	 */
	public void setSMC_DO01B(java.lang.Boolean value)
	{
		setDigitalOutput("SMC_DO01B", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SMC_DO02B</i>'</b>.<br>
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
	 * @return current value of the digital output 'SMC_DO02B'
	 */
	public boolean getSMC_DO02B()
	{
		return getBooleanIOValue("SMC_DO02B", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SMC_DO02B</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'SMC_DO02B'
	 */
	public void setSMC_DO02B(java.lang.Boolean value)
	{
		setDigitalOutput("SMC_DO02B", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SMC_DO02A</i>'</b>.<br>
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
	 * @return current value of the digital output 'SMC_DO02A'
	 */
	public boolean getSMC_DO02A()
	{
		return getBooleanIOValue("SMC_DO02A", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SMC_DO02A</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'SMC_DO02A'
	 */
	public void setSMC_DO02A(java.lang.Boolean value)
	{
		setDigitalOutput("SMC_DO02A", value);
	}

}
