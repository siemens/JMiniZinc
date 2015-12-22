package at.siemens.ct.jmz.elements.solving;

import at.siemens.ct.jmz.elements.IntVar;

public class Optimize extends SolvingStrategy {

  private OptimizationType type;
  private IntVar objective;

  public Optimize(OptimizationType type, IntVar objective) {
    this.type = type;
    this.objective = objective;
  }

  @Override
  public String declare() {
    return String.format("solve %s %s;", type, objective.getName());
  }

}
