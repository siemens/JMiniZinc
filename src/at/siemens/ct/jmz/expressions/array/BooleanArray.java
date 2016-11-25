package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.Array;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.expressions.bool.BooleanVariable;
import at.siemens.ct.jmz.expressions.set.BooleanSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 */
public class BooleanArray extends Array<Boolean, Boolean> {

  private static final SetExpression<Boolean> TYPE = BooleanSetExpression.BOOLEAN_UNIVERSE;

  public static BooleanArray createVariable(String name, SetExpression<Integer> range) {
    return createVariable(name, ListUtils.fromElements(range), null);
  }

  public static BooleanArray createVariable(String name,
      List<? extends SetExpression<Integer>> range) {
    return createVariable(name, range, null);
  }

  public static BooleanArray createVariable(String name, SetExpression<Integer> range,
      ArrayExpression<Boolean> values) {
    return createVariable(name, ListUtils.fromElements(range), values);
  }

  private static BooleanArray createVariable(String name,
      List<? extends SetExpression<Integer>> range,
      ArrayExpression<Boolean> values) {
    return new BooleanArray(name, range, values, true);
  }

  public static BooleanArray createConstant(String name, SetExpression<Integer> range) {
    return createConstant(name, ListUtils.fromElements(range), null);
  }

  public static BooleanArray createConstant(String name,
      List<? extends SetExpression<Integer>> range) {
    return createConstant(name, range, null);
  }

  public static BooleanArray createConstant(String name, SetExpression<Integer> range,
      ArrayExpression<Boolean> values) {
    return createConstant(name, ListUtils.fromElements(range), values);
  }

  private static BooleanArray createConstant(String name,
      List<? extends SetExpression<Integer>> range,
      ArrayExpression<Boolean> values) {
    return new BooleanArray(name, range, values, false);
  }

  private BooleanArray(String name, List<? extends SetExpression<Integer>> range,
      ArrayExpression<Boolean> values, boolean variable) {
    super(range, createInnerTypeInst(name, variable), values);
	}

  private static TypeInst<Boolean, Boolean> createInnerTypeInst(String name, boolean variable) {
    if (variable) {
      return new BooleanVariable(name);
    } else {
      return new BasicTypeInst<Boolean>(name, TYPE);
    }
  }

	@Override
	protected Function<? super String, Boolean> getElementParser() {
		return Boolean::valueOf;
	}

  @Override
  protected IntFunction<Boolean[]> getArrayGenerator() {
    return size -> new Boolean[size];
  }

}
