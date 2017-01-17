/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.elements.solving.SolvingStrategy;

/**
 * A solving strategy that does not produce any "solve" statement.<br>
 * (This leads to incomplete mzn files, but is helpful for test cases that do not need solve items.)
 * 
 * @author Copyright Siemens AG, 2016
 */
public class NullSolvingStrategy extends SolvingStrategy {

  @Override
  public String declare() {
    return null;
  }

}
