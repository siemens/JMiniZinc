package at.siemens.ct.jmz.expressions.array;

import java.util.List;

import at.siemens.ct.jmz.expressions.set.SetExpression;

public class ArrayConcatenation<T> implements ArrayExpression<T> {

	private ArrayExpression<T> left;
	private ArrayExpression<T> right;

	public ArrayConcatenation(ArrayExpression<T> left, ArrayExpression<T> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String use1d() {
		return String.format("%s ++ %s", left.use(), right.use());
	}

	@Override
	public String use() {
		return use1d();
	}

}
