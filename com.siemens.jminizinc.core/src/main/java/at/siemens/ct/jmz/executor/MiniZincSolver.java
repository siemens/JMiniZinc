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
 * Enables the choice between different MiniZinc solvers (like Chuffed, Gecode, ...)
 * 
 * @author Copyright Siemens AG, 2019
 */
public enum MiniZincSolver {

  CHUFFED("chuffed"),
  GECODE("gecode"),
  OSICBC("osicbc");
  
  private String executableName;
  private String[] options;

  private MiniZincSolver(String solverName) {
    this.executableName = "minizinc";
    this.options = new String[] { "--solver", solverName };
  }

  public String getExecutableName() {
    return executableName;
  }

  public List<String> getOptions() {
    return Arrays.<String> asList(options);
  }

}
