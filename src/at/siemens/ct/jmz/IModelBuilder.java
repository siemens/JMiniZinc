package at.siemens.ct.jmz;

import java.util.Collection;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.NamedElement;

/**
 * An interface for MiniZinc model builders.
 * 
 * @author z003ft4a (Richard Taupe)
 *
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

  NamedElement getElementByName(String name);

}