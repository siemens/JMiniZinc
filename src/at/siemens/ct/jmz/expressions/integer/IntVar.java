package at.siemens.ct.jmz.expressions.integer;

import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Variable;
import at.siemens.ct.jmz.expressions.array.IntArray;

public class IntVar extends Variable<Integer> implements IntExpression {

  private IntSet type;
  private IntExpression value;

  public IntVar(String name) {
    this(name, IntSet.ALL_INTEGERS);
  }

  public IntVar(String name, IntSet type) {
    this(name, type, null);
  }

  public IntVar(String name, IntSet type, IntExpression value) {
    super(name);
    this.type = type;
    this.value = value;
  }

  @Override
  public String declare() {
    mustHaveName();
    StringBuilder declaration = new StringBuilder();
    declaration.append(String.format("var %s: %s", type.use(), name));

    if (value != null) {
      declaration.append(" = ");
      declaration.append(value);
    }

    declaration.append(";");
    return declaration.toString();
  }

  /**
   * Creates an integer variable named {@code name} and assigns the sum of {@code summands} to it.
   * 
   * @param name
   * @param summands
   * @return a reference to the created variable.
   */
  public static IntVar createSum(String name, IntArray summands) {
    return new IntVar(name, IntSet.ALL_INTEGERS, new Sum(summands)); // TODO: tighter domain bounds?
  }

  @Override
  public String use() {
    return getName();
  }

  @Override
  public Pattern getPattern() {
    return getIntegerPattern();
  }

  public static Pattern getIntegerPattern() {
    return Pattern.compile("-?\\d+");
  }

  @Override
  public Integer parseValue(String string) {
    int i = Integer.parseInt(string);
    Boolean valueInDomain = type.contains(i);
    if (valueInDomain == Boolean.FALSE) {
      throw new IllegalArgumentException("Value not in domain: " + string);
    }
    return i;
  }

}
