package at.siemens.ct.jmz.elements;

public class IntSet implements MiniZincElement {

  /**
   * Represents the set of all integers
   */
  public static final IntSet ALL_INTEGERS = new IntSet("int", null, null);

  private String name;
  private IntConstant lb;
  private IntConstant ub;

  public IntSet(String name, int lb, int ub) {
    this(name, new IntConstant(lb), new IntConstant(ub));
  }

  public IntSet(String name, int lb, IntConstant ub) {
    this(name, new IntConstant(lb), ub);
  }

  public IntSet(String name, IntConstant lb, IntConstant ub) {
    super();
    this.name = name;
    this.lb = lb;
    this.ub = ub;
  }

  @Override
  public String declare() {
    return String.format("set of int: %s = %s..%s;", name, lb.nameOrValue(), ub.nameOrValue());
  }

  public String getName() {
    return name;
  }

}
