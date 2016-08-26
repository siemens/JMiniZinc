package at.siemens.ct.jmz.expressions.set;

public class Union extends BinarySetOperation {

	public Union(SetExpression<Integer> operand1, SetExpression<Integer> operand2) {
		super(operand1, operand2);
	}

	@Override
	public Boolean contains(Integer value) {
		return operand1.contains(value) || operand2.contains(value);
	}

}
