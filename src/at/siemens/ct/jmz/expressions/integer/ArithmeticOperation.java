package at.siemens.ct.jmz.expressions.integer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.Operation;

public class ArithmeticOperation<T extends Number> extends Operation<T, T>
		implements Expression<T> {

  public ArithmeticOperation(Expression<T> left, ArithmeticOperator operator, Expression<T> right) {
    super(left, operator, right);
  }

  public static <T extends Number> ArithmeticOperation<T> plus(Expression<T> left,
      Expression<T> right) {
    return new ArithmeticOperation<T>(left, ArithmeticOperator.PLUS, right);
  }

	@SafeVarargs
	public static <T extends Number> ArithmeticOperation<T> plus(Expression<T>... summands) {
		return plus(Arrays.asList(summands));
	}

	public static <T extends Number> ArithmeticOperation<T> plus(Collection<Expression<T>> summands) {
		return plusOfList(new ArrayList<>(summands));
	}

	private static <T extends Number> ArithmeticOperation<T> plusOfList(List<Expression<T>> summands) {
		if (summands.size() < 2) {
			throw new IllegalArgumentException("At least two summands are needed to creat a sum");
		} else if (summands.size() == 2) {
			return plus(summands.get(0), summands.get(1));
		} else {
			Expression<T> head = summands.remove(0);
			return plus(head, plusOfList(summands));
		}
	}

	public static ArithmeticOperation<Integer> plus(Integer left, Expression<Integer> right) {
    return plus(new IntegerConstant(left), right);
  }

  public static ArithmeticOperation<Integer> plus(Expression<Integer> left, Integer right) {
    return plus(left, new IntegerConstant(right));
  }

  public static <T extends Number> ArithmeticOperation<T> minus(Expression<T> left,
      Expression<T> right) {
    return new ArithmeticOperation<T>(left, ArithmeticOperator.MINUS, right);
  }

  public static ArithmeticOperation<Integer> minus(Integer left, Expression<Integer> right) {
    return minus(new IntegerConstant(left), right);
  }

  public static ArithmeticOperation<Integer> minus(Expression<Integer> left, Integer right) {
    return minus(left, new IntegerConstant(right));
  }

  public static <T extends Number> ArithmeticOperation<T> times(Expression<T> left,
      Expression<T> right) {
    return new ArithmeticOperation<T>(left, ArithmeticOperator.TIMES, right);
  }

  public static ArithmeticOperation<Integer> times(Integer left, Expression<Integer> right) {
    return times(new IntegerConstant(left), right);
  }

  public static ArithmeticOperation<Integer> times(Expression<Integer> left, Integer right) {
    return times(left, new IntegerConstant(right));
  }

  public static <T extends Number> ArithmeticOperation<T> div(Expression<T> left,
      Expression<T> right) {
    return new ArithmeticOperation<T>(left, ArithmeticOperator.DIV_INT, right);
  }

  public static ArithmeticOperation<Integer> div(Integer left, Expression<Integer> right) {
    return div(new IntegerConstant(left), right);
  }

  public static ArithmeticOperation<Integer> div(Expression<Integer> left, Integer right) {
    return div(left, new IntegerConstant(right));
  }

  public static <T extends Number> ArithmeticOperation<T> modulo(Expression<T> left,
      Expression<T> right) {
    return new ArithmeticOperation<T>(left, ArithmeticOperator.MODULO, right);
  }

  public static ArithmeticOperation<Integer> modulo(Integer left, Expression<Integer> right) {
    return modulo(new IntegerConstant(left), right);
  }

  public static ArithmeticOperation<Integer> modulo(Expression<Integer> left, Integer right) {
    return modulo(left, new IntegerConstant(right));
  }

	@Override
	public ArithmeticOperator getOperator() {
		return (ArithmeticOperator) super.getOperator();
	}

}
