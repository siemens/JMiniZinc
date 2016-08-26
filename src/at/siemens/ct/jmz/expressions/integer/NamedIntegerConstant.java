package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.NamedConstant;

public class NamedIntegerConstant extends NamedConstant<Integer> implements IntegerExpression {

	public NamedIntegerConstant(String name, Expression<Integer> value) {
		super(name, value);
	}

}
