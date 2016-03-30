package at.siemens.ct.jmz.executor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.ThreadUtils;
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
  private Stack<List<String>> runningCommands = new Stack<>();
  private File temporaryModelFile;
  private String lastSolverOutput;
  private String lastSolverErrors;
  private int lastExitCode;

  protected Executor(IModelWriter modelWriter) {
    this.modelWriter = modelWriter;
  }

  protected String modelToTempFile() throws IOException {
    temporaryModelFile = modelWriter.toTempFile();
    return temporaryModelFile.getAbsolutePath();
  }

  protected Process startProcessIncludeSearchDirectories(String executable, List<String> options)
      throws IOException {
    return startProcess(executable, options, modelWriter.getSearchDirectories(),
        modelToTempFile());
  }

  protected Process startProcess(String executable, List<String> options,
      Collection<Path> searchDirectories, String modelFileName) throws IOException {
    List<String> command = new ArrayList<>(4);
    command.add(executable);
    command.addAll(options);
    for (Path dir : searchDirectories) {
      command.add("-I");
      command.add(dir.toAbsolutePath().toString());
    }
    command.add(modelFileName);
    return startProcess(command);
  }

  protected Process startProcess(List<String> command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    return startProcess(processBuilder);
  }

  protected Process startProcess(String... command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    return startProcess(processBuilder);
  }

  private Process startProcess(ProcessBuilder processBuilder) throws IOException {
    Process process = processBuilder.start();
    startTimes.put(process, System.currentTimeMillis());
    ACTIVE_PROCESSES.add(process);
    runningProcesses.push(process);
    runningCommands.push(processBuilder.command());
    return process;
  }

  @Override
  public long waitForSolution() throws InterruptedException {
    if (runningProcesses.isEmpty()) {
      throw new IllegalStateException("No running process.");
    }

    Process runningProcess = getCurrentProcess();
    Future<String> futureOutput = ThreadUtils.readInThread(runningProcess.getInputStream());
    Future<String> futureErrors = ThreadUtils.readInThread(runningProcess.getErrorStream());

    try {
      lastExitCode = runningProcess.waitFor();
      System.out.println("Process exited with exit code " + lastExitCode);
      // TODO: runningProcess.waitFor(timeout, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.out.println(
          "Executor was interrupted while running " + getCurrentCommand());
      for (Process process : runningProcesses) {
        process.destroy();
      }
      throw e;
    } finally {
      System.out.println("Executor is finished: " + getCurrentCommand());

      try {
        lastSolverOutput = futureOutput.get();
        lastSolverErrors = futureErrors.get();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }

      ACTIVE_PROCESSES.remove(runningProcess);
    }
    return elapsedTime(runningProcess);
  }

  private String getCurrentCommand() {
    return runningCommands.peek().stream().collect(Collectors.joining(" "));
  }

  private Process getCurrentProcess() {
    return runningProcesses.peek();
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

  @Override
  public int getLastExitCode() {
    return lastExitCode;
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
