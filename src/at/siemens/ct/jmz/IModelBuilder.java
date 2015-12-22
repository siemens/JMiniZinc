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
   * 
   * @see IntConstant#IntConstant(String, Integer)
   */
  IntConstant createIntConstant(String name, int value);

  /**
   * Creates an array of integer constants in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   * 
   * @see IntArrayConstant#IntArrayConstant(String, IntSet, IntSet, int[])
   */
  IntArrayConstant createIntArrayConstant(String name, IntSet range, IntSet type, int[] values);

  /**
   * Creates an array of integer variables in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   * 
   * @see IntArrayVar#IntArrayVar(String, IntSet, IntSet)
   */
  IntArrayVar createIntArrayVar(String name, IntSet range, IntSet type);

  /**
   * @see this{@link #createIntSet(String, IntConstant, IntConstant)}
   */
  IntSet createIntSet(String name, int lb, int ub);

  /**
   * @see this{@link #createIntSet(String, IntConstant, IntConstant)}
   */
  IntSet createIntSet(String name, int lb, IntConstant ub);

  /**
   * Creates a set of integers, using {@code lb} as the lower bound and a newly created {@link IntConstant} using
   * {@code ubName} and {@code ubValue} as the upper bound.
   * 
   * @param name
   * @param lb
   * @param ubName
   * @param ubValue
   * @return a reference to this set for future use.
   * 
   * @see #createIntSet(String, IntConstant, IntConstant)
   */
  IntSet createIntSet(String name, int lb, String ubName, int ubValue);

  /**
   * Creates a set of integers with the given bounds in the MiniZinc model.
   * 
   * @param name
   * @param lb
   *          the lower bound
   * @param ub
   *          the upper bound
   * @return a reference to this set for future use.
   * 
   * @see IntSet#IntSet(String, IntConstant, IntConstant)
   */
  IntSet createIntSet(String name, IntConstant lb, IntConstant ub);

  /**
   * Creates an integer variable in the MiniZinc model.
   * 
   * @return a reference to this constant for future use.
   * 
   * @see IntVar#IntVar(String, IntSet)
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