package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.elements.BasicTypeInst;
import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class BasicBoolean extends BasicTypeInst<Boolean> implements BooleanExpression {

  public BasicBoolean(BasicTypeInst<Boolean> innerTypeInst) {
    super(innerTypeInst);
  }

  public BasicBoolean(String name, SetExpression<Boolean> type) {
    super(name, type);
  }

  public BasicBoolean(String name, SetExpression<Boolean> type, Expression<Boolean> value) {
    super(name, type, value);
  }

}
