package at.siemens.ct.jmz.elements;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.array.IntArrayExpression;

public interface IntArray extends Element, IntArrayExpression {

  Collection<IntSet> getRange();

  default String declareRange() {
    return declareRange(getRange());
  }

  static String declareRange(Collection<IntSet> range) {
    return range.stream().map(IntSet::nameOrRange).collect(Collectors.joining(", "));
  }

}
