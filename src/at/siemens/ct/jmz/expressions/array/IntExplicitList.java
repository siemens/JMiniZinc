package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * TODO: Overlaps with {@link ListComprehension}. Wanted: a beautiful design
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntExplicitList implements IntArrayExpression {

  public static final String DEFAULT_NULL = "<>";
  public static final char LEFT_BRACKET = '[';
  public static final char RIGHT_BRACKET = ']';

  private Collection<IntSet> range;
  private IntSet type;
  private Collection<Integer> values;
  private String nullElement = DEFAULT_NULL;

  public IntExplicitList(Collection<IntSet> range, IntSet type, Collection<Integer> values) {
    super();
    this.range = range;
    this.type = type;
    this.values = values;
    initNullElement();
  }

  private void initNullElement() {
    if (type instanceof PseudoOptionalIntSet) {
      nullElement = ((PseudoOptionalIntSet) type).getNullElement().use();
    }
  }

  @Override
  public Collection<IntSet> getRange() {
    return Collections.unmodifiableCollection(range);
  }

  @Override
  public String use() {
    return valuesToString();
  }

  private String valuesToString() {
    Function<? super Integer, ? extends String> intOrNull =
        i -> (i == null ? nullElement : i.toString());
    return LEFT_BRACKET + values.stream().map(intOrNull).collect(Collectors.joining(", "))
        + RIGHT_BRACKET;
  }
}
