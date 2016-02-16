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

  private static final String COMPILER = "mzn2fzn";
  private static final String SOLVER = "fzn-gecode";

  private static final String COMPILER_OUTPUT_FZN = "--output-fzn-to-file";

  private File fznFile;

  public PipedMiniZincExecutor(IModelWriter modelWriter) {
    super(modelWriter);
  }

  @Override
  public void startProcess() throws IOException {
    fznFile = TemporaryFiles.createFZN();
    startProcess(COMPILER, modelToTempFile(), COMPILER_OUTPUT_FZN, fznFile.getAbsolutePath());
  }

  @Override
  public void waitForSolution() throws InterruptedException {
    // wait for compiler:
    super.waitForSolution();

    // execute and wait for solver:
    try {
      startProcess(SOLVER, fznFile.getAbsolutePath());
      super.waitForSolution();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      removeTemporaryFiles();
    }
  }

  private void removeTemporaryFiles() {
    if (fznFile != null) {
      fznFile.delete();
      fznFile = null;
    }
  }

}
