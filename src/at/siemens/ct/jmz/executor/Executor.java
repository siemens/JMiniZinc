package at.siemens.ct.jmz.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Runs a MiniZinc process and communicates with it.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class Executor implements IExecutor {

  private static final String DEFAULT_EXE_PATH = "minizinc";

  private IModelWriter modelWriter;
  private String pathToExecutable = DEFAULT_EXE_PATH;
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

    removeCurrentModelFile();
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
  public int getSolution(IntVar i) {
    throw new UnsupportedOperationException(); // TODO: implement
  }

}
