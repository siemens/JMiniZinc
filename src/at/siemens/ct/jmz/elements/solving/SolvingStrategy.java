package at.siemens.ct.jmz.elements.solving;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.expressions.integer.IntegerVariable;

public abstract class SolvingStrategy implements Element {

  public static final SolvingStrategy SOLVE_SATISFY = new SolveSatisfy();

  public static SolvingStrategy minimize(IntegerVariable objective) {
    return new Optimize(OptimizationType.MIN, objective);
  }

  public static SolvingStrategy maximize(IntegerVariable objective) {
    return new Optimize(OptimizationType.MAX, objective);
  }

  public static SolvingStrategy optimize(OptimizationType type, IntegerVariable objective) {
    return new Optimize(type, objective);
  }

  public static SolvingStrategy minimize(IntegerVariable objective, String searchAnnotation) {
    return new Optimize(OptimizationType.MIN, objective, searchAnnotation);
  }

  public static SolvingStrategy maximize(IntegerVariable objective, String searchAnnotation) {
    return new Optimize(OptimizationType.MAX, objective, searchAnnotation);
  }

  public static SolvingStrategy optimize(OptimizationType type, IntegerVariable objective,
      String searchAnnotation) {
    return new Optimize(type, objective, searchAnnotation);
  }
}
