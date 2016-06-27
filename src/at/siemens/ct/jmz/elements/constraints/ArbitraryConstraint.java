package at.siemens.ct.jmz.elements.constraints;

import at.siemens.ct.jmz.expressions.bool.ArbitraryBooleanExpression;

/**
 * Allows to construct constraints with arbitrary expressions.<br>
 * ATTENTION: These constraints are (of course) not syntax-checked by JMiniZinc!
 * TODO: In the future, all usages of this class should be implemented by dedicated constraint classes.
 * 
 * @author z003ft4a (Richard Taupe)
 * @deprecated use {@link ArbitraryBooleanExpression} instead.
 */
@Deprecated
public class ArbitraryConstraint extends Constraint {

  private String expression;

  public ArbitraryConstraint(String constraintGroup, String constraintName, String expression) {
    super(constraintGroup, constraintName);
    this.expression = expression;
  }

  /**
   * @see #ArbitraryConstraint(String)
   * @see String#format(String, Object...)
   */
  public ArbitraryConstraint(String constraintGroup, String constraintName, String formatExpression,
      Object... args) {
    super(constraintGroup, constraintName);
    this.expression = String.format(formatExpression, args);
  }

  @Override
  String getExpression() {
    return expression;
  }

}
