package at.siemens.ct.jmz.elements.constraints;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

public abstract class Constraint implements Element, BooleanExpression {

  @Override
  public String declare() {
    return "constraint " + getExpression() + ";";
  }

  /**
   * @return the constraint expression, which is the same as the result of {@link #declare()}, just without the
   *         {@code constraint} keyword in the front and the semicolon in the end.
   */
  abstract String getExpression();

  @Override
  public String use() {
    return getExpression();
  }

}
