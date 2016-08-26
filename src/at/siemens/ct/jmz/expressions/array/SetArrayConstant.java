package at.siemens.ct.jmz.expressions.array;

import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.NamedElement;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;
import at.siemens.ct.jmz.expressions.set.RangeExpression;

/**
 * Represents an array of constant integer sets.
 * TODO: When JMiniZinc development is consolidated, there should be only one type of Arrays, whose type can be int,
 * bool, float, string or set.
 * TODO: to avoid massive changes during design consolidation, we support only one-dimensional arrays of sets for now.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class SetArrayConstant implements NamedElement {

  // TODO: duplicate in IntExplicitList
  public static final char LEFT_BRACKET = '[';
  public static final char RIGHT_BRACKET = ']';

  private String name;
  private IntegerSetExpression range;
  private RangeExpression type;
  private List<? extends IntegerSetExpression> values;

  public SetArrayConstant(String name, IntegerSetExpression range, RangeExpression type,
      List<? extends IntegerSetExpression> values) {
    super();
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = values;
  }

  @Override
  public String declare() {
    mustHaveName();
    return String.format("array[%s] of set of %s: %s = %s;", range.use(), type.use(), name,
        valuesToString());
  }

  @Override
  public String getName() {
    return name;
  }

  private String valuesToString() {
    return LEFT_BRACKET
        + values.stream().map(IntegerSetExpression::use).collect(Collectors.joining(", "))
        + RIGHT_BRACKET;
  }

}
