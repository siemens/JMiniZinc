/**
 * Copyright Siemens AG, 2016, 2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Copyright Siemens AG, 2016, 2019
 */
public class MiniZincExecutable implements Executable {

  private final MiniZincSolver solver;
  private final File mznFile;

  public MiniZincExecutable(MiniZincSolver solver, File mznFile) {
    this.solver = solver;
    this.mznFile = mznFile;
  }

  @Override
  public String getName() {
    return solver.getExecutableName();
  }

  @Override
  public List<String> getOptions(Long timeoutMs, Collection<Path> searchDirectories) {
    List<String> options = new ArrayList<>();
    options.addAll(solver.getOptions());
    for (Path dir : searchDirectories) {
      options.add("-I");
      options.add(dir.toAbsolutePath().toString());
    }

    options.add(mznFile.getAbsolutePath());

    return options;
  }

}
