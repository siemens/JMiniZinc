package at.siemens.ct.jmz.expressions.set;

/**
 * TODO: unit tests (for all subclasses)
 * @author © Siemens AG, 2016
 */
public abstract class BinarySetOperation implements IntegerSetExpression {

	protected SetExpression<Integer> operand1, operand2;

	protected BinarySetOperation(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	public String use() {
    return String.format("%s %s %s", operand1.use(), getOperatorSymbol(), operand2.use());
	}

  protected abstract String getOperatorSymbol();

}
