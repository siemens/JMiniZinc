package at.siemens.ct.jmz.expressions.set;

import java.util.Set;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;

public interface IntSetExpression extends Expression<Set<Integer>> {

  /**
   * Creates an iterator over this set, using {@code nameOfIterator} as the name of the local variable.
   * 
   * @param nameOfIterator
   * @return an iterator over this set
   */
  default IteratorExpression iterate(String nameOfIterator) {
    return new IteratorExpression(this, nameOfIterator);
  }

  /**
   * Checks if the given value is contained by this set. If this cannot be determined (e.g. if the set is bounded by
   * constants whose values are not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  Boolean contains(int value);
}
