package at.siemens.ct.jmz.executor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import at.siemens.ct.common.utils.NumberUtils;
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

  private String identifier;
  private IModelWriter modelWriter;
  private Long timeoutMs;
  private Stack<Process> runningProcesses = new Stack<>();
  private Map<Process, Long> startTimes = new HashMap<>();
  private Stack<List<String>> runningCommands = new Stack<>();
  private String lastSolverOutput;
  private String lastSolverErrors;
  private int lastExitCode;

  protected Executor(String identifier, IModelWriter modelWriter) {
    this.identifier = identifier;
    this.modelWriter = modelWriter;
  }

  @Override
  public void startProcess(Long timeoutMs, String... additionalOptions) throws IOException {
    this.timeoutMs = timeoutMs;
  }

  /**
   * Starts the given {@code executable}. If {@code timeoutMs != null}, the executable will be asked to terminate in
   * {@code timeoutMs - 1000} milliseconds.
   * 
   * @param executable
   * @param timeoutMs
   * @return the started {@link Process}.
   * @throws IOException
   */
  protected Process startProcess(Executable executable, Long timeoutMs, String... additionalOptions)
      throws IOException {
    this.timeoutMs = timeoutMs;
    Long voluntaryTimeoutMs = NumberUtils.Opt.minus(timeoutMs, 1000L);
    List<String> options = executable.getOptions(voluntaryTimeoutMs,
        modelWriter.getSearchDirectories());
    options.addAll(Arrays.asList(additionalOptions));
    return startProcess(executable.getName(), options);
  }

  protected File modelToTempFile() throws IOException {
    return modelWriter.toTempFile();
  }

  private Process startProcess(String executable, List<String> options)
      throws IOException {
    List<String> command = new ArrayList<>(4);
    command.add(executable);
    command.addAll(options);
    return startProcess(command);
  }

  private Process startProcess(List<String> command) throws IOException {
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

    long elapsedTime = elapsedTime(runningProcess);
    try {
      Long remainingMs = NumberUtils.Opt.minus(timeoutMs, elapsedTime);
      if (remainingMs == null) {
        runningProcess.waitFor();
      } else {
        boolean exited = runningProcess.waitFor(remainingMs, TimeUnit.MILLISECONDS);
        if (!exited) {
          System.out.println("Timeout was reached while running " + getCurrentCommand());
          destroyRunningProcesses();
        }
      }
      lastExitCode = runningProcess.exitValue();
      System.out.println("Process exited with exit code " + lastExitCode);
    } catch (InterruptedException e) {
      System.out.println(
          "Executor was interrupted while running " + getCurrentCommand());
      destroyRunningProcesses();
      throw e;
    } finally {
      elapsedTime = elapsedTime(runningProcess);
      System.out
          .println("Executor " + identifier + " is finished after " + elapsedTime + " ms: "
              + getCurrentCommand());

      try {
        lastSolverOutput = futureOutput.get();
        lastSolverErrors = futureErrors.get();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }

      ACTIVE_PROCESSES.remove(runningProcess);
    }
    return elapsedTime;
  }

  private String getCurrentCommand() {
    return runningCommands.peek().stream().collect(Collectors.joining(" "));
  }

  private Process getCurrentProcess() {
    return runningProcesses.peek();
  }

  private void destroyRunningProcesses() {
    for (Process process : runningProcesses) {
      process.destroy();
    }
  }

  private long elapsedTime(Process process) {
    return System.currentTimeMillis() - startTimes.get(process);
  }

  protected Long remainingTime() {
    return NumberUtils.Opt.minus(timeoutMs, elapsedTime(getCurrentProcess()));
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
