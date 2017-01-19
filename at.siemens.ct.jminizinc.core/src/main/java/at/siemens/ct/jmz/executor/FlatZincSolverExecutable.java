/**
 * Copyright Siemens AG, 2016
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
 * @author Copyright Siemens AG, 2016
 */
public class FlatZincSolverExecutable implements Executable {

  private final File fznFile;
  private FlatZincSolver flatZincSolver;

  public FlatZincSolverExecutable(File fznFile, FlatZincSolver flatZincSolver) {
    this.fznFile = fznFile;
    this.flatZincSolver = flatZincSolver;
  }

  @Override
  public String getName() {
    return flatZincSolver.getSolverName();
  }

  @Override
  public List<String> getOptions(Long timeoutMs, Collection<Path> searchDirectories) {
    List<String> options = new ArrayList<>();

    if (timeoutMs != null) {
      options.add("-time");
      options.add(String.valueOf(timeoutMs));
    }

    options.add(fznFile.getAbsolutePath());

    return options;
  }

}
