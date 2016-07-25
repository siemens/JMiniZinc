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