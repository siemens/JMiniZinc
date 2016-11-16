package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArrayLiteral<T, V> implements ArrayExpression<V> {

	public static final String DEFAULT_NULL = "<>";
	public static final char LEFT_BRACKET = '[';
	public static final char RIGHT_BRACKET = ']';

	private List<? extends SetExpression<Integer>> range;
	private SetExpression<T> type;
	private Collection<? extends Expression<V>> values;
	private String nullElement = DEFAULT_NULL;

	public ArrayLiteral(SetExpression<Integer> range, SetExpression<T> type, Collection<? extends Expression<V>> values) {
		this(ListUtils.fromElements(range), type, values);
	}

	public ArrayLiteral(List<? extends SetExpression<Integer>> range, SetExpression<T> type,
			Collection<? extends Expression<V>> values) {
		this.range = range;
		this.type = type;
		this.values = values;
		initNullElement();
	}

	private void initNullElement() {
		//TODO: abstract from int
		if (type instanceof PseudoOptionalIntSet) {
			nullElement = ((PseudoOptionalIntSet) type).getNullElement().use();
		}
	}

	@Override
	public List<? extends SetExpression<Integer>> getRange() {
		return Collections.unmodifiableList(range);
	}

	@Override
  public String useWithOriginalDimensions() {
    return valuesToString();
	}

	private String valuesToString() {
		Function<? super Expression<V>, ? extends String> elementOrNull = i -> (i == null ? nullElement : i.use());
		return LEFT_BRACKET + values.stream().map(elementOrNull).collect(Collectors.joining(", "))
				+ RIGHT_BRACKET;
	}
}
