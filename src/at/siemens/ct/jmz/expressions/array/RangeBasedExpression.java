package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;

import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.expressions.Expression;

public interface RangeBasedExpression extends Expression {

  Collection<IntSet> getRange();

}