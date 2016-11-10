package at.siemens.ct.jmz.expressions.set;

/**
 * Represents a MiniZinc option type with an underlying integer set. This means that a variable of this type can either
 * be assigned an element of this set or, optionally, a null value.
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class OptionalIntSet implements IntegerSetExpression {

	private SetExpression<Integer> innerSet; // TODO: or IntegerSubset? (distinguish between constant and variable sets?)

	public OptionalIntSet(SetExpression<Integer> originalSet) {
		if (originalSet instanceof OptionalIntSet)
			throw new IllegalArgumentException("Set is already optional: " + originalSet);
		this.innerSet = originalSet;
	}

	@Override
	public Boolean contains(Integer value) {
		if (value == null)
			return true;
		return innerSet.contains(value);
	}

	@Override
	public String use() {
		return "opt " + innerSet.use();
	}

}
