package at.siemens.ct.jmz.elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Variable<T> implements Element {

  protected String name;

  public Variable(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
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
    return null;
  }

  public abstract T parseValue(String value);

}
