package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.IntArrayVar;
import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.expressions.Expression;

public interface SummableIntegers extends Expression {

  /**
   * @return {@code true} if this is only one value (e.g. an {@link IntVar}, in contrast to an {@link IntArrayVar}).
   */
  boolean isSingleton();

  default String toSum() {
    if (isSingleton()) {
      return parenthesiseIfNegative();
    } else {
      return String.format("sum(%s)", use());
    }
    // TODO: replace by object representing function call?
  }

  default String parenthesiseIfNegative() {
    String usage = use();
    if (IntExpression.isNegative(usage)) {
      usage = IntExpression.parenthesise(usage);
    }
    return usage;
  }

}
