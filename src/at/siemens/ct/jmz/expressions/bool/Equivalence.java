package at.siemens.ct.jmz.expressions.bool;

/**
 * @author © Siemens AG, 2016
 */
public class Equivalence extends BinaryLogicalOperation {

  public Equivalence(BooleanExpression operand1, BooleanExpression operand2) {
    super(operand1, BinaryLogicalOperator.IfAndOnlyIf, operand2);
  }

}
