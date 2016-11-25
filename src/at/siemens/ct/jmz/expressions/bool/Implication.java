package at.siemens.ct.jmz.expressions.bool;

/**
 * @author © Siemens AG, 2016
 */
public class Implication extends BinaryLogicalOperation {

  public Implication(BooleanExpression antedecent, BooleanExpression consequent) {
    super(antedecent, BinaryLogicalOperator.Implies, consequent);
  }

}
