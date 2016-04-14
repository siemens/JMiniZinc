package at.siemens.ct.jmz.executor;

import java.io.IOException;

import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Executes <i>minizinc</i>, which in turn spawns child processes like <i>mzn2fzn</i>, <i>fzn-gecode</i> etc. The
 * disadvantage of this is that we cannot kill grandchild processes, so this executor cannot guarantee that all
 * processes are terminated when it is interrupted.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class MiniZincExecutor extends Executor {

  public MiniZincExecutor(String identifier, IModelWriter modelWriter) {
    super(identifier, modelWriter);
  }

  @Override
  public void startProcess(Long timeoutMs, String... additionalOptions) throws IOException {
    startProcess(new MiniZincExecutable(modelToTempFile()), timeoutMs, additionalOptions);
  }

}
