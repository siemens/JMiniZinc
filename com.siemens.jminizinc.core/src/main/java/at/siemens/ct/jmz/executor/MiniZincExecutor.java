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
 * Executes <i>minizinc</i>, which in turn spawns child processes like <i>mzn2fzn</i>, <i>fzn-gecode</i> etc. The
 * disadvantage of this is that we cannot kill grandchild processes, so this executor cannot guarantee that all
 * processes are terminated when it is interrupted.
 * 
 * @author Copyright Siemens AG, 2016, 2019
 */
public class MiniZincExecutor extends Executor {

  private MiniZincSolver solver = MiniZincSolver.GECODE;

  public MiniZincExecutor(String identifier) {
    super(identifier);
  }

  public MiniZincExecutor(String identifier, MiniZincSolver solver) {
    super(identifier);
    this.solver = solver;
  }

  @Override
  public void startProcess(IModelWriter modelWriter, Long timeoutMs, String... additionalOptions) throws IOException {
    startProcess(new MiniZincExecutable(solver, modelToTempFile(modelWriter)), modelWriter, timeoutMs,
        additionalOptions);
  }

}
