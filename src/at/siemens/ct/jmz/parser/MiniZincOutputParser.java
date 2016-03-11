package at.siemens.ct.jmz.parser;

import at.siemens.ct.common.utils.StringUtils;
import at.siemens.ct.jmz.executor.IExecutor;

/**
 * Parses output from {@link IExecutor}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class MiniZincOutputParser {

  static final String SOLUTION_SEPARATOR = "----------";

  private final IExecutor executor;

  public MiniZincOutputParser(IExecutor executor) {
    this.executor = executor;
  }

  public SolverMessage getSolverMessage() {
    return SolverMessage.fromAllOutput(getAllOutput());
  }

  /**
   * Gets the entire solver standard output.
   * 
   * @return the entire string that has been written by the solver to stdout.
   */
  public String getAllOutput() {
    return executor.getLastSolverOutput();
  }

  /**
   * Gets the entire solver error output.
   * 
   * @return the entire string that has been written by the solver to stderr.
   */
  public String getAllErrors() {
    return executor.getLastSolverErrors();
  }

  /**
   * Gets the part of the solver output that describes the last solution that was found.
   * 
   * @return the part of the solver output that is found between the last and the second-to-last
   *         {@link #SOLUTION_SEPARATOR}.
   */
  public String getLastSolution() {
    return extractLastSolution(SOLUTION_SEPARATOR, SOLUTION_SEPARATOR);
  }

  private String extractLastSolution(String sepBefore, String sepAfter) {
    String allOutput = getAllOutput();
    return StringUtils.removePostfixAndPrefix(allOutput, sepBefore, sepAfter);
  }

}
