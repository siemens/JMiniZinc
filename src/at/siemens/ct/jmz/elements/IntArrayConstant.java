package at.siemens.ct.jmz.elements;

import java.util.Collection;
import java.util.List;

import at.siemens.ct.common.utils.ArrayUtils;
import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.array.IntExplicitList;

/**
 * Represents an array of integer constants.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntArrayConstant implements IntArray {

  private String name;
  private Collection<IntSet> range;
  private IntSet type;
  private IntExplicitList values;

  /**
   * Creates an array of integer constants.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the range (must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          the collection of actual values
   */
  public IntArrayConstant(String name, IntSet range, IntSet type, Collection<Integer> values) {
    this(name, ListUtils.fromElements(range), type, values);
  }

  /**
   * Creates a multi-dimensional array of integer constants.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the ranges (each must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          the collection of actual values
   */
  public IntArrayConstant(String name, Collection<IntSet> range, IntSet type,
      Collection<Integer> values) {
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = new IntExplicitList(range, type, values);
  }

  /**
   * @see #IntArrayConstant(String, IntSet, IntSet, Collection)
   */
  public IntArrayConstant(String name, IntSet range, IntSet type, int[] values) {
    this(name, range, type, ListUtils.fromArray(values));
  }

  public IntArrayConstant(String name, List<IntSet> range, IntSet type, int[][] values) {
    this(name, range, type, ArrayUtils.toOneDimensionalList(values));
  }

  @Override
  public Collection<IntSet> getRange() {
    return range;
  }

  @Override
  public String declare() {
    return String.format("array[%s] of %s: %s = %s;", declareRange(), type.nameOrRange(), name,
        values.coerce());
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean isVariable() {
    return false;
  }

  @Override
  public String nameOrValue() {
    return name != null ? name : values.nameOrValue();
  }

}
