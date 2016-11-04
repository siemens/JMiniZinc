package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * Assigns a value to a previously unassigned variable.
 *
 * @author z003ft4a (Richard Taupe)
 *
 * @param <V>
 *          the type of the variable.
 */
public class Assignment<T, V> implements Element {

  private TypeInst<T, V> variable;
  private Expression<V> expression;

  public Assignment(TypeInst<T, V> variable, Expression<V> expression) {
		this.variable = variable;
		this.expression = expression;
	}

  public Assignment(Variable<T, V> variable, V value) { // TODO: TypeInst instead of Variable
    this.variable = variable;
    this.expression = new Constant<T, V>(variable.getType(), value);
  }

	@Override
	public String declare() {
		return String.format("%s = %s;", variable.getName(), expression.use());
	}

}
