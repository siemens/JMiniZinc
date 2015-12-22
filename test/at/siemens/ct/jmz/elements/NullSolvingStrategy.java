package at.siemens.ct.jmz.elements;

/**
 * A solving strategy that does not produce any "solve" statement.<br>
 * (This leads to incomplete mzn files, but is helpful for test cases that do not need solve items.)
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class NullSolvingStrategy implements SolvingStrategy {

  @Override
  public String declare() {
    return null;
  }

}
