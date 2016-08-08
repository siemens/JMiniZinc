package at.siemens.ct.jmz.expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.siemens.ct.jmz.elements.NamedElement;

public abstract class Variable<T> implements NamedElement, Expression<T> {

  protected String name;

  public Variable(String name) {
    super();
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String use() {
    return getName();
  }

  public abstract Pattern getPattern();

  public T parseResults(String results) {
    Pattern valuePattern = getPattern();
    Pattern solutionPattern = Pattern
        .compile(name + "\\s*=\\s*(" + valuePattern.pattern() + ");");
    Matcher matcher = solutionPattern.matcher(results);
    if (matcher.find()) {
      return parseValue(matcher.group(1));
    }
    throw new IllegalArgumentException(
        "No match found for variable " + this.getName() + " in the following results: " + results);
  }

  public abstract T parseValue(String value);

}
