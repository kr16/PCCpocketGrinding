package modules;

import java.util.Iterator;

import com.kuka.common.params.IParameter;
import com.kuka.common.params.IParameterSet;

public class RedundancyParams implements IParameterSet{
	private double statusParameter;
	private double turnParameter;
	private double e1Parameter;
	
	public RedundancyParams() {
		//constructor does nothing as I don't know what I am doing
	}
	
	@Override
	public IParameterSet combine(IParameterSet other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IParameter<?>> boolean contains(Class<T> paramType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<IParameter<?>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IParameter<?>> T optionalParameter(Class<T> paramType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V, W extends V, T extends IParameter<V>> V optionalValue(
			Class<T> paramType, W defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int paramCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends IParameter<?>> T requiredParameter(Class<T> paramType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V, T extends IParameter<V>> V requiredValue(Class<T> paramType) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getStatusParameter() {
		return statusParameter;
	}

	public void setStatusParameter(double statusParameter) {
		this.statusParameter = statusParameter;
	}

	public double getTurnParameter() {
		return turnParameter;
	}

	public void setTurnParameter(double turnParameter) {
		this.turnParameter = turnParameter;
	}

	public double getE1Parameter() {
		return e1Parameter;
	}

	public void setE1Parameter(double e1Parameter) {
		this.e1Parameter = e1Parameter;
	}

}
