package at.siemens.ct.jmz.executor;

/**
 * Enables the choice between different FlatZinc solvers (like Gecode, G12, ...)
 * 
 * @author © Siemens AG, 2016
 */
public enum FlatZincSolver {

  GECODE("-Ggecode", "fzn-gecode");

  private String compilerFlag;
  private String solverName;

  /**
   * @return the option to be passed to {@link MznToFznExecutable} to adapt it to this solver.
   */
  public String getCompilerFlag() {
    return compilerFlag;
  }

  /**
   * @return the name of the solver´s executable
   */
  public String getSolverName() {
    return solverName;
  }

  private FlatZincSolver(String compilerFlag, String solverName) {
    this.compilerFlag = compilerFlag;
    this.solverName = solverName;
  }

}
