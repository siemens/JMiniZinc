package at.siemens.ct.jmz.elements.constraints;

import at.siemens.ct.jmz.elements.Element;

public abstract class Constraint implements Element {

  @Override
  public String declare() {
    return "constraint " + getExpression() + ";";
  }

  /**
   * @return the constraint expression, which is the same as the result of {@link #declare()}, just without the
   *         {@code constraint} keyword in the front and the semicolon in the end.
   */
  abstract String getExpression();
  
}
