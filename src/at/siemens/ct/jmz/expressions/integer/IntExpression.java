package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.expressions.Expression;

public interface IntExpression extends Expression, SummableIntegers {

  @Override
  default boolean isSingleton() {
    return true;
  }

  /**
   * Adds a delta to this expression and returns the result.
   * 
   * @param delta
   * @return a new expression whose value is {@code this+delta}
   */
  default IntExpression add(int delta) {
    return new SumExpression(this, new IntConstant(delta));
  }

}
