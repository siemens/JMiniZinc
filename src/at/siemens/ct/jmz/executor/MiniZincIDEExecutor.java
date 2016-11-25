/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import java.io.IOException;

import at.siemens.ct.jmz.writer.IModelWriter;

/**
 * Executes <i>MiniZincIDE</i> (i.e. shows the generated model on the screen) and should therefore only be used for
 * testing/presentation purposes.
 * 
 * @author © Siemens AG, 2016
 */
public class MiniZincIDEExecutor extends Executor {

  protected MiniZincIDEExecutor(String identifier, IModelWriter modelWriter) {
    super(identifier, modelWriter);
  }

  @Override
  public void startProcess(Long timeoutMs, String... additionalOptions) throws IOException {
    startProcess(new MiniZincIDEExecutable(modelToTempFile()), timeoutMs, additionalOptions);
  }

}
