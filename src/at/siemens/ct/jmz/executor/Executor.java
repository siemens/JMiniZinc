package at.siemens.ct.jmz.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Runs a MiniZinc process and communicates with it.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public abstract class Executor implements IExecutor {

  private static final Set<Process> ACTIVE_PROCESSES = Collections
      .synchronizedSet(new HashSet<Process>());

  private IModelWriter modelWriter;
  private Stack<Process> runningProcesses = new Stack<>();
  private Map<Process, Long> startTimes = new HashMap<>();
  private Stack<String[]> runningProcessNames = new Stack<>();
  private File temporaryModelFile;
  private String lastSolverOutput;
  private String lastSolverErrors;

  protected Executor(IModelWriter modelWriter) {
    this.modelWriter = modelWriter;
  }

  protected String modelToTempFile() throws IOException {
    temporaryModelFile = modelWriter.toTempFile();
    return temporaryModelFile.getAbsolutePath();
  }

  protected Process startProcess(String... command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    Process runningProcess = startProcess(processBuilder);
    ACTIVE_PROCESSES.add(runningProcess);
    runningProcesses.push(runningProcess);
    runningProcessNames.push(command);
    return runningProcess;
  }

  private Process startProcess(ProcessBuilder processBuilder) throws IOException {
    Process process = processBuilder.start();
    startTimes.put(process, System.currentTimeMillis());
    return process;
  }

  @Override
  public long waitForSolution() throws InterruptedException {
    if (runningProcesses.isEmpty()) {
      throw new IllegalStateException("No running process.");
    }

    Process runningProcess = runningProcesses.peek();
    BufferedReader outputReader = new BufferedReader(
        new InputStreamReader(runningProcess.getInputStream()));
    BufferedReader errorReader = new BufferedReader(
        new InputStreamReader(runningProcess.getErrorStream()));

    try {
      runningProcess.waitFor();
      // TODO: runningProcess.waitFor(timeout, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println(
          "Executor was interrupted while running " + Arrays.toString(runningProcessNames.peek()));
      for (Process process : runningProcesses) {
        process.destroy();
      }
      throw e;
    } finally {
      System.out.println("Executor is finished: " + Arrays.toString(runningProcessNames.peek()));

      lastSolverOutput = outputReader.lines().collect(Collectors.joining(System.lineSeparator()));
      lastSolverErrors = errorReader.lines().collect(Collectors.joining(System.lineSeparator()));

      removeCurrentModelFile();
      ACTIVE_PROCESSES.remove(runningProcess);
    }
    return elapsedTime(runningProcess);
  }

  private long elapsedTime(Process process) {
    return System.currentTimeMillis() - startTimes.get(process);
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
      temporaryModelFile.deleteOnExit();
      temporaryModelFile = null;
    }
  }

  /**
   * @return {@code true} iff there exists an active {@link Executor} which has a currently running process.
   */
  public static boolean isRunning() {
    for (Process process : ACTIVE_PROCESSES) {
      if (process.isAlive()) {
        return true;
      }
    }
    return false;
  }

}
