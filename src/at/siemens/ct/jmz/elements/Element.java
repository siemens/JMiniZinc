package at.siemens.ct.jmz.elements;

/**
 * @author � Siemens AG, 2016
 */
public interface Element {

  /**
   * @return a declaration of this element in MiniZinc syntax
   */
  String declare();

  /**
   * @return {@code true} iff this element is a variable
   */
  default boolean isVariable() {
    return this instanceof Variable;
  }
}