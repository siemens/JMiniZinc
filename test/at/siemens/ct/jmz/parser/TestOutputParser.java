package at.siemens.ct.jmz.parser;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.siemens.ct.jmz.executor.IExecutor;

/**
 * Tests {@link MiniZincOutputParser}.
 * 
 * @author © Siemens AG, 2016
 */
public class TestOutputParser {

  private PseudoExecutor executor;
  private MiniZincOutputParser parser;

  @Before
  public void setUp() {
    executor = new PseudoExecutor();
    parser = new MiniZincOutputParser(executor);
  }

  @Test
  public void testComplete() {
    writeSolutions(3);
    write(SolverMessage.COMPLETE.getMessage());
    expect("sol3");
  }

  @Test
  public void testUnbounded() {
    writeSolutions(3);
    write(SolverMessage.UNBOUNDED.getMessage());
    expect("sol3");
  }

  @Test
  public void testUnsatisfiable() {
    writeSolutions(3);
    write(SolverMessage.UNSATISFIABLE.getMessage());
    expect("sol3");
  }

  @Test
  public void testUnknown() {
    writeSolutions(3);
    write(SolverMessage.UNKNOWN.getMessage());
    expect("sol3");
  }

  @Test
  public void testIncomplete() {
    writeSolutions(2);
    write("sol3");
    expect("sol2");
  }

  @Test
  public void testEmpty() {
    expect("");
  }

  private void writeSolutions(int n) {
    for (int i = 1; i <= n; i++) {
      write("sol" + i);
      writeSolutionSeparator();
    }
  }

  private void writeSolutionSeparator() {
    write(MiniZincOutputParser.SOLUTION_SEPARATOR);
  }

  private void write(String output) {
    executor.outputBuilder.append(output);
  }

  private void expect(String expectedSolution) {
    String lastSolution = parser.getLastSolution();
    Assert.assertEquals(expectedSolution, lastSolution);
  }

  private class PseudoExecutor implements IExecutor {

    StringBuilder outputBuilder = new StringBuilder();
    private long startTime;

    PseudoExecutor() {
    }

    @Override
    public void startProcess(Long timeoutMs, String... additionalOptions) throws IOException {
      this.startTime = System.currentTimeMillis();
    }

    @Override
    public long waitForSolution() throws InterruptedException {
      return System.currentTimeMillis() - startTime;
    }

    @Override
    public String getLastSolverOutput() {
      return outputBuilder.toString();
    }

    @Override
    public String getLastSolverErrors() {
      return "";
    }

    @Override
    public int getLastExitCode() {
      return EXIT_CODE_SUCCESS;
    }

  }

}
