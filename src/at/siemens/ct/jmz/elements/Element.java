package at.siemens.ct.jmz.elements;

public interface Element {

  /**
   * @return a declaration of this element in MiniZinc syntax
   */
  String declare();
}