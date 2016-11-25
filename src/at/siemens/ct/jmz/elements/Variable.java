package at.siemens.ct.jmz.elements;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents a MiniZinc variable.
 * This class can be used to build variable {@link BasicTypeInst}s and {@link Set}s.
 * To build a variable array, an {@link Array} of {@link Variable}s has to be created.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of this variable (e.g. {@link Integer})
 * @param <V> The data type of this variable´s value (e.g. {@link Integer} or {@link java.util.Set}{@code <Integer>})
 */
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
