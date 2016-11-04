package at.siemens.ct.jmz.expressions.set;

import java.util.regex.Pattern;

/**
 * TODO: unit tests (for all subclasses)
 * @author z003ft4a
 *
 */
public abstract class BinarySetOperation implements IntegerSetExpression {

	protected SetExpression<Integer> operand1, operand2;

	protected BinarySetOperation(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	public String use() {
    return String.format("%s %s %s", operand1, getOperatorSymbol(), operand2);
	}

  protected abstract String getOperatorSymbol();

  @Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

}
