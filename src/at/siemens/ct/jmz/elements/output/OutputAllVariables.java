package at.siemens.ct.jmz.elements.output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Variable;

public class OutputAllVariables extends OutputStatement {

  private List<Element> variables;

  /**
   * Filters all variables from the given collection of {@link Element}s. They will be printed, each in a separate line,
   * following the pattern {@code name=value}.
   */
  public OutputAllVariables(Collection<Element> elements) {
    this(elements.stream());
  }

  /**
   * Filters all variables from the given stream of {@link Element}s. They will be printed, each in a separate line,
   * following the pattern {@code name=value}.
   */
  public OutputAllVariables(Stream<Element> elements) {
    this.variables = elements.filter(Element::isVariable).collect(Collectors.toList());
  }

  @Override
  protected Collection<String> getElements() {
    return variables.stream().map(v -> outputVariable((Variable) v)).collect(Collectors.toList());
  }

  private static String outputVariable(Variable v) {
    String vname = v.getName();
    return String.format("\"%s = \\(%s);\\n\"", vname, vname);
  }

}
