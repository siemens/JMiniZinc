package at.siemens.ct.jmz.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Runs a MiniZinc process and communicates with it.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Executor implements IExecutor {

  private static final String MZN_EXE_PATH = "minizinc";
  private static final String IDE_EXE_PATH = "MiniZincIDE";
  private static String DEFAULT_EXE_PATH = MZN_EXE_PATH;
  private static boolean DEFAULT_SHOW_IDE = false;

  private IModelWriter modelWriter;
  private String pathToExecutable = DEFAULT_EXE_PATH;
  private boolean showModelInIDE = DEFAULT_SHOW_IDE;
  private Process runningProcess;
  private File temporaryModelFile;
  private String lastSolverOutput;
  private String lastSolverErrors;

  public Executor(IModelWriter modelWriter) {
    super();
    this.modelWriter = modelWriter;
  }

  @Override
  public String getPathToExecutable() {
    return pathToExecutable;
  }

  @Override
  public void setPathToExecutable(String pathToExecutable) {
    this.pathToExecutable = pathToExecutable;
  }

  @Override
  public void setShowModelInIDE(boolean showModelInIDE) {
    this.showModelInIDE = showModelInIDE;
    this.pathToExecutable = getExePath(showModelInIDE);
  }

  public static void setDefaultShowModelInIDE(boolean showModelInIDE) {
    DEFAULT_SHOW_IDE = showModelInIDE;
    DEFAULT_EXE_PATH = getExePath(showModelInIDE);
  }

  private static String getExePath(boolean showModelInIDE) {
    return showModelInIDE ? IDE_EXE_PATH : MZN_EXE_PATH;
  }

  @Override
  public Process startProcess() throws IOException {
    temporaryModelFile = modelWriter.toTempFile();
    String command = pathToExecutable; // TODO: add more parameters, abstract parameter adding
    ProcessBuilder processBuilder = new ProcessBuilder(command,
        temporaryModelFile.getAbsolutePath());
    runningProcess = processBuilder.start();
    return runningProcess;
  }

  @Override
  public void waitForSolution() {
    if (runningProcess == null) {
      throw new IllegalStateException("No running process.");
    }

    BufferedReader outputReader = new BufferedReader(
        new InputStreamReader(runningProcess.getInputStream()));
    BufferedReader errorReader = new BufferedReader(
        new InputStreamReader(runningProcess.getErrorStream()));

    try {
      runningProcess.waitFor();
    } catch (InterruptedException e) {
    }

    lastSolverOutput = outputReader.lines().collect(Collectors.joining(System.lineSeparator()));
    lastSolverErrors = errorReader.lines().collect(Collectors.joining(System.lineSeparator()));

    if (!showModelInIDE) {
      removeCurrentModelFile();
    }
  }

  @Override
  public String getLastSolverOutput() {
    return lastSolverOutput;
  }

  @Override
  public String getLastSolverErrors() {
    return lastSolverErrors;
  }

  private void removeCurrentModelFile() {
    if (temporaryModelFile != null) {
      temporaryModelFile.delete();
      temporaryModelFile = null;
    }
  }

  @Override
  public <T> T getSolution(Variable<T> variable) {
    return variable.parseResults(lastSolverOutput);
    // TODO: only read last solution (?)
    // TODO: what if UNSATISFIABLE or UNBOUND or UNKNOWN?
  }

}
