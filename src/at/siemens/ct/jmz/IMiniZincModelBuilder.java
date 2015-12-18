package at.siemens.ct.jmz;

import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.IntConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.elements.MiniZincElement;

/**
 * An interface for MiniZinc model builders.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public interface IMiniZincModelBuilder {

  /**
   * Creates an integer constant in the MiniZinc model.
   * 
   * @param name
   * @param value
   * @return a reference to this constant for future use.
   */
  IntConstant createIntConstant(String name, int value);

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
   * @param string
   * @param allIntegers
   * @return a reference to this constant for future use.
   */
  IntVar createIntVar(String string, IntSet allIntegers);

  /**
   * Streams the elements that have been built.
   */
  Stream<MiniZincElement> elements();

  /**
   * Removes all created elements.
   */
  void reset();

}