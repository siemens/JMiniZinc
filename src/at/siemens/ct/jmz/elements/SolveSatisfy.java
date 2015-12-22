package at.siemens.ct.jmz.elements;

public class SolveSatisfy implements SolvingStrategy {

  @Override
  public String declare() {
    return "solve satisfy;";
  }

}
