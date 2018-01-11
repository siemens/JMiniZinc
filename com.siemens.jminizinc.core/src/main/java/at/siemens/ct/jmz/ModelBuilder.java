/**
 * Copyright Siemens AG, 2016
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package at.siemens.ct.jmz;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.TypeInst;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * A MiniZinc model builder.
 * 
 * @author Copyright Siemens AG, 2016
 */
public class ModelBuilder implements IModelBuilder {

  private List<Element> allElements = new LinkedList<>();
  private Map<String, TypeInst<?, ?>> namedElements = new HashMap<>();
  private ConstraintRegistry constraintRegistry = new ConstraintRegistry();
  private boolean relaxedProblem = false;

  private void addElement(Element element) {
    boolean ignore = false;

    if (element instanceof TypeInst<?, ?>) {
      registerNamedElement(((TypeInst<?, ?>) element));
    }

    if (element instanceof Constraint) {
      Constraint constraint = (Constraint) element;
      registerConstraint(constraint);
      if (constraintRegistry.isIgnored(constraint))
        ignore = true;
    }

    if (!ignore) {
      allElements.add(element);
    }
  }

  private void registerNamedElement(TypeInst<?, ?> namedElement) {
    String name = namedElement.getName();
    if (namedElements.containsKey(name)) {
      throw new IllegalArgumentException("NamedElement with this name already exists: " + name);
    }
    namedElements.put(name, namedElement);
  }

  private void registerConstraint(Constraint constraint) {
    constraintRegistry.register(constraint);
  }

  @Override
  public Stream<Element> elements() {
    return allElements.stream();
  }

  @Override
  public void reset() {
    allElements.clear();
    namedElements.clear();
    constraintRegistry.reset();
    relaxedProblem = false;
  }

  @Override
  public void add(Element... elements) {
    for (Element element : elements) {
      addElement(element);
    }
  }

  @Override
  public void add(Collection<? extends Element> elements) {
    for (Element element : elements) {
      addElement(element);
    }
  }

  @Override
  public TypeInst<?, ?> getElementByName(String name) {
    return namedElements.get(name);
  }

  @Override
  public Collection<String> getConstraintGroups() {
    return constraintRegistry.getGroups();
  }

  ConstraintRegistry getConstraintRegistry() {
    return constraintRegistry;
  }

  @Override
  public IModelBuilder rebuildIgnoringConstraintGroups(String... constraintGroups) {
    ModelBuilder newModelBuilder = new ModelBuilder();
    newModelBuilder.getConstraintRegistry().ignoreGroups(constraintGroups);
    newModelBuilder.add(allElements);
    newModelBuilder.setRelaxed(constraintGroups.length > 0);
    return newModelBuilder;
  }

  @Override
  public IModelBuilder rebuildChangingConstraints(IConstraintRelaxation constraintRelaxation) {
    ModelBuilder newModelBuilder = new ModelBuilder();
    boolean relaxed = false;
    for (Element element : allElements) {
      Element replacement = element;
      if (element instanceof Constraint) {
        Constraint constraint = (Constraint) element;
        if (constraintRelaxation.isRelaxed(constraint)) {
          relaxed = true;
          replacement = constraintRelaxation.getRelaxed(constraint);
        }
      }
      newModelBuilder.add(replacement);
    }
    newModelBuilder.setRelaxed(relaxed);
    return newModelBuilder;
  }

  void setRelaxed(boolean relaxed) {
    this.relaxedProblem = relaxed;
  }

  @Override
  public boolean isRelaxed() {
    return relaxedProblem;
  }

}
