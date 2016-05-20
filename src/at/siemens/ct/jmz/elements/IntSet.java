package at.siemens.ct.jmz.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import at.siemens.ct.jmz.expressions.integer.IntExpression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * A set of integers that is defined as all integers between a lower and an upper bound.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntSet implements NamedElement, IntSetExpression {

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
    mustHaveName();
    return String.format("set of int: %s = %s;", name, getRange());
  }

  /**
   * @return the name of this set, if it is not {@code null}, or else its range in the form {@code lb..ub}.
   */
  @Override
  public String use() {
    return nameOrRange();
  }

  private String nameOrRange() {
    return name != null ? name : getRange();
  }

  private String getRange() {
    return String.format("%s..%s", lb.use(), ub.use());
  }

  /**
   * Creates a nameless set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
   * 
   * @param possibleValues
   * @return a reference to the created set.
   */
  public static IntSet deriveRange(Collection<Integer> possibleValues) {
    return deriveRange(null, possibleValues);
  }
  
  /**
   * Creates a nameless set covering the given values ({@code min(possibleValues)..max(possibleValues)}).
   * 
   * @param possibleValues
   * @return a reference to the created set.
   */
  public static IntSet deriveRange(Integer... possibleValues) {
    return deriveRange(null, Arrays.asList(possibleValues));
  }
  
  /**
   * Creates a set with the given name covering the given values ({@code min(possibleValues)..max(possibleValues)}).
   * 
   * @param name
   * @param possibleValues
   * @return a reference to the created set.
   */
  public static IntSet deriveRange(String name, Collection<Integer> possibleValues) {
    Collection<Integer> possibleValuesExceptNull = new HashSet<>(possibleValues);
    possibleValuesExceptNull.remove(null);
    if (possibleValuesExceptNull.isEmpty()) {
      throw new IllegalArgumentException("Collection of possible values is empty.");
    }
    return new IntSet(name, Collections.min(possibleValuesExceptNull),
        Collections.max(possibleValuesExceptNull));
  }
  
  /**
   * Creates a set with the given name covering the given values ({@code min(possibleValues)..max(possibleValues)}).
   * 
   * @param name
   * @param possibleValues
   * @return a reference to the created set.
   */
  public static IntSet deriveRange(String name, Integer... possibleValues) {
	  return deriveRange(name,Arrays.asList(possibleValues));
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

  @Override
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
      }
      return null;
    }
  }

}
