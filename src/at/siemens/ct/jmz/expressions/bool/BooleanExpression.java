package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author © Siemens AG, 2016
 */
public interface BooleanExpression extends Expression<Boolean> {

  /**
   * Determines if this expression is composite and thus should be parenthesised when used in other expressions.
   */
  default boolean isComposite() {
    return true;
  }

  /**
   * Puts parentheses around this expression if it {@link #isComposite()}.
   * 
   * @return {@link #use()}, adding parentheses around the expression if {@link #isComposite()}.
   */
  default String parenthesiseIfNeccessary() {
    if (isComposite()) {
      return "(" + use() + ")";
    } else {
      return use();
    }
  }

  default Negation negate() {
    return new Negation(this);
  }

  default Conjunction and(BooleanExpression conjunct) {
    return new Conjunction(this, conjunct);
  }

  default Disjunction or(BooleanExpression disjunct) {
    return new Disjunction(this, disjunct);
  }

  default Implication implies(BooleanExpression consequent) {
    return new Implication(this, consequent);
  }

  default LeftImplication onlyIf(BooleanExpression antedecent) {
    return new LeftImplication(this, antedecent);
  }

  default Equivalence iff(BooleanExpression operand2) {
    return new Equivalence(this, operand2);
  }
}
