package at.siemens.ct.jmz.elements;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class Set<T> extends TypeInst<T, java.util.Set<T>> implements SetExpression<T> {

  private BasicTypeInst<T> innerTypeInst;

  public Set(BasicTypeInst<T> innerTypeInst) {
    this.innerTypeInst = innerTypeInst;
  }

  public Set(String name, SetExpression<T> value) {
    this(name, value, value);
  }

  public Set(String name, SetExpression<T> type, SetExpression<T> value) {
    this.innerTypeInst = new BasicTypeInst<>(name, type);
    this.value = value;
  }

  @Override
  public String getName() {
    return innerTypeInst.getName();
  }

  @Override
  public SetExpression<T> getType() {
    return innerTypeInst.getType();
  }

  @Override
  public String declare(Expression<?> value) {
    return "set of " + innerTypeInst.declare(value);
  }

  @Override
  public Pattern getPattern() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public java.util.Set<T> parseValue(String value) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String use() {
    String name = getName();
    return name != null ? name : value.use();
  }

  @Override
  public Boolean contains(T value) {
    if (this.value != null) {
      return ((SetExpression<T>) this.value).contains(value);
    } else {
      return null;
    }
  }

}
