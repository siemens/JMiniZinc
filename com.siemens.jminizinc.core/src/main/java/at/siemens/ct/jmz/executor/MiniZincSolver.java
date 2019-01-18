/**
 * Copyright Siemens AG, 2019
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.executor;

import java.util.Arrays;
import java.util.List;

/**
 * Enables the choice between different MiniZinc solvers (like Gecode, G12, ...)
 * 
 * @author Copyright Siemens AG, 2019
 */
public enum MiniZincSolver {

  GECODE("minizinc"),
  G12_fd("minizinc", "-G", "g12_fd"),
  G12_lazyfd("minizinc", "-G", "g12_lazyfd", "-b", "lazyfd"),
  G12_MIP("minizinc", "-G", "linear", "-b", "mip"),
  CBC("mzn-cbc", "-G", "linear");
  
  private String executableName;
  private String[] options;

  private MiniZincSolver(String executableName, String... options) {
    this.executableName = executableName;
    this.options = options;
  }

  public String getExecutableName() {
    return executableName;
  }

  public List<String> getOptions() {
    return Arrays.<String> asList(options);
  }

}
