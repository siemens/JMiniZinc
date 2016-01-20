package at.siemens.ct.jmz.expressions.array;

import at.siemens.ct.jmz.expressions.Expression;

public interface IntArrayExpression extends Expression {

  /**
   * @return the expression's name, if it has one, or else its value
   */
  String nameOrValue();

}
