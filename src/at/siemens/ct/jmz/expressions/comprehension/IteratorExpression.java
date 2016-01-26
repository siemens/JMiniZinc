package at.siemens.ct.jmz.expressions.comprehension;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * Represents one (of potentially multiple) iterators in a {@link Generator}.
 * 
 * @author z003ft4a (Richard Taupe)
 */
public class IteratorExpression implements Expression {

  private IntSet range;
  private String name;

  public IteratorExpression(IntSet range, String name) {
    this.range = range;
    this.name = name;
  }

  public IntSet getRange() {
    return range;
  }

  public String getName() {
    return name;
  }

  @Override
  public String use() {
    return String.format("%s in %s", name, range.nameOrRange());
  }

}
