package at.siemens.ct.jmz;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.NamedElement;

/**
 * A MiniZinc model builder.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ModelBuilder implements IModelBuilder {

  private List<Element> allElements = new LinkedList<>();
  private Map<String, NamedElement> namedElements = new HashMap<>();

  private void addElement(Element element) {
    allElements.add(element);
    if (element instanceof NamedElement) {
      NamedElement namedElement = ((NamedElement) element);
      String name = namedElement.getName();
      if (namedElements.containsKey(name)) {
        throw new IllegalArgumentException("NamedElement with this name already exists: " + name);
      }
      namedElements.put(name, namedElement);
    }
  }

  @Override
  public Stream<Element> elements() {
    return allElements.stream();
  }

  @Override
  public void reset() {
    allElements.clear();
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

}
