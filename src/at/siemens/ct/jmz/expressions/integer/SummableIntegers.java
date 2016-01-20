package at.siemens.ct.jmz.expressions.integer;

import at.siemens.ct.jmz.elements.IntArrayVar;
import at.siemens.ct.jmz.elements.IntVar;

public interface SummableIntegers {

  /**
   * @return {@code true} if this is only one value (e.g. an {@link IntVar}, in contrast to an {@link IntArrayVar}).
   */
  boolean isSingleton();

  String nameOrValue();

  default String toSum() {
    if (isSingleton()) {
      return nameOrValue();
    } else {
      return String.format("sum(%s)", nameOrValue());
      // TODO: replace by object representing function call
    }
  }

}
