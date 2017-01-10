/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz.elements.solving;

import at.siemens.ct.jmz.expressions.integer.IntegerVariable;

/**
 * @author © Siemens AG, 2016
 */
public class Optimize extends SolvingStrategy {

  private OptimizationType type;
  private IntegerVariable objective;
  private String searchAnnotation;

  public Optimize(OptimizationType type, IntegerVariable objective) {
    this.type = type;
    this.objective = objective;
  }

  /**
   * TODO: searchAnnotation should not be a string but a JMiniZinc object
   * TODO: searchAnnotation should also be supported by {@link SolveSatisfy}
   */
  public Optimize(OptimizationType type, IntegerVariable objective, String searchAnnotation) {
    this.type = type;
    this.objective = objective;
    this.searchAnnotation = searchAnnotation;
  }

  @Override
  public String declare() {
    StringBuilder sb = new StringBuilder();
    sb.append("solve");
    if (searchAnnotation != null && !searchAnnotation.isEmpty()) {
      sb.append(" :: ");
      sb.append(searchAnnotation);
    }
    sb.append(' ');
    sb.append(type);
    sb.append(' ');
    sb.append(objective.getName());
    sb.append(';');
    return sb.toString();
  }

}
