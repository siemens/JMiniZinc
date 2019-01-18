/**
 * Copyright Siemens AG, 2016, 2019
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
 * @author Copyright Siemens AG, 2016, 2019
 */
public class MiniZincIDEExecutor extends Executor {

  protected MiniZincIDEExecutor(String identifier) {
    super(identifier);
  }

  @Override
  public void startProcess(IModelWriter modelWriter, Long timeoutMs, String... additionalOptions) throws IOException {
    startProcess(new MiniZincIDEExecutable(modelToTempFile(modelWriter)), modelWriter, timeoutMs, additionalOptions);
  }

}
