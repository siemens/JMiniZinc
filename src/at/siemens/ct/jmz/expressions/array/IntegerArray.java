package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.Array;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class IntegerArray extends Array<Integer> {

  private static final SetExpression<Integer> TYPE = IntegerSetExpression.INTEGER_UNIVERSE;

  public static IntegerArray createVariable(String name, SetExpression<Integer> range) {
    return createVariable(name, ListUtils.fromElements(range), TYPE);
  }

  public static IntegerArray createVariable(String name, SetExpression<Integer> range,
      SetExpression<Integer> type) {
    return createVariable(name, ListUtils.fromElements(range), type);
  }

  public static IntegerArray createVariable(String name, ArrayExpression<Integer> values) {
    return createVariable(name, values.getRange(), TYPE, values);
  }

  public static IntegerArray createVariable(String name, SetExpression<Integer> type,
      ArrayExpression<Integer> values) {
    return createVariable(name, values.getRange(), type, values);
  }

  public static IntegerArray createVariable(String name,
      List<? extends SetExpression<Integer>> range) {
    return createVariable(name, range, TYPE);
  }

  public static IntegerArray createVariable(String name,
      List<? extends SetExpression<Integer>> range, SetExpression<Integer> type) {
    return createVariable(name, range, type, null);
  }

  private static IntegerArray createVariable(String name,
      List<? extends SetExpression<Integer>> range, SetExpression<Integer> type,
      ArrayExpression<Integer> values) {
    return new IntegerArray(name, range, type, values, true);
  }

  public static IntegerArray createConstant(String name, SetExpression<Integer> range) {
    return createConstant(name, ListUtils.fromElements(range), TYPE);
  }

  public static IntegerArray createConstant(String name, SetExpression<Integer> range,
      SetExpression<Integer> type) {
    return createConstant(name, ListUtils.fromElements(range), type);
  }

  public static IntegerArray createConstant(String name, ArrayExpression<Integer> values) {
    return createConstant(name, values.getRange(), TYPE, values); // TODO: values.getType()
  }

  public static IntegerArray createConstant(String name, SetExpression<Integer> type,
      ArrayExpression<Integer> values) {
    return createConstant(name, values.getRange(), type, values);
  }

  public static IntegerArray createConstant(String name,
      List<? extends SetExpression<Integer>> range) {
    return createConstant(name, range, TYPE);
  }

  public static IntegerArray createConstant(String name,
      List<? extends SetExpression<Integer>> range, SetExpression<Integer> type) {
    return createConstant(name, range, type, null);
  }

  private static IntegerArray createConstant(String name,
      List<? extends SetExpression<Integer>> range,
      SetExpression<Integer> type, ArrayExpression<Integer> values) {
    return new IntegerArray(name, range, type, values, false);
  }

  private IntegerArray(String name, List<? extends SetExpression<Integer>> range,
      SetExpression<Integer> type, ArrayExpression<Integer> values, boolean variable) {
    super(range, createInnerTypeInst(name, type, variable), values);
	}

  private static TypeInst<Integer, Integer> createInnerTypeInst(String name,
      SetExpression<Integer> type, boolean variable) {
    if (variable) {
      return new IntegerVariable(name, type);
    } else {
      return new BasicTypeInst<Integer>(name, type);
    }
  }

	@Override
	protected Function<? super String, Integer> getElementParser() {
		return Integer::valueOf;
	}

  @Override
  protected IntFunction<Integer[]> getArrayGenerator() {
    return size -> new Integer[size];
  }

}
