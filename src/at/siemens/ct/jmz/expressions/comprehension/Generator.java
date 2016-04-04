package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * Represents the generating part of a {@link Comprehension}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Generator implements Expression<int[]> {

  private Collection<IteratorExpression> iterators;

  public Generator(IteratorExpression... iterators) {
    this.iterators = ListUtils.fromElements(iterators);
  }

  public Generator(Collection<IteratorExpression> iterators) {
    this.iterators = iterators;
  }

  public List<? extends IntSetExpression> getRange() {
    return iterators.stream().map(IteratorExpression::getRange).collect(Collectors.toList());
  }

  @Override
  public String use() {
    return iterators.stream().map(IteratorExpression::iterate).collect(Collectors.joining(", "));
  }

}
