package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

public abstract class Variable<T, V> extends TypeInst<T, V> {

  private TypeInst<T, ?> innerTypeInst;

  public Variable(TypeInst<T, ?> innerTypeInst) {
    if (innerTypeInst instanceof BasicTypeInst<?> || innerTypeInst instanceof Set<?>) {
      this.innerTypeInst = innerTypeInst;
    } else {
      throw new IllegalArgumentException(String.format("Variables can only be formed of %ss or %ss",
          BasicTypeInst.class.getSimpleName(), Set.class.getSimpleName()));
    }
	}

  public Variable(String name, SetExpression<T> type) {
    this(name, type, null);
  }

  public Variable(String name, SetExpression<T> type, Expression<V> value) {
    this(new BasicTypeInst<T>(name, type));
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
    return "var " + innerTypeInst.declare(value);
	}

}
