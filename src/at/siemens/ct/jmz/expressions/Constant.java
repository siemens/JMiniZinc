package at.siemens.ct.jmz.expressions;

import java.util.Set;

public class Constant<T> implements Expression<T> {

  private Expression<Set<T>> type;
  private T value;

  public Constant(Expression<Set<T>> type, T value) {
    this.type = type;
    this.value = value;
  }

  public Expression<Set<T>> getType() {
    return type;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String use() {
    return String.valueOf(value);
  }

}
