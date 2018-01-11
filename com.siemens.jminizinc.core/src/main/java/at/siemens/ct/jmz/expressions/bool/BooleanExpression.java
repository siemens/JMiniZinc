/**
 * Copyright Siemens AG, 2016-2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author Copyright Siemens AG, 2016-2017
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

  @Override
  BooleanExpression substitute(String name, Object value);
}
