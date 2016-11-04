package at.siemens.ct.jmz.expressions.set;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;

public class SetLiteral extends IntegerSubset {

	private Collection<IntegerConstant> elements;

	private SetLiteral(SetExpression<Integer> parent, Collection<IntegerConstant> elements) {
		super(parent);
		this.elements = elements;
	}

	public static SetLiteral subset(SetExpression<Integer> parent, Collection<IntegerConstant> elements) {
		return new SetLiteral(parent, elements);
	}

	public static SetLiteral fromIntegers(SetExpression<Integer> parent, Collection<Integer> elements) {
		return subset(parent, elements.stream().map(IntegerConstant::new).collect(Collectors.toSet()));
	}

	public static SetLiteral singleton(SetExpression<Integer> parent, IntegerConstant element) {
		return subset(parent, ListUtils.fromElements(element));
	}

	public static SetLiteral subset(Collection<IntegerConstant> elements) {
    return subset(IntegerSetExpression.INTEGER_UNIVERSE, elements);
	}

	public static SetLiteral fromIntegers(Collection<Integer> elements) {
    return subset(IntegerSetExpression.INTEGER_UNIVERSE,
				elements.stream().map(IntegerConstant::new).collect(Collectors.toSet()));
	}

	public static SetLiteral singleton(IntegerConstant element) {
    return subset(IntegerSetExpression.INTEGER_UNIVERSE, ListUtils.fromElements(element));
	}

	@Override
	public String use() {
    return "{"
        + elements.stream().sorted().map(IntegerConstant::use).collect(Collectors.joining(", "))
        + "}"; // note: elements are sorted to improve readability
	}

	@Override
	public Boolean contains(Integer value) {
		return elements.contains(value);
	}

	@Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

}
