package at.siemens.ct.jmz.elements.output;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.Variable;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;

/**
 * @deprecated Currently, we do not support custom output statements because our result parser relies on the default
 *             output format.
 * @see Variable#getPattern()
 * @see Variable#parseResults(String)
 * @author z003ft4a (Richard Taupe)
 *
 */
@Deprecated
public abstract class OutputStatement implements Element {

  protected abstract Collection<String> getElements();

  /**
   * TODO: support {@link ListComprehension}
   * TODO: differentiate between string literals and function calls
   */
  @Override
  public String declare() {
    StringBuilder declaration = new StringBuilder();
    declaration.append("output [");
    declaration.append(getElements().stream().collect(Collectors.joining(", ")));
    declaration.append("];");
    return declaration.toString();
  }

}
