package at.siemens.ct.jmz.expressions.array;

import java.util.List;

import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author © Siemens AG, 2016
 * 
 * @param <T> The data type of the elements of the array resulting from the concatenation
 */
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
	public String useWithOriginalDimensions() {
		return String.format("%s ++ %s", left.use1d(), right.use1d());
	}

	@Override
	public String use() {
		return useWithOriginalDimensions();
	}

}
