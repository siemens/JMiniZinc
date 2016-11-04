package at.siemens.ct.jmz.expressions.set;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.elements.Set;

public interface BooleanSetExpression extends SetExpression<Boolean> {

  static final Set<Boolean> BOOLEAN_UNIVERSE = new Set<>(new BasicTypeInst<>("bool", null));

  @Override
  default SetExpression<Boolean> getType() {
    return BOOLEAN_UNIVERSE;
  }
}
