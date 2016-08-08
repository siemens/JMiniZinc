package at.siemens.ct.jmz.expressions.bool;

public class Conjunction extends BinaryLogicalOperation {

  public Conjunction(BooleanExpression conjunct1, BooleanExpression conjunct2) {
    super(conjunct1, BinaryLogicalOperator.And, conjunct2);
  }

}
