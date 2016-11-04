package at.siemens.ct.jmz.elements;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public class BasicTypeInst<T> extends TypeInst<T, T> {

  private String name;
  private SetExpression<T> type;

  protected BasicTypeInst(BasicTypeInst<T> innerTypeInst) {
    this(innerTypeInst.name, innerTypeInst.type, innerTypeInst.value);
  }

  public BasicTypeInst(String name, SetExpression<T> type) {
    this(name, type, null);
  }

  public BasicTypeInst(String name, SetExpression<T> type, Expression<T> value) {
    super();
    this.name = name;
    this.type = type;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public SetExpression<T> getType() {
    return type;
  }

  public Expression<T> getValue() {
    return value;
  }

  @Override
  public String use() {
    return getName();
  }

  @Override
  public Pattern getPattern() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T parseValue(String value) {
    // TODO Auto-generated method stub
    return null;
  }

}
