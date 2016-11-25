package at.siemens.ct.jmz.elements.solving;

/**
 * @author © Siemens AG, 2016
 */
public enum OptimizationType {
  MIN("minimize"), MAX("maximize");

  private String keyword;

  OptimizationType(String keyword) {
    this.keyword = keyword;
  }

  @Override
  public String toString() {
    return keyword;
  }
}