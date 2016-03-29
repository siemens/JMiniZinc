package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.IntConstant;

public interface IntExpression extends SummableIntegers<Integer> {

  @Override
  default boolean isSingleton() {
    return true;
  }

  /**
   * Adds a delta to this expression and returns the result.
   * 
   * @param delta
   * @return a new expression whose value is {@code this+delta}
   */
  default IntExpression add(int delta) {
    return new SumExpression(this, new IntConstant(delta));
  }

  /**
   * Checks if the given integer expression string is negative.
   * 
   * @param expression
   * @return {@code true} iff the given expression starts with a minus sign
   */
  static boolean isNegative(String expression) {
    return expression.startsWith("-");
  }

  /**
   * Puts the given integer expression string into braces.
   * 
   * @param expression
   * @return a parenthesised version of the given expression
   */
  static String parenthesise(String expression) {
    return "(" + expression + ")";
  }

  /**
   * Checks if the given value is greater than or equal to this expression. If this cannot be determined (e.g. if the
   * value of the expression is not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  default Boolean isGreaterThanOrEqualTo(int value) {
    return null;
  }

  /**
   * Checks if the given value is less than or equal to this expression. If this cannot be determined (e.g. if the value
   * of the expression is not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  default Boolean isLessThanOrEqualTo(int value) {
    return null;
  }

}
