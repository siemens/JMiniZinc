package at.siemens.ct.jmz.expressions.set;

import java.util.Set;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.NamedConstantSet;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

public interface SetExpression<T> extends Expression<Set<T>> {

	/**
	 * Checks if the given value is contained by this set. If this cannot be determined (e.g. if the set is bounded by
	 * constants whose values are not known), {@code null} is returned.
	 *
	 * @param value
	 * @return
	 */
	Boolean contains(T value);

	Pattern getPattern();

	@Override
	default NamedConstantSet<T> toNamedConstant(String name) {
		return new NamedConstantSet<>(name, this);
	}

	/**
	 * Creates an iterator over this set, using {@code nameOfIterator} as the name of the local variable.
	 *
	 * @param nameOfIterator
	 * @return an iterator over this set
	 */
	default IteratorExpression<T> iterate(String nameOfIterator) {
		return new IteratorExpression<T>(this, nameOfIterator);
	}

}
