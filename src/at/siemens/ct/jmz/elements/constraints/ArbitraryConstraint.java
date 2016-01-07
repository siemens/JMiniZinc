package at.siemens.ct.jmz.elements.constraints;

/**
 * Allows to construct constraints with arbitrary expressions.<br>
 * ATTENTION: These constraints are (of course) not syntax-checked by JMiniZinc!
 * TODO: In the future, all usages of this class should be implemented by dedicated constraint classes.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArbitraryConstraint extends Constraint {

  private String expression;

  public ArbitraryConstraint(String expression) {
    super();
    this.expression = expression;
  }

  /**
   * @see #ArbitraryConstraint(String)
   * @see String#format(String, Object...)
   */
  public ArbitraryConstraint(String formatExpression, Object... args) {
    super();
    this.expression = String.format(formatExpression, args);
  }

  @Override
  String getExpression() {
    return expression;
  }

}
