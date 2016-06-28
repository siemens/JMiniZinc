package at.siemens.ct.jmz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.NamedElement;
import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * A MiniZinc model builder.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ModelBuilder implements IModelBuilder {

  private List<Element> allElements = new LinkedList<>();
  private Map<String, NamedElement> namedElements = new HashMap<>();
  private ConstraintRegistry constraintRegistry = new ConstraintRegistry();
  private boolean relaxedProblem = false;

  private void addElement(Element element) {
    boolean ignore = false;

    if (element instanceof NamedElement) {
      registerNamedElement(((NamedElement) element));
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

  private void registerNamedElement(NamedElement namedElement) {
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
  public NamedElement getElementByName(String name) {
    return namedElements.get(name);
  }

  @Override
  public Collection<String> getConstraintGroups() {
    return constraintRegistry.getGroups();
  }

  @Override
  public void rebuildIgnoringConstraintGroups(String... constraintGroups) {
    Collection<Element> copiedElements = new ArrayList<>(allElements);
    reset();
    constraintRegistry.ignoreGroups(constraintGroups);
    add(copiedElements);
    relaxedProblem = constraintGroups.length > 0;
  }

  @Override
  public boolean isRelaxed() {
    return relaxedProblem;
  }

}
