package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.ComparisonExpression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * Represents the generating part of a {@link Comprehension}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Generator implements Expression<int[]> {

  private ComparisonExpression<Integer> restriction;
  private Collection<IteratorExpression> iterators;

  public Generator(IteratorExpression... iterators) {
    this(ListUtils.fromElements(iterators));
  }

  public Generator(Collection<IteratorExpression> iterators) {
    this(null, iterators);
  }

  public Generator(ComparisonExpression<Integer> restriction, IteratorExpression... iterators) {
    this(restriction, ListUtils.fromElements(iterators));
  }

  public Generator(ComparisonExpression<Integer> restriction,
      Collection<IteratorExpression> iterators) {
    this.restriction = restriction;
    this.iterators = iterators;
  }

  public List<? extends IntSetExpression> getRange() {
    return iterators.stream().map(IteratorExpression::getRange).collect(Collectors.toList());
  }

  @Override
  public String use() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        iterators.stream().map(IteratorExpression::iterate).collect(Collectors.joining(", ")));
    if (restriction != null) {
      sb.append(" where ");
      sb.append(restriction.use());
    }
    return sb.toString();
  }

}
