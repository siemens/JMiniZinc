package at.siemens.ct.jmz.elements;

import java.util.Collection;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * A set of integers that is defined by a concrete subset of another {@link IntSetExpression}.
 * 
 * @author z003ft4a
 *
 */
public class IntSubSet implements NamedElement, IntSetExpression {

  private String name;
  private IntSetExpression parent;
  private Collection<Integer> elements;

  public IntSubSet(String name, IntSetExpression parent, Collection<Integer> elements) {
    // TODO: make more generic (as elements, we could accept another IntSetExpression, which is either an explicit
    // constant set or a comprehension (see IntExplicitList)
    this.name = name;
    this.parent = parent;
    this.elements = elements;
  }

  @Override
  public String declare() {
    mustHaveName();
    return String.format("set of %s: %s = %s;", parent.use(), name, define());
  }

  private String define() {
    return "{" + elements.stream().map(String::valueOf).collect(Collectors.joining(", ")) + "}";
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * @return the name of this set, if it is not {@code null}, or else its definition as a list of integers between curly
   *         brackets.
   */
  @Override
  public String use() {
    return nameOrDefinition();
  }

  private String nameOrDefinition() {
    return name != null ? name : define();
  }

  @Override
  public Boolean contains(int value) {
    return elements.contains(value);
  }

  public static Collection<IntSubSet> createConstants(Collection<Collection<Integer>> sets) {
    return sets.stream().map(IntSubSet::createConstant).collect(Collectors.toList());
  }

  public static IntSubSet createConstant(Collection<Integer> set) {
    return new IntSubSet(null, IntSet.ALL_INTEGERS, set);
  }

}
