package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>TestFrank</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class TestFrankIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'TestFrank'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'TestFrank'
	 */
	@Inject
	public TestFrankIOGroup(Controller controller)
	{
		super(controller, "TestFrank");

		addDigitalOutput("DO01A", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO01B", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO02A", IOTypes.BOOLEAN, 1);
		addDigitalOutput("DO02B", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO01A</i>'</b>.<br>
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
	 * @return current value of the digital output 'DO01A'
	 */
	public boolean getDO01A()
	{
		return getBooleanIOValue("DO01A", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO01A</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'DO01A'
	 */
	public void setDO01A(java.lang.Boolean value)
	{
		setDigitalOutput("DO01A", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO01B</i>'</b>.<br>
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
	 * @return current value of the digital output 'DO01B'
	 */
	public boolean getDO01B()
	{
		return getBooleanIOValue("DO01B", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO01B</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'DO01B'
	 */
	public void setDO01B(java.lang.Boolean value)
	{
		setDigitalOutput("DO01B", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO02A</i>'</b>.<br>
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
	 * @return current value of the digital output 'DO02A'
	 */
	public boolean getDO02A()
	{
		return getBooleanIOValue("DO02A", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO02A</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'DO02A'
	 */
	public void setDO02A(java.lang.Boolean value)
	{
		setDigitalOutput("DO02A", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>DO02B</i>'</b>.<br>
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
	 * @return current value of the digital output 'DO02B'
	 */
	public boolean getDO02B()
	{
		return getBooleanIOValue("DO02B", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>DO02B</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'DO02B'
	 */
	public void setDO02B(java.lang.Boolean value)
	{
		setDigitalOutput("DO02B", value);
	}

}
