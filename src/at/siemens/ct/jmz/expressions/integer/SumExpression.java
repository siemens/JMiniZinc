package at.siemens.ct.jmz.expressions.integer;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents the sum over one or more (array(s) of) integers.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class SumExpression implements IntExpression {

  private Collection<SummableIntegers<?>> summands;

  public SumExpression(SummableIntegers<?>... summands) {
    this.summands = Arrays.asList(summands);
  }

  @Override
  public String toString() {
    return use();
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  @Override
  public String use() {
    return summands.stream().map(SummableIntegers::toSum).collect(Collectors.joining(" + "));
    // TODO: Use "-..." instead of "+(-...)" if summand is negative
    // TODO: Omit blanks if desired (e.g. in IntSet bounds)
  }

}
