/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import at.siemens.ct.common.utils.ArrayUtils;
import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.Array;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.integer.IntegerExpression;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author Copyright Siemens AG, 2016
 */
public class IntegerArray extends Array<Integer, Integer> {

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

	public static IntegerArray createVariable(String name, List<? extends SetExpression<Integer>> range,
			SetExpression<Integer> type, ArrayExpression<Integer> values) {
    return new IntegerArray(name, range, type, values, true);
  }

	public static IntegerArray createVariable(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			ArrayExpression<Integer> values) {
		return new IntegerArray(name, ListUtils.fromElements(range), type, values, true);
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

	public static IntegerArray createConstant(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			int[] values) {
		return createConstant(name, ListUtils.fromElements(range), type, new ExplicitIntegerList(range, type, values));
	}

  public static IntegerArray createConstant(String name,
      List<? extends SetExpression<Integer>> range) {
    return createConstant(name, range, TYPE);
  }

  public static IntegerArray createConstant(String name,
      List<? extends SetExpression<Integer>> range, SetExpression<Integer> type) {
		return createConstant(name, range, type, (ArrayExpression<Integer>) null);
  }

	public static IntegerArray createConstant(String name,
			List<? extends SetExpression<Integer>> range, SetExpression<Integer> type, Collection<Integer> values) {
		return createConstant(name, range, type, new ExplicitIntegerList(range, type, values));
	}

	public static IntegerArray createConstant(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			Collection<Integer> values) {
		return createConstant(name, ListUtils.fromElements(range), type, new ExplicitIntegerList(range, type, values));
	}

	public static IntegerArray createConstant(String name,
			List<? extends SetExpression<Integer>> range, SetExpression<Integer> type, int[][] values) {
		return createConstant(name, range, type, new ExplicitIntegerList(range, type, values));
	}

	public static IntegerArray createConstant(String name,
			List<? extends SetExpression<Integer>> range, int[][] values) {
		return createConstant(name, range, TYPE, new ExplicitIntegerList(range, values));
	}

	public static IntegerArray createConstant(String name, List<? extends SetExpression<Integer>> range,
			SetExpression<Integer> type, boolean[][] values) {
		return createConstant(name, range, type, ArrayUtils.boolToInt(values));
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

	@Override
	public IntegerArrayAccessExpression access(Expression<Integer> index) {
		return new IntegerArrayAccessExpression(this, index);
	}

	@Override
	public IntegerArrayAccessExpression access(Expression<Integer> index1, Expression<Integer> index2) {
		return new IntegerArrayAccessExpression(this, index1, index2);
	}

	@Override
	public IntegerArrayAccessExpression access(IntegerExpression... indices) {
		return IntegerArrayAccessExpression.accessInteger(this, indices);
	}

}
