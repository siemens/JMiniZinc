package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.integer.IntExpression;

/**
 * Represents a MiniZinc integer set type with an implicit null value that is an element of this set. In contrast to
 * {@link OptionalIntSet}, which uses the support for option types built in to MiniZinc (but not well supported yet by
 * MiniZinc itself), we use an integer to represent nulls here. This element can be obtained by
 * {@link #getNullElement()}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class PseudoOptionalIntSet extends IntSet {

  private String potentialName;

  public PseudoOptionalIntSet(IntSet intSet) {
    super(null, intSet.getLb().add(-1), intSet.getUb());
    potentialName = intSet.getName() + "0";
  }

  @Override
  public String declare() {
    // if this set has no name yet, it must get one now:
    if (name == null) {
      name = potentialName;
    }
    return super.declare();
  }

  /**
   * @return the element of this set that represents null values.
   */
  public IntExpression getNullElement() {
    return getLb();
  }

}
