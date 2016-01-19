package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntArray;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * TODO: Overlaps with {@link ListComprehension}. Wanted: a beautiful design
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntArrayExpression implements Expression {

  public static final String DEFAULT_NULL = "<>";
  public static final char LEFT_BRACKET = '[';
  public static final char RIGHT_BRACKET = ']';

  private Collection<IntSet> range;
  private IntSet type;
  private Collection<Integer> values;
  private String nullElement = DEFAULT_NULL;

  public IntArrayExpression(Collection<IntSet> range, IntSet type, Collection<Integer> values) {
    super();
    this.range = range;
    this.type = type;
    this.values = values;
    initNullElement();
  }

  private void initNullElement() {
    if (type instanceof PseudoOptionalIntSet) {
      nullElement = ((PseudoOptionalIntSet) type).getNullElement().nameOrValue();
    }
  }

  @Override
  public String toString() {
    return valuesToString();
  }

  /**
   * Returns the operation call to coerce the given collection of {@code values} to an array whose dimensions are
   * defined by {@code range}.
   * 
   * @param range
   * @param type
   * @param values
   * @return TODO: Return operation object instead of string
   */
  public String coerce() {
    int dimensions = range.size();
    if (dimensions == 1) {
      return valuesToString();
    } else {
      return String.format("array%dd(%s, %s)", dimensions, IntArray.declareRange(range),
          valuesToString());
    }
  }

  private String valuesToString() {
    Function<? super Integer, ? extends String> intOrNull =
        i -> (i == null ? nullElement : i.toString());
    return LEFT_BRACKET + values.stream().map(intOrNull).collect(Collectors.joining(", "))
        + RIGHT_BRACKET;
  }
}
