/**
 * Copyright Siemens AG, 2016, 2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import at.siemens.ct.jmz.files.TemporaryFiles;
import at.siemens.ct.jmz.writer.IModelWriter;

import java.io.File;
import java.io.IOException;

/**
 * Executes <i>mzn2fzn</i> and <i>fzn-gecode</i> separately. This has the advantage over {@link MiniZincExecutor} that
 * the executor has control over both processes and can kill them if it is interrupted. Because of this, this
 * implementation should be preferred over {@link MiniZincExecutor} if a clean exit behaviour in case of interrupts is
 * required.
 *
 * @deprecated because in the latest releases of MiniZinc IDE, mzn2fzn is not shipped as a separate executable anymore.
 * 
 * @author Copyright Siemens AG, 2016, 2019
 */
@Deprecated
public class PipedMiniZincExecutor extends Executor {

  private FlatZincSolver solver = FlatZincSolver.GECODE;
  private File fznFile;
  private IModelWriter modelWriter;

  public PipedMiniZincExecutor(String identifier) {
    super(identifier);
  }

  public PipedMiniZincExecutor(String identifier, FlatZincSolver solver) {
    super(identifier);
    this.solver = solver;
  }

  @Override
  public void startProcess(IModelWriter modelWriter, Long timeoutMs, String... additionalOptions) throws IOException {
    super.startProcess(modelWriter, timeoutMs, additionalOptions);
    this.fznFile = TemporaryFiles.createFZN();
    this.modelWriter = modelWriter;
    startProcess(new MznToFznExecutable(modelToTempFile(modelWriter), fznFile, solver), modelWriter, timeoutMs,
        additionalOptions);
  }

  @Override
  public long waitForSolution() throws InterruptedException, IOException {
    // wait for compiler:
    long elapsedTime = super.waitForSolution();

    printLastSolverErrors();

    if (getLastExitCode() == EXIT_CODE_SUCCESS) {
      // execute and wait for solver:
      startProcess(new FlatZincSolverExecutable(fznFile, solver), modelWriter, remainingTime());
      elapsedTime += super.waitForSolution();
    }

    return elapsedTime;
  }

}
