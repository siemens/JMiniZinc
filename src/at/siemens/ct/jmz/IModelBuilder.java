package at.siemens.ct.jmz;

import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.IntArrayConstant;
import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.IntVar;

/**
 * An interface for MiniZinc model builders.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public interface IModelBuilder {

  /**
   * Creates an integer constant in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   */
  IntConstant createIntConstant(String name, int value);

  /**
   * Creates an array of integer constants in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   */
  IntArrayConstant createIntArrayConstant(String name, IntSet range, IntSet type, int[] values);

  /**
   * @see this{@link #createIntSet(String, IntConstant, IntConstant)}
   */
  IntSet createIntSet(String name, int lb, int ub);

  /**
   * @see this{@link #createIntSet(String, IntConstant, IntConstant)}
   */
  IntSet createIntSet(String name, int lb, IntConstant ub);

  /**
   * Creates a set of integers with the given bounds in the MiniZinc model.
   * 
   * @param name
   * @param lb
   *          the lower bound
   * @param ub
   *          the upper bound
   * @return a reference to this set for future use.
   */
  IntSet createIntSet(String name, IntConstant lb, IntConstant ub);

  /**
   * Creates an integer variable in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   */
  IntVar createIntVar(String string, IntSet type);

  /**
   * Streams the elements that have been built.
   */
  Stream<Element> elements();

  /**
   * Removes all created elements.
   */
  void reset();

}