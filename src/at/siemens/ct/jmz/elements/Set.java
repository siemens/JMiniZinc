package at.siemens.ct.jmz.elements;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.SetExpression;

/**
 * Represents a MiniZinc set.
 * The type of elements of the set is determined by the {@link TypeInst} contained within an instance of this class.
 * If the set shall be variable, it has to be contained by a {@link Variable}.
 * 
 * @author © Siemens AG, 2016
 *
 * @param <T> The primitive type of the elements in this set (e.g. {@link Integer})
 */
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
    String elementPattern = innerTypeInst.getPattern().pattern();
    return Pattern.compile(
        "\\{((" + elementPattern + ", )*" + elementPattern + ")\\}\\)");
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
