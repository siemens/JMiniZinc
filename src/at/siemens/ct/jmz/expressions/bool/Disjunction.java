package at.siemens.ct.jmz.expressions.bool;

public class Disjunction extends BinaryLogicalOperation {

  public Disjunction(BooleanExpression disjunct1, BooleanExpression disjunct2) {
    super(disjunct1, BinaryLogicalOperator.Or, disjunct2);
  }

}
