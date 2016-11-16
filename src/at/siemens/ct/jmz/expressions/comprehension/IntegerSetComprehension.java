package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;

public class IntegerSetComprehension extends SetComprehension<Integer>
    implements IntegerSetExpression {

	public IntegerSetComprehension(Generator<Integer> generator, Expression<Integer> expression) {
    super(generator, expression);
  }

}
