package at.siemens.ct.jmz.elements;

public interface SolvingStrategy extends Element {
  static final SolvingStrategy SOLVE_SATISFY = new SolveSatisfy();
}
