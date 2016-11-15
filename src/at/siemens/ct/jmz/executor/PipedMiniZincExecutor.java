package at.siemens.ct.jmz.executor;

import java.io.File;
import java.io.IOException;

import at.siemens.ct.jmz.files.TemporaryFiles;
import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Executes <i>mzn2fzn</i> and <i>fzn-gecode</i> separately. This has the advantage over {@link MiniZincExecutor} that
 * the executor has control over both processes and can kill them if it is interrupted. Because of this, this
 * implementation should be preferred over {@link MiniZincExecutor} if a clean exit behaviour in case of interrupts is
 * required.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class PipedMiniZincExecutor extends Executor {

  private static final FlatZincSolver FLATZINC_SOLVER = FlatZincSolver.GECODE;

  private File fznFile;

  public PipedMiniZincExecutor(String identifier, IModelWriter modelWriter) {
    super(identifier, modelWriter);
  }

  @Override
  public void startProcess(Long timeoutMs, String... additionalOptions) throws IOException {
    super.startProcess(timeoutMs, additionalOptions);
    fznFile = TemporaryFiles.createFZN();
    startProcess(new MznToFznExecutable(modelToTempFile(), fznFile, FLATZINC_SOLVER), timeoutMs, additionalOptions);
  }

  @Override
  public long waitForSolution() throws InterruptedException, IOException {
    // wait for compiler:
    long elapsedTime = super.waitForSolution();

    printLastSolverErrors();

    if (getLastExitCode() == EXIT_CODE_SUCCESS) {
      // execute and wait for solver:
      startProcess(new FlatZincSolverExecutable(fznFile, FLATZINC_SOLVER), remainingTime());
      elapsedTime += super.waitForSolution();
    }

    return elapsedTime;
  }

}
