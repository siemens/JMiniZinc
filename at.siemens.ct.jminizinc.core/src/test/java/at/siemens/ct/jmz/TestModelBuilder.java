/**
 * Copyright Siemens AG, 2017
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.siemens.ct.jmz;

import org.junit.Test;

import at.siemens.ct.jmz.elements.constraints.Constraint;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;
import at.siemens.ct.jmz.expressions.bool.RelationalOperation;
import at.siemens.ct.jmz.expressions.bool.RelationalOperator;
import at.siemens.ct.jmz.expressions.integer.IntegerConstant;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;

/**
 * Tests declarations of various {@link Element}s
 *
 * @author Copyright Siemens AG, 2017
 */
public class TestModelBuilder {

  @Test
  public void testTwoUnnamedConstraints() {
    IntegerVariable x3 = new IntegerVariable("x3");
    BooleanExpression expression1 = new RelationalOperation<>(x3, RelationalOperator.EQ,
        new IntegerConstant(2));
    BooleanExpression expression2 = new RelationalOperation<>(x3, RelationalOperator.EQ,
        new IntegerConstant(3));
    ModelBuilder modelBuilder = new ModelBuilder();
    modelBuilder.add(new Constraint(expression1), new Constraint(expression2));
    // expect success (adding two unnamed constraints two a ModelBuilder must not raise an exception)
  }

}
