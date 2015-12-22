package at.siemens.ct.jmz.elements.solving;

import at.siemens.ct.jmz.elements.Element;
import at.siemens.ct.jmz.elements.IntVar;
import at.siemens.ct.jmz.elements.NullSolvingStrategy;

public abstract class SolvingStrategy implements Element {

  public static final SolvingStrategy NULL = new NullSolvingStrategy();
  public static final SolvingStrategy SOLVE_SATISFY = new SolveSatisfy();

  public static SolvingStrategy minimize(IntVar objective) {
    return new Optimize(OptimizationType.MIN, objective);
  }

  public static SolvingStrategy maximize(IntVar objective) {
    return new Optimize(OptimizationType.MAX, objective);
  }

  public static SolvingStrategy optimize(OptimizationType type, IntVar i) {
    switch (type) {
    case MIN:
      return minimize(i);
    case MAX:
      return maximize(i);
    default:
      throw new IllegalArgumentException("Unknown optimization type: " + type);
    }
  }
}
