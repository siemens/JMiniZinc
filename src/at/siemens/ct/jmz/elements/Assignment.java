package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * Assigns a value to a previously unassigned variable.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 * @param <T>
 *          the type of the variable.
 */
public class Assignment<T> implements Element {

  private Variable<T> variable;
  private Expression<T> expression;

  public Assignment(Variable<T> variable, Expression<T> expression) {
    this.variable = variable;
    this.expression = expression;
  }

  public Assignment(Variable<T> variable, T value) {
    this.variable = variable;
    this.expression = new Constant<>(value);
  }

  @Override
  public String declare() {
    return String.format("%s = %s;", variable.getName(), expression.use());
  }

}
