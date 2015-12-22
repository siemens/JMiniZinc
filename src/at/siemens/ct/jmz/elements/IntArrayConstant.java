package at.siemens.ct.jmz.elements;

import java.util.Arrays;

/**
 * Represents an array of integer constants.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntArrayConstant implements Element {

  private String name;
  private IntSet range;
  private IntSet type;
  private int[] values;

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
   *          the array of actual values
   */
  public IntArrayConstant(String name, IntSet range, IntSet type, int[] values) {
    this.name = name;
    this.range = range;
    this.type = type;
    this.values = values;
  }

  @Override
  public String declare() {
    return String.format("array[%s] of %s: %s = %s;", range.getName(), type.getName(), name,
        Arrays.toString(values));
  }

}
