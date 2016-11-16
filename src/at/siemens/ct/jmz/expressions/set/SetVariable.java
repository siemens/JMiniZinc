package at.siemens.ct.jmz.expressions.set;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.Variable;

public class SetVariable<T> extends Variable<T, java.util.Set<T>> implements SetExpression<T> {

	public SetVariable(BasicTypeInst<T> innerTypeInst) {
		super(new Set<>(innerTypeInst));
	}

	public SetVariable(Set<T> innerTypeInst) {
		super(innerTypeInst);
	}

	@Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.Set<T> parseValue(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean contains(T value) {
		// TODO Auto-generated method stub
		return null;
	}

}
