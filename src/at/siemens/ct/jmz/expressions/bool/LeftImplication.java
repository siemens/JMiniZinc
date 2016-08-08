package at.siemens.ct.jmz.expressions.bool;

public class LeftImplication extends BinaryLogicalOperation {

  public LeftImplication(BooleanExpression consequent, BooleanExpression antedecent) {
    super(consequent, BinaryLogicalOperator.OnlyIf, antedecent);
  }

}
