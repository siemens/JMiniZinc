package at.siemens.ct.jmz.expressions.set;

import java.util.Set;

import at.siemens.ct.jmz.elements.BasicTypeInst;

public class StringSetExpression implements SetExpression<String> {

  private Set<String> values;
  private SetExpression<String> ENUM_UNIVERSE = new at.siemens.ct.jmz.elements.Set<>(
    new BasicTypeInst<>("enum", null));

  public StringSetExpression(Set<String> values) {
    this.values = values;
  }

  @Override
  public Boolean contains(String value) {
    return values.contains(value);
  }

  @Override
  public SetExpression<String> getType() {
    return ENUM_UNIVERSE;
  }

  @Override
  public String use() {
    return String.join(",", values);
  }

  @Override
  public SetExpression<String> substitute(String name, Object value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<String> value() {
    return values;
  }
}
