package at.siemens.ct.jmz.elements.constraints;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.Expression;

public class Constraint implements Element {

  private final String constraintGroup;
  private final String constraintName;
  private Expression<Boolean> expression;

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

  public String getConstraintGroup() {
    return constraintGroup;
  }

  public String getConstraintName() {
    return constraintName;
  }

  @Override
  public String declare() {
    return "constraint " + getExpression() + ";";
  }

  /**
   * @return the constraint expression, which is the same as the result of {@link #declare()}, just without the
   *         {@code constraint} keyword in the front and the semicolon in the end.
   */
  String getExpression() {
    return expression.use();
  }

}
