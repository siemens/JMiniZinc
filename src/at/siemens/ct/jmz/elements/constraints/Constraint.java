package at.siemens.ct.jmz.elements.constraints;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author © Siemens AG, 2016
 */
public class Constraint implements Element {

  protected final String constraintGroup;
  protected final String constraintName;
  protected Expression<Boolean> expression;

  /**
   * @see #Constraint(String, String, Expression)
   * @param constraintGroup
   * @param constraintName
   */
  protected Constraint(String constraintGroup, String constraintName) {
    this(constraintGroup, constraintName, null);
  }

  /**
   * Creates a new constraint, assigning it a group and a name.
   * 
   * @param constraintGroup
   *          Constraints that belong together should have the same group name.
   * @param constraintName
   *          By this name, constraints are uniquely identifiable within a group.
   * @param expression
   *          The expression enforced to be true by this constraint.
   */
  public Constraint(String constraintGroup, String constraintName,
      Expression<Boolean> expression) {
    this.constraintGroup = constraintGroup;
    this.constraintName = constraintName;
    this.expression = expression;
  }

  /**
   * Creates a new constraint without assigning it a group or a name.
   * 
   * @see #Constraint(String, String, Expression)
   */
  public Constraint(Expression<Boolean> expression) {
    this(null, null, expression);
  }

  public String getConstraintGroup() {
    return constraintGroup;
  }

  public String getConstraintName() {
    return constraintName;
  }

  @Override
  public String declare() {
    return "constraint " + getExpressionString() + ";";
  }

  protected String getExpressionString() {
    return expression.use();
  }

  public Expression<?> getExpression() {
    return expression;
  }

}
