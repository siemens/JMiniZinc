package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class BasicInteger extends BasicTypeInst<Integer> implements IntegerExpression {

  public BasicInteger(BasicTypeInst<Integer> innerTypeInst) {
    super(innerTypeInst);
  }

  public BasicInteger(String name, SetExpression<Integer> type) {
    super(name, type);
  }

  public BasicInteger(String name, SetExpression<Integer> type, Expression<Integer> value) {
    super(name, type, value);
  }

	@Override
	public Boolean isGreaterThanOrEqualTo(int value) {
		Expression<Integer> expression = getValue();
		if (expression instanceof IntegerExpression) {
			return ((IntegerExpression) expression).isGreaterThanOrEqualTo(value);
		}
		return null;
	}

	@Override
	public Boolean isLessThanOrEqualTo(int value) {
		Expression<Integer> expression = getValue();
		if (expression instanceof IntegerExpression) {
			return ((IntegerExpression) expression).isLessThanOrEqualTo(value);
		}
		return null;
	}

}
