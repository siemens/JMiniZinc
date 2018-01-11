/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements.output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Variable;

/**
 * @author Copyright Siemens AG, 2016
 */
public class OutputAllVariables extends OutputStatement {

  private List<Variable<?, ?>> variables;

  /**
   * Filters all variables from the given collection of {@link Element}s. They will be printed, each in a separate line,
   * following the pattern {@code name=value}.
   */
  public OutputAllVariables(Collection<Element> elements) {
    this(elements.stream());
  }

  /**
   * Filters all variables from the given stream of {@link Element}s. They will be printed, each in a separate line,
   * following the pattern {@code name=value}.
   */
  public OutputAllVariables(Stream<Element> elements) {
    this.variables = elements.filter(e -> e instanceof Variable).map(e -> (Variable<?, ?>) e)
        .collect(Collectors.toList());
  }

  @Override
  protected Collection<String> getElements() {
    return variables.stream().map(v -> outputVariable(v)).collect(Collectors.toList());
  }

  private static String outputVariable(Variable<?, ?> v) {
    String vname = v.getName();
    return String.format("\"%s = \\(%s);\\n\"", vname, vname);
  }

}
