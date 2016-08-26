package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;
import at.siemens.ct.jmz.expressions.set.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * TODO: Overlaps with {@link ListComprehension}. Wanted: a beautiful design
 *
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ExplicitList<T> implements ArrayExpression<T> {

	public static final String DEFAULT_NULL = "<>";
	public static final char LEFT_BRACKET = '[';
	public static final char RIGHT_BRACKET = ']';

	private List<? extends SetExpression<Integer>> range;
	private SetExpression<T> type;
	private Collection<T> values;
	private String nullElement = DEFAULT_NULL;

	public ExplicitList(SetExpression<Integer> range, SetExpression<T> type, Collection<T> values) {
		this(ListUtils.fromElements(range), type, values);
	}

	public ExplicitList(List<? extends SetExpression<Integer>> range, SetExpression<T> type, Collection<T> values) {
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
	public String use() {
		return valuesToString();
	}

	private String valuesToString() {
		Function<? super T, ? extends String> elementOrNull = i -> (i == null ? nullElement : i.toString());
		return LEFT_BRACKET + values.stream().map(elementOrNull).collect(Collectors.joining(", "))
				+ RIGHT_BRACKET;
	}

	@Override
	public ArrayConstant<T> toNamedConstant(String name) {
		return new ArrayConstant<>(name, this);
	}
}
