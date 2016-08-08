package at.siemens.ct.jmz.expressions.bool;

public class Implication extends BinaryLogicalOperation {

  public Implication(BooleanExpression antedecent, BooleanExpression consequent) {
    super(antedecent, BinaryLogicalOperator.Implies, consequent);
  }

}
