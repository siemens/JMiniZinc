package at.siemens.ct.jmz.expressions;

public interface Expression {

  /**
   * @return a string representation (e.g. its name or value) to be used in a MiniZinc program.
   */
  String use();

}
