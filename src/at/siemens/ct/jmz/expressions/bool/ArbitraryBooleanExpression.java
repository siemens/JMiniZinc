package at.siemens.ct.jmz.expressions.bool;

/**
 * Allows to construct constraints with arbitrary expressions.<br>
 * ATTENTION: These constraints are (of course) not syntax-checked by JMiniZinc!
 * TODO: In the future, all usages of this class should be implemented by dedicated constraint classes.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArbitraryBooleanExpression implements BooleanExpression {

  private String expression;

  public ArbitraryBooleanExpression(String expression) {
    this.expression = expression;
  }

  public ArbitraryBooleanExpression(String formatExpression, Object... args) {
    this.expression = String.format(formatExpression, args);
  }

  @Override
  public String use() {
    return expression;
  }

}
