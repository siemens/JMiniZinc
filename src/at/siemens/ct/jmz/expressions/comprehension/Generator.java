package at.siemens.ct.jmz.expressions.comprehension;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * Represents the generating part of a {@link Comprehension}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Generator implements Expression {

  private Collection<IteratorExpression> iterators;

  public Generator(IteratorExpression... iterators) {
    this.iterators = ListUtils.fromElements(iterators);
  }

  public Generator(Collection<IteratorExpression> iterators) {
    this.iterators = iterators;
  }

  public Collection<IntSet> getRange() {
    return iterators.stream().map(IteratorExpression::getRange).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return iterators.stream().map(IteratorExpression::toString).collect(Collectors.joining(", "));
  }

}
