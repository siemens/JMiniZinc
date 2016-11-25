package at.siemens.ct.jmz.expressions.comprehension;

import at.siemens.ct.jmz.expressions.Expression;

/**
 * @author © Siemens AG, 2016
 */
public class IntegerListComprehension extends ListComprehension<Integer> {

	public IntegerListComprehension(Generator<Integer> generator, Expression<Integer> expression) {
    super(generator, expression);
  }

}
