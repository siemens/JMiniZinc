package at.siemens.ct.jmz.expressions.set;

/**
 * @author � Siemens AG, 2016
 */
public class Union extends BinarySetOperation {

  private static final String OPERATOR_SYMBOL = "union";

	public Union(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Boolean contains(Integer value) {
		return operand1.contains(value) || operand2.contains(value);
	}

  @Override
  protected String getOperatorSymbol() {
    return OPERATOR_SYMBOL;
  }

}
