package at.siemens.ct.jmz.elements.solving;

import at.siemens.ct.jmz.elements.IntVar;

public class Optimize extends SolvingStrategy {

  private OptimizationType type;
  private IntVar objective;
  private String searchAnnotation;

  public Optimize(OptimizationType type, IntVar objective) {
    this.type = type;
    this.objective = objective;
  }

  /**
   * TODO: searchAnnotation should not be a string but a JMiniZinc object
   * TODO: searchAnnotation should also be supported by {@link SolveSatisfy}
   */
  public Optimize(OptimizationType type, IntVar objective, String searchAnnotation) {
    this.type = type;
    this.objective = objective;
    this.searchAnnotation = searchAnnotation;
  }

  @Override
  public String declare() {
    StringBuilder sb = new StringBuilder();
    sb.append("solve");
    if (searchAnnotation != null && !searchAnnotation.isEmpty()) {
      sb.append(" :: ");
      sb.append(searchAnnotation);
    }
    sb.append(' ');
    sb.append(type);
    sb.append(' ');
    sb.append(objective.getName());
    sb.append(';');
    return sb.toString();
  }

}
