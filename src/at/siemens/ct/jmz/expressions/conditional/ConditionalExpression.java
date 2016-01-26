package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

/**
 * Represents an if-else-...-endif tree in MiniZinc. Each branch must be an expression of type {@code T}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 * @param <T>
 */
public class ConditionalExpression<T extends Expression> implements Expression {

  private BooleanExpression condition;
  private T thenBranch;
  private T elseBranch;

  public ConditionalExpression(BooleanExpression condition, T thenBranch, T elseBranch) {
    this.condition = condition;
    this.thenBranch = thenBranch;
    this.elseBranch = elseBranch;
  }

  @Override
  public String use() {
    return String.format("if %s then %s else %s endif", condition.use(), thenBranch.use(),
        elseBranch.use());
  }

}
