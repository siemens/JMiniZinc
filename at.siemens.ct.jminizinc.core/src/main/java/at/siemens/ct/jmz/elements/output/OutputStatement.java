/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements.output;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * @deprecated Currently, we do not support custom output statements because our result parser relies on the default
 *             output format.
 * @see Variable#getPattern()
 * @see Variable#parseResults(String)
 * @author Copyright Siemens AG, 2016
 */
@Deprecated
public abstract class OutputStatement implements Element {

  protected abstract Collection<String> getElements();

  /**
   * TODO: support {@link ListComprehension}
   * TODO: differentiate between string literals and function calls
   */
  @Override
  public String declare() {
    StringBuilder declaration = new StringBuilder();
    declaration.append("output [");
    declaration.append(getElements().stream().collect(Collectors.joining(", ")));
    declaration.append("];");
    return declaration.toString();
  }

}
