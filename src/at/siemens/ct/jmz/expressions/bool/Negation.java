package at.siemens.ct.jmz.expressions.bool;

/**
 * Negates a {@link BooleanExpression}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Negation implements BooleanExpression {

  private BooleanExpression negatedExpression;

  public Negation(BooleanExpression negatedExpression) {
    this.negatedExpression = negatedExpression;
  }

  @Override
  public String use() {
    return String.format("not (%s)", negatedExpression.use());
  }

}
