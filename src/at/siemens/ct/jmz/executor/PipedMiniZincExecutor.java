package at.siemens.ct.jmz.executor;

import java.io.File;
import java.io.IOException;

import at.siemens.ct.common.utils.ListUtils;
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

  private static final String COMPILER = "mzn2fzn";
  private static final String COMPILER_FLAG = "-Ggecode";
  private static final String SOLVER = "fzn-gecode";

  private static final String COMPILER_OUTPUT_FZN = "--output-fzn-to-file";

  private File fznFile;

  public PipedMiniZincExecutor(IModelWriter modelWriter) {
    super(modelWriter);
  }

  @Override
  public void startProcess() throws IOException {
    fznFile = TemporaryFiles.createFZN();
    startProcessIncludeSearchDirectories(COMPILER,
        ListUtils.fromElements(COMPILER_FLAG, COMPILER_OUTPUT_FZN, fznFile.getAbsolutePath()));
  }

  @Override
  public long waitForSolution() throws InterruptedException {
    // wait for compiler:
    long elapsedTime = super.waitForSolution();

    printLastSolverErrors();

    if (getLastExitCode() == EXIT_CODE_SUCCESS) {
      // execute and wait for solver:
      try {
        startProcess(SOLVER, fznFile.getAbsolutePath());
        elapsedTime += super.waitForSolution();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return elapsedTime;
  }

}
