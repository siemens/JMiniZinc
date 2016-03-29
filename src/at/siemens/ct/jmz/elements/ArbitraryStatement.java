package at.siemens.ct.jmz.elements;

/**
 * This hard-codes an arbitrary statement into a MiniZinc model, throwing over board all aspects of style and good
 * manners.<br>
 * <strong>Use with sparingly and cautiously!</strong>
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class ArbitraryStatement implements Element {

  private final String statement;

  public ArbitraryStatement(String statement) {
    this.statement = statement;
  }

  @Override
  public String declare() {
    return statement;
  }

}
