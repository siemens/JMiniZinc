package at.siemens.ct.jmz.expressions.bool;

/**
 * Negates a {@link BooleanExpression}.
 * 
 * @author © Siemens AG, 2016
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
