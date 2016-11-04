package at.siemens.ct.jmz.expressions.bool;

import at.siemens.ct.jmz.expressions.Constant;
import at.siemens.ct.jmz.expressions.set.BooleanSetExpression;

public class BooleanConstant extends Constant<Boolean, Boolean> implements BooleanExpression {

	public static final BooleanConstant TRUE = new BooleanConstant(true);
	public static final BooleanConstant FALSE = new BooleanConstant(false);

	public static BooleanConstant valueOf(boolean otherValue) {
		return otherValue ? TRUE : FALSE;
	}

	public BooleanConstant(boolean value) {
    super(BooleanSetExpression.BOOLEAN_UNIVERSE, Boolean.valueOf(value));
	}

	@Override
	public boolean isComposite() {
		return false;
	}

}
