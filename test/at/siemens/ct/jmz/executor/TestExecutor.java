/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.Set;
import at.siemens.ct.jmz.elements.solving.SolvingStrategy;
import at.siemens.ct.jmz.expressions.array.IntegerArray;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;
import at.siemens.ct.jmz.expressions.set.RangeExpression;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

/**
 * Tests {@link Executor}
 *
 * @author © Siemens AG, 2016
 */
public class TestExecutor {

  private IModelBuilder modelBuilder = new ModelBuilder();
  private IModelWriter modelWriter = new ModelWriter(modelBuilder);
  private IExecutor executor = new PipedMiniZincExecutor("test", modelWriter);

  @Before
  public void setUp() {
    modelBuilder.reset();
    modelWriter.setSolvingStrategy(SolvingStrategy.SOLVE_SATISFY);
  }

  @Test
  public void testSingleVariableGetOutput() throws IOException, InterruptedException {
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    IntegerVariable i = new IntegerVariable("i", setOneTwoThree);
    modelBuilder.add(setOneTwoThree, i);
    executor.startProcess();
    Assert.assertTrue(Executor.isRunning());
    executor.waitForSolution();
    Assert.assertFalse(Executor.isRunning());
    String lastSolverOutput = executor.getLastSolverOutput();

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("i = 1;");
    expectedOutput.append(System.lineSeparator());
    expectedOutput.append("----------");

    Assert.assertEquals(expectedOutput.toString(), lastSolverOutput);
  }

  @Test
  public void testSingleVariableGetSolution() throws IOException, InterruptedException {
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    IntegerVariable i = new IntegerVariable("i", setOneTwoThree);
    modelBuilder.add(setOneTwoThree, i);
    executor.startProcess();
    Assert.assertTrue(Executor.isRunning());
    executor.waitForSolution();
    Assert.assertFalse(Executor.isRunning());
    int solI = i.parseResults(executor.getLastSolverOutput());
    Assert.assertTrue("Unexpected solution: i=" + solI, solI >= 1 && solI <= 3);
  }

  @Test
  public void testArrayGetSolution() throws IOException, InterruptedException {
    Set<Integer> setOneTwoThree = new RangeExpression(1, 3).toNamedConstant("OneTwoThree");
    IntegerArray a = IntegerArray.createVariable("a", setOneTwoThree);
    modelBuilder.add(setOneTwoThree, a);
    executor.startProcess();
    Assert.assertTrue(Executor.isRunning());
    executor.waitForSolution();
    Assert.assertFalse(Executor.isRunning());
		Integer[] solA = a.parseResults(executor.getLastSolverOutput());
    System.out.println(Arrays.toString(solA));
    Assert.assertEquals("Unexpected length of solution array: a=" + Arrays.toString(solA), 3,
        solA.length);
  }

}
