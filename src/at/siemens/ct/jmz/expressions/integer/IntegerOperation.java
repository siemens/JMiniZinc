package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Expression;

public class IntegerOperation extends ArithmeticOperation<Integer> implements IntegerExpression {

	public IntegerOperation(Expression<Integer> left, ArithmeticOperator operator, Expression<Integer> right) {
		super(left, operator, right);
	}

	public IntegerOperation(ArithmeticOperation<Integer> arithmeticOperation) {
		super(arithmeticOperation.getLeft(), arithmeticOperation.getOperator(), arithmeticOperation.getRight());
	}

}
