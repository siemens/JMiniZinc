package at.siemens.ct.jmz.expressions;

/**
 * @author © Siemens AG, 2016
 * 
 * @param <T> The data type of the expression (e.g. {@link Integer} or {@link Boolean})
 */
public interface Expression<T> {

  /**
   * @return a string representation (e.g. its name or value) to be used in a MiniZinc program.
   */
  String use();

}
