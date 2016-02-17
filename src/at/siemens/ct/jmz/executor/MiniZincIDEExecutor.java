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

  private static final String IDE_EXE_PATH = "MiniZincIDE";

  protected MiniZincIDEExecutor(IModelWriter modelWriter) {
    super(modelWriter);
  }

  @Override
  public void startProcess() throws IOException {
    startProcess(IDE_EXE_PATH, modelToTempFile());
  }

}
