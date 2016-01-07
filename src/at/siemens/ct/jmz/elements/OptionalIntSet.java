package at.siemens.ct.jmz.elements;

/**
 * Represents a MiniZinc option type with an underlying integer set. This means that a variable of this type can either
 * be assigned an element of this set or, optionally, a null value.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class OptionalIntSet extends IntSet {

  public OptionalIntSet(IntSet intSet) {
    super("opt " + intSet.getName(), intSet.getLb(), intSet.getUb());
  }

}
