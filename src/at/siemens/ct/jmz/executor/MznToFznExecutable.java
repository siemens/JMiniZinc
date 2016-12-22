/**
 * Copyright © Siemens AG, 2016
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
 * @author © Siemens AG, 2016
 */
public class MznToFznExecutable implements Executable {

  private static final String EXE_NAME = "mzn2fzn";
  private static final String COMPILER_OUTPUT_FZN = "--output-fzn-to-file";

  private final File mznFile;
  private final File fznFile;
  private final FlatZincSolver flatZincSolver;

  public MznToFznExecutable(File mznFile, File fznFile, FlatZincSolver flatZincSolver) {
    this.mznFile = mznFile;
    this.fznFile = fznFile;
    this.flatZincSolver = flatZincSolver;
  }

  @Override
  public String getName() {
    return EXE_NAME;
  }

  @Override
  public List<String> getOptions(Long timeoutMs, Collection<Path> searchDirectories) {
    List<String> options = new ArrayList<>();

    options.add(flatZincSolver.getCompilerFlag());

    for (Path dir : searchDirectories) {
      options.add("-I");
      options.add(dir.toAbsolutePath().toString());
    }

    options.add(COMPILER_OUTPUT_FZN);
    options.add(fznFile.getAbsolutePath());

    options.add(mznFile.getAbsolutePath());

    return options;
  }

}
