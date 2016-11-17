package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.Array;
import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;
import at.siemens.ct.jmz.expressions.set.SetVariable;

/**
 * An array of sets of integers.
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntegerSetArray extends Array<Integer, Set<Integer>> {

	protected IntegerSetArray(List<? extends SetExpression<Integer>> range, TypeInst<Integer, Set<Integer>> innerTypeInst,
			ArrayExpression<Set<Integer>> values) {
		super(range, innerTypeInst, values);
	}

	protected IntegerSetArray(List<? extends SetExpression<Integer>> range, TypeInst<Integer, Set<Integer>> innerTypeInst,
			List<? extends Expression<Set<Integer>>> values) {
		super(range, innerTypeInst,
				values == null ? null : new ArrayLiteral<Integer, Set<Integer>>(range, innerTypeInst.getType(), values));
	}

	private IntegerSetArray(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			List<? extends Expression<Set<Integer>>> values, boolean variable) {
		this(ListUtils.fromElements(range), createInnerTypeInst(name, type, variable), values);
	}

	public static IntegerSetArray createVariable(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			List<? extends Expression<Set<Integer>>> values) {
		return new IntegerSetArray(name, range, type, values, true);
	}

	public static IntegerSetArray createConstant(String name, SetExpression<Integer> range, SetExpression<Integer> type,
			List<? extends Expression<Set<Integer>>> values) {
		return new IntegerSetArray(name, range, type, values, false);
	}

	public static IntegerSetArray createVariable(String name, SetExpression<Integer> range, SetExpression<Integer> type) {
		return createVariable(name, range, type, null);
	}

	public static IntegerSetArray createConstant(String name, SetExpression<Integer> range, SetExpression<Integer> type) {
		return createConstant(name, range, type, null);
	}

	private static TypeInst<Integer, Set<Integer>> createInnerTypeInst(String name,
			SetExpression<Integer> type, boolean variable) {
		if (variable) {
			return new SetVariable<>(new BasicTypeInst<>(name, type));
		} else {
			return new at.siemens.ct.jmz.elements.Set<>(name, type);
		}
	}

	@Override
	protected Function<? super String, Set<Integer>> getElementParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IntFunction<Set<Integer>[]> getArrayGenerator() {
		return size -> new Set[size];
	}

}
