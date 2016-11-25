package at.siemens.ct.jmz.expressions.bool;

/**
 * @author © Siemens AG, 2016
 */
public class LeftImplication extends BinaryLogicalOperation {

  public LeftImplication(BooleanExpression consequent, BooleanExpression antedecent) {
    super(consequent, BinaryLogicalOperator.OnlyIf, antedecent);
  }

}
