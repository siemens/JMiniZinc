package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.expressions.Expression;

public interface IntExpression extends Expression, SummableIntegers {

  @Override
  default boolean isSingleton() {
    return true;
  }

}
