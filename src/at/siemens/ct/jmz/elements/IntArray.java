package at.siemens.ct.jmz.elements;

import java.util.List;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.array.IntArrayExpression;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

public interface IntArray extends NamedElement, IntArrayExpression {

  @Override
  List<? extends IntSetExpression> getRange();

  default String declareRange() {
    return declareRange(getRange());
  }

  static String declareRange(List<? extends IntSetExpression> range) {
    return range.stream().map(IntSetExpression::use).collect(Collectors.joining(", "));
  }

}
