package at.siemens.ct.jmz.elements;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

public class BooleanVar extends Variable<Boolean> implements BooleanExpression {

  private BooleanExpression value;

  public BooleanVar(String name) {
    this(name, null);
  }

  public BooleanVar(String name, boolean value) {
    this(name, BooleanConstant.valueOf(value));
  }

  public BooleanVar(String name, BooleanExpression value) {
    super(name);
    this.value = value;
  }

  @Override
  public String declare() {
    mustHaveName();
    StringBuilder declaration = new StringBuilder();
    declaration.append(String.format("var bool: %s", name));

    if (value != null) {
      declaration.append(" = ");
      declaration.append(value.use());
    }

    declaration.append(";");
    return declaration.toString();
  }

  @Override
  public Pattern getPattern() {
    return getPatternStatic();
  }

  static Pattern getPatternStatic() {
    return Pattern.compile("true|false");
  }

  @Override
  public Boolean parseValue(String string) {
    return Boolean.parseBoolean(string);
  }

}
