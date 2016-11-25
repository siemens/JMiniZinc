package at.siemens.ct.jmz.elements.solving;

/**
 * @author © Siemens AG, 2016
 */
public class SolveSatisfy extends SolvingStrategy {

  @Override
  public String declare() {
    return "solve satisfy;";
  }

}
