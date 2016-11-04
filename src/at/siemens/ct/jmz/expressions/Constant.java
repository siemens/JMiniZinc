package at.siemens.ct.jmz.expressions;

import java.util.Set;

public class Constant<T, V> implements Expression<V> {

  private Expression<Set<T>> type;
  private V value;

  public Constant(Expression<Set<T>> type, V value) {
    this.type = type;
    this.value = value;
  }

  public Expression<Set<T>> getType() {
    return type;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String use() {
    return String.valueOf(value);
  }

}
