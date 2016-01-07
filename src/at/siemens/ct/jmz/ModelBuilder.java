package at.siemens.ct.jmz;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;

/**
 * A MiniZinc model builder.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ModelBuilder implements IModelBuilder {

  private List<Element> elements = new LinkedList<>();

  private void addElement(Element element) {
    elements.add(element);
  }

  @Override
  public Stream<Element> elements() {
    return elements.stream();
  }

  @Override
  public void reset() {
    elements.clear();
  }

  @Override
  public void add(Element... elements) {
    for (Element element : elements) {
      addElement(element);
    }
  }

}
