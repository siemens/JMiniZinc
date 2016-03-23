package at.siemens.ct.jmz.elements;

import java.util.Collection;
import java.util.Collections;

import at.siemens.ct.jmz.expressions.comprehension.IteratorExpression;
import at.siemens.ct.jmz.expressions.integer.IntExpression;

public class IntSet implements NamedElement {

  /**
   * Represents the set of all integers
   */
  public static final IntSet ALL_INTEGERS = new IntSet("int", null, null);

  protected String name;
  private IntExpression lb;
  private IntExpression ub;

  public IntSet(String name, int lb, int ub) {
    this(name, new IntConstant(lb), new IntConstant(ub));
  }

  public IntSet(String name, int lb, IntExpression ub) {
    this(name, new IntConstant(lb), ub);
  }

  public IntSet(String name, IntExpression lb, IntExpression ub) {
    super();
    this.name = name;
    this.lb = lb;
    this.ub = ub;
  }

  public IntSet(int lb, int ub) {
    this(null, lb, ub);
  }

  public IntSet(int lb, IntExpression ub) {
    this(null, lb, ub);
  }

  public IntSet(IntExpression lb, IntExpression ub) {
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
    return String.format("%s..%s", lb.use(), ub.use());
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
  public String getName() {
    return name;
  }

  protected IntExpression getLb() {
    return lb;
  }

  protected IntExpression getUb() {
    return ub;
  }

  /**
   * Creates an iterator over this set, using {@code nameOfIterator} as the name of the local variable.
   * 
   * @param nameOfIterator
   * @return an iterator over this set
   */
  public IteratorExpression iterate(String nameOfIterator) {
    return new IteratorExpression(this, nameOfIterator);
  }

  /**
   * Checks if the given value is contained by this set. If this cannot be determined (e.g. if the set is bounded by
   * constants whose values are not known), {@code null} is returned.
   * 
   * @param value
   * @return
   */
  public Boolean contains(int value) {
    if (lb == null && ub == null) {
      return true;
    } else if (lb == null) {
      return ub.isGreaterThanOrEqualTo(value);
    } else if (ub == null) {
      return lb.isLessThanOrEqualTo(value);
    } else {
      Boolean ubGEQ = ub.isGreaterThanOrEqualTo(value);
      Boolean lbLEQ = lb.isLessThanOrEqualTo(value);
      if (ubGEQ != null && lbLEQ != null) {
        return ubGEQ && lbLEQ;
      } else {
        return null;
      }
    }
  }

}
