package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.IntegerSetExpression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerSetComprehension extends SetComprehension<Integer>
    implements IntegerSetExpression {

	public IntegerSetComprehension(Generator<Integer> generator, Expression<Integer> expression) {
    super(generator, expression);
  }

}
