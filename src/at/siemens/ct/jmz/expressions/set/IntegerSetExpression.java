package at.siemens.ct.jmz.expressions.set;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Set;

public interface IntegerSetExpression extends SetExpression<Integer> {

  static final SetExpression<Integer> INTEGER_UNIVERSE = new Set<Integer>(
      new BasicTypeInst<>("int", null));

  @Override
  default SetExpression<Integer> getType() {
    return INTEGER_UNIVERSE;
  }
}
