package at.siemens.ct.jmz.elements;

import java.util.Collection;
import java.util.Collections;

public class IntSet implements Element {

  /**
   * Represents the set of all integers
   */
  public static final IntSet ALL_INTEGERS = new IntSet("int", null, null);

  protected String name;
  private IntConstant lb;
  private IntConstant ub;

  public IntSet(String name, int lb, int ub) {
    this(name, new IntConstant(lb), new IntConstant(ub));
  }

  public IntSet(String name, int lb, IntConstant ub) {
    this(name, new IntConstant(lb), ub);
  }

  public IntSet(String name, IntConstant lb, IntConstant ub) {
    super();
    this.name = name;
    this.lb = lb;
    this.ub = ub;
  }

  public IntSet(int lb, int ub) {
    this(null, lb, ub);
  }

  public IntSet(int lb, IntConstant ub) {
    this(null, lb, ub);
  }

  public IntSet(IntConstant lb, IntConstant ub) {
    this(null, lb, ub);
  }

  @Override
  public String declare() {
    return String.format("set of int: %s = %s;", name, getRange());
  }

  /**
   * @return the name of this set, if it is not {@code null}, or else its range in the form {@code lb..ub}.
   */
  public String nameOrRange() {
    return name != null ? name : getRange();
  }

  private String getRange() {
    return String.format("%s..%s", lb.nameOrValue(), ub.nameOrValue());
  }

  /**
   * Creates a set with the given name covering the given values ({@code min(possibleValues)..max(possibleValues)}).
   * 
   * @param name
   * @param possibleValues
   * @return a reference to the created set.
   */
  public static IntSet deriveRange(String name, Collection<Integer> possibleValues) {
    return new IntSet(name, Collections.min(possibleValues), Collections.max(possibleValues));
  }

  @Override
  public boolean isVariable() {
    return false;
  }

  protected String getName() {
    return name;
  }

  protected IntConstant getLb() {
    return lb;
  }

  protected IntConstant getUb() {
    return ub;
  }

}
