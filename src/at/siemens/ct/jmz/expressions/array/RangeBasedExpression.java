package at.siemens.ct.jmz.expressions.array;

import java.util.List;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

public interface RangeBasedExpression extends Expression<int[]> {

  List<? extends IntSetExpression> getRange();

  /**
   * @return the number of dimensions
   */
  default int getDimensions() {
    return getRange().size();
  }

}