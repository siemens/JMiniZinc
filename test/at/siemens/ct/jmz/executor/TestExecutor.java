package at.siemens.ct.jmz.executor;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.siemens.ct.jmz.IModelBuilder;
import at.siemens.ct.jmz.ModelBuilder;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.elements.SolvingStrategy;
import at.siemens.ct.jmz.writer.IModelWriter;
import at.siemens.ct.jmz.writer.ModelWriter;

/**
 * Tests {@link Executor}
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class TestExecutor {

  private IModelBuilder modelBuilder = new ModelBuilder();
  private IModelWriter modelWriter = new ModelWriter(modelBuilder,
      SolvingStrategy.SOLVE_SATISFY);
  private IExecutor executor = new Executor(modelWriter);

  @Before
  public void setUp() {
    modelBuilder.reset();
  }

  @Test
  public void testSingleVariableGetOutput() throws IOException {
    IntSet setOneTwoThree = modelBuilder.createIntSet("OneTwoThree", 1, 3);
    IntVar i = modelBuilder.createIntVar("i", setOneTwoThree);
    executor.startProcess();
    executor.waitForSolution();
    String lastSolverOutput = executor.getLastSolverOutput();

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("i = 1;");
    expectedOutput.append(System.lineSeparator());
    expectedOutput.append("----------");

    Assert.assertEquals(expectedOutput.toString(), lastSolverOutput);
  }

  @Test
  @Ignore
  public void testSingleVariableGetSolution() throws IOException {
    IntSet setOneTwoThree = modelBuilder.createIntSet("OneTwoThree", 1, 3);
    IntVar i = modelBuilder.createIntVar("i", setOneTwoThree);
    executor.startProcess();
    executor.waitForSolution();
    int solI = executor.getSolution(i);
    Assert.assertTrue("Unexpected solution: i=" + solI, solI >= 1 && solI <= 3);
  }

}
