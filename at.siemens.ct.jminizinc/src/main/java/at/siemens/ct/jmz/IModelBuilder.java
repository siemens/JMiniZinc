/**
 * Copyright © Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz;

import java.util.Collection;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.TypeInst;

/**
 * An interface for MiniZinc model builders.
 * 
 * @author © Siemens AG, 2016
 */
public interface IModelBuilder {

  /**
   * Streams the elements that have been built.
   */
  Stream<Element> elements();

  /**
   * Removes all created elements.
   */
  void reset();

  /**
   * Adds the given elements to the MiniZinc model.
   */
  void add(Element... elements);

  /**
   * @see #add(Element...)
   */
  void add(Collection<? extends Element> elements);

  TypeInst<?, ?> getElementByName(String name);

  /**
   * @return the set of constraint groups known by the model builder's {@link ConstraintRegistry}.
   */
  Collection<String> getConstraintGroups();

  /**
   * Rebuilds the model while ignoring the given groups of constraints.
   * 
   * @param constraintGroups
   */
  IModelBuilder rebuildIgnoringConstraintGroups(String... constraintGroups);

  /**
   * Rebuilds the model while replacing certain constraints by relaxed variants.
   * 
   * @param constraintRelaxation
   */
  IModelBuilder rebuildChangingConstraints(IConstraintRelaxation constraintRelaxation);

  /**
   * Checks if the current model corresponds to a relaxed problem.
   * 
   * @return {@code true} iff any constraints are currently ignored.
   */
  boolean isRelaxed();

}