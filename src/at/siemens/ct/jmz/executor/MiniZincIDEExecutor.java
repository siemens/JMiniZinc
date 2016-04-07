package at.siemens.ct.jmz.executor;

import java.io.IOException;

import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Executes <i>MiniZincIDE</i> (i.e. shows the generated model on the screen) and should therefore only be used for
 * testing/presentation purposes.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class MiniZincIDEExecutor extends Executor {

  protected MiniZincIDEExecutor(String identifier, IModelWriter modelWriter) {
    super(identifier, modelWriter);
  }

  @Override
  public void startProcess(Long timeoutMs) throws IOException {
    startProcess(new MiniZincIDEExecutable(modelToTempFile()), timeoutMs);
  }

}
