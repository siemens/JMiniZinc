package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.List;

import at.siemens.ct.common.utils.ArrayUtils;
import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * Represents an array of integer constants.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntArrayConstant implements IntArray {

  private String name;
  private List<? extends IntSetExpression> range;
  private IntSet type;
  private IntExplicitList values;

  public IntArrayConstant(Collection<Integer> values) {
    throw new UnsupportedOperationException(
        "Please use IntExplicitList if you need an unnamed IntArrayConstant.");
    // TODO: may be refactored ... there exists a conceptual overlap (see IntExplicitList)
  }

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
  public IntArrayConstant(String name, IntSetExpression range, IntSet type,
      Collection<Integer> values) {
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
  public IntArrayConstant(String name, List<? extends IntSetExpression> range, IntSet type,
      Collection<Integer> values) {
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = new IntExplicitList(range, type, values);
  }

  /**
   * @see #IntArrayConstant(String, IntSetExpression, IntSet, Collection)
   */
  public IntArrayConstant(String name, IntSetExpression range, IntSet type, int[] values) {
    this(name, range, type, ListUtils.fromArray(values));
  }

  public IntArrayConstant(String name, List<? extends IntSetExpression> range, IntSet type,
      int[][] values) {
    this(name, range, type, ArrayUtils.toOneDimensionalList(values));
  }

  /**
   * The boolean values given here will be translated to integers ({@code true = 1}, {@code false = 0}).
   * TODO: Implement real boolean arrays
   */
  public IntArrayConstant(String name, List<? extends IntSetExpression> range, IntSet type,
      boolean[][] values) {
    this(name, range, type, ArrayUtils.boolToInt(values));
  }

  @Override
  public List<? extends IntSetExpression> getRange() {
    return range;
  }

  @Override
  public String declare() {
    mustHaveName();
    return String.format("array[%s] of %s: %s = %s;", declareRange(), type.use(), name,
        values.coerce());
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String use() {
    return name != null ? name : values.use();
  }

}
