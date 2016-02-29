package at.siemens.ct.jmz.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.common.utils.ListUtils;
import at.siemens.ct.jmz.expressions.comprehension.Comprehension;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

public class IntArrayVar extends Variable<int[]> implements IntArray {

  private Collection<IntSet> range;
  private IntSet type;
  private ListComprehension values;

  /**
   * @see #IntArrayVar(String, IntSet, IntSet, Comprehension)
   */
  public IntArrayVar(String name, IntSet range, IntSet type) {
    this(name, range, type, null);
  }

  /**
   * @see #IntArrayVar(String, Collection, IntSet, Comprehension)
   */
  public IntArrayVar(String name, Collection<IntSet> range, IntSet type) {
    this(name, range, type, null);
  }

  /**
   * Creates a multi-dimensional array of integer variables.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the ranges (each must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          a list comprehension (may be {@code null})
   */
  public IntArrayVar(String name, Collection<IntSet> range, IntSet type, ListComprehension values) {
    super(name);
    this.range = range;
    this.type = type;
    this.values = values;
  }

  /**
   * Creates an array of integer variables.
   * 
   * @param name
   *          the name of the array
   * @param range
   *          the range (must be finite)
   * @param type
   *          the type of each integer (may be infinite)
   * @param values
   *          a list comprehension (may be {@code null})
   */
  public IntArrayVar(String name, IntSet range, IntSet type, ListComprehension values) {
    this(name, ListUtils.fromElements(range), type, values);
  }

  @Override
  public Collection<IntSet> getRange() {
    return range;
  }

  @Override
  public String declare() {
    StringBuilder declaration = new StringBuilder();
    declaration
        .append(String.format("array[%s] of var %s: %s", declareRange(), type.nameOrRange(), name));

    if (values != null) {
      declaration.append(" = ");
      declaration.append(values.coerce());
    }

    declaration.append(";");
    return declaration.toString();
  }

  @Override
  public String use() {
    return name;
  }

  @Override
  public Pattern getPattern() {
    String intPattern = IntVar.getPatternStatic().pattern();
    return Pattern.compile(
        "array(\\d)d\\((\\d+..\\d+, )+\\[((" + intPattern + ", )*" + intPattern + ")\\]\\)");
  }

  @Override
  public int[] parseValue(String value) {
    Matcher matcher = getPattern().matcher(value);
    if (matcher.find()) {
      parseDimensions(matcher);
      return parseValue(matcher);
    } else {
      throw new IllegalArgumentException("Not an integer array: " + value);
    }
  }

  private void parseDimensions(Matcher matcher) {
    String matchDimensions = matcher.group(1);
    int parsedDimensions = Integer.valueOf(matchDimensions);
    checkParsedDimensions(parsedDimensions);
  }

  private int[] parseValue(Matcher matcher) {
    String matchValue = matcher.group(3);
    int[] parsedValue = Arrays.stream(matchValue.split(", ")).mapToInt(Integer::valueOf)
        .toArray();
    checkParsedValue(parsedValue);
    return parsedValue;
  }

  private void checkParsedDimensions(int parsedDimensions) {
    int expectedDimensions = this.getDimensions();
    if (expectedDimensions != parsedDimensions) {
      throw new IllegalArgumentException("Unexpected number of dimensions: expected "
          + expectedDimensions + ", got " + parsedDimensions);
    }
  }

  private void checkParsedValue(int[] parsedValue) {
    for (int value : parsedValue) {
      Boolean valueInDomain = type.contains(value);
      if (valueInDomain == Boolean.FALSE) {
        throw new IllegalArgumentException("Value not in domain: " + value);
      }
    }
  }

}
